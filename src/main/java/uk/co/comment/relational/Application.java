package uk.co.comment.relational;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = "uk.co.comment.relational")
public class Application {
    
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
    public static class ConfigurationData {
        
        @Autowired
        private Environment environment;
        
        @Bean
        public DataSource dataSource() {
            DataSource dataSource = new DataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost/" + environment.getProperty("database.schema")
                    + "?useUnicode=yes&characterEncoding=UTF-8&connectionCollation=utf8_general_ci&useLegacyDatetimeCode=false&serverTimezone=UTC");
            dataSource.setUsername(environment.getProperty("database.user"));
            dataSource.setPassword(environment.getProperty("database.password"));
            dataSource.setInitialSize(10);
            dataSource.setMaxActive(20);
            return dataSource;
        }
        
    }
    
}
