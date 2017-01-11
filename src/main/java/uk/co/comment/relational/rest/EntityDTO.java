package uk.co.comment.relational.rest;

public class EntityDTO {
    
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public EntityDTO id(Long id) {
        this.id = id;
        return this;
    }
    
}
