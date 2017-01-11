package uk.co.comment.relational.rest;

import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

public class CommentDTO {
    
    private Long id;
    
    @NotNull
    private String comment;
    
    @NotNull
    private String name;
    
    private DateTime posted;
    
    public Long likes;
    
    public CommentDTO() {
    }
    
    public CommentDTO(Long id, String comment, String name, DateTime posted, Long likes) {
        this.id = id;
        this.comment = comment;
        this.name = name;
        this.posted = posted;
        this.likes = likes;
    }
    
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
    
    public Long getLikes() {
        return likes;
    }
    
    public void setLikes(Long likes) {
        this.likes = likes;
    }
    
}
