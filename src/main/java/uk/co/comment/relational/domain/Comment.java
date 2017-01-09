package uk.co.comment.relational.domain;

import org.assertj.core.util.Sets;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment extends DatabaseEntity {
    
    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "posted", nullable = false)
    private DateTime posted;
    
    @OneToMany(mappedBy = "comment")
    private Set<CommentLike> likes = Sets.newHashSet();
    
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
    
    public Set<CommentLike> getLikes() {
        return likes;
    }
    
    public void setLikes(Set<CommentLike> likes) {
        this.likes = likes;
    }
    
}
