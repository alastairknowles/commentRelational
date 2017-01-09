package uk.co.comment.relational.domain;

import javax.persistence.*;

@Entity
@Table(name = "comment_like")
public class CommentLike {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Comment getComment() {
        return comment;
    }
    
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    
}
