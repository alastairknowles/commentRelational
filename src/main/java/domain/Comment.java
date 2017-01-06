package domain;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "posted", nullable = false)
    private DateTime posted;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public DateTime getPosted() {
        return posted;
    }
    
    public void setPosted(DateTime posted) {
        this.posted = posted;
    }
    
}
