package uk.co.comment.relational.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.comment.relational.rest.CommentDTO;
import uk.co.comment.relational.rest.CommentsDTO;
import uk.co.comment.relational.rest.EntityDTO;
import uk.co.comment.relational.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class Controller {
    
    private CommentService commentService;
    
    public Controller(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/comments", method = RequestMethod.POST)
    public EntityDTO createComment(@RequestBody @Valid CommentDTO commentDTO) {
        return commentService.createComment(commentDTO);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/comments", method = RequestMethod.GET)
    public CommentsDTO retrieveComments() {
        return commentService.getComments();
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/comments/{id}/like", method = RequestMethod.POST)
    public EntityDTO likeComment(@PathVariable Long id) {
        return commentService.likeComment(id);
    }
    
}
