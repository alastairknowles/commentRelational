package uk.co.comment.relational;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootApplication(scanBasePackages = "uk.co.comment.relational")
public class Application {
    
    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);
    
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
    
    @Configuration
    @Profile("local")
    @PropertySource(value = {"classpath:application.properties", "classpath:application.local.properties"})
    public static class ConfigurationLocal {
    }
    
    @Configuration
    @Profile("test")
    @PropertySource(value = {"classpath:application.properties", "classpath:application.test.properties"})
    public static class ConfigurationTest {
    }
    
    @Configuration
    public static class ConfigurationCommon {
        
        private Environment environment;
        
        public ConfigurationCommon(Environment environment) {
            this.environment = environment;
            DateTimeZone.setDefault(DateTimeZone.UTC);
        }
        
        @Bean
        public DataSource dataSource() {
            String host = "jdbc:mysql://localhost";
            String schema = environment.getProperty("database.schema");
            String username = environment.getProperty("database.user");
            String password = environment.getProperty("database.password");
            
            try (Connection connection = DriverManager.getConnection(host, "root", "root")) {
                LOGGER.info("Preparing database schema: " + schema);
                PreparedStatement createSchema = connection.prepareStatement("create schema if not exists " + schema);
                createSchema.execute();
                
                LOGGER.info("Preparing database user: " + username);
                PreparedStatement createUser = connection.prepareStatement("grant all on " + schema + ".* to '" + username + "'@'%' identified by '" + password + "'");
                createUser.execute();
            } catch (SQLException e) {
                LOGGER.info("Failed to prepare database schema and user - check Java has root permissions", e);
            }
            
            DataSource dataSource = new DataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(host + "/" + schema + "?useUnicode=yes&characterEncoding=UTF-8&connectionCollation=utf8_general_ci&useLegacyDatetimeCode=false&serverTimezone=UTC");
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setInitialSize(10);
            dataSource.setMaxActive(20);
            return dataSource;
        }
        
        @Bean
        public Flyway flyway(DataSource dataSource) {
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            
            String activeProfile = environment.getActiveProfiles()[0];
            flyway.setLocations("classpath:database/common", "classpath:database/" + activeProfile);
            
            if (activeProfile.equals("test")) {
                flyway.clean();
            }
            
            flyway.migrate();
            return flyway;
        }
        
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper()
                    .registerModule(new JodaModule());
        }
        
    }
    
}
