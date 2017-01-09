package uk.co.comment.relational.rest;

import java.util.ArrayList;
import java.util.Collection;

public class CommentsDTO extends ArrayList<CommentDTO> {
    
    public CommentsDTO() {
    }
    
    public CommentsDTO(Collection<CommentDTO> commentDTOs) {
        super(commentDTOs);
    }
    
}
