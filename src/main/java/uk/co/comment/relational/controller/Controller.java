package uk.co.comment.relational.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.comment.relational.rest.CommentDTO;
import uk.co.comment.relational.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class Controller {
    
    @Autowired
    private CommentService commentService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/comment", method = RequestMethod.POST)
    public void createComment(@RequestBody @Valid CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
    }
    
}
