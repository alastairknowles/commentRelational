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
    
    public CommentDTO id(Long id) {
        this.id = id;
        return this;
    }
    
    public CommentDTO comment(String comment) {
        this.comment = comment;
        return this;
    }
    
    public CommentDTO name(String name) {
        this.name = name;
        return this;
    }
    
    public CommentDTO posted(DateTime posted) {
        this.posted = posted;
        return this;
    }
    
}
