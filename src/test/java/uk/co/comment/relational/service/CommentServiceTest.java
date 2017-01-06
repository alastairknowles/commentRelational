package uk.co.comment.relational.service;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.comment.relational.domain.Comment;
import uk.co.comment.relational.rest.CommentDTO;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentServiceTest {
    
    @Autowired
    private CommentService commentService;
    
    @Test
    public void shouldPersistComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My first comment");
        commentDTO.setName("Alastair Knowles");
        
        DateTime baseline = DateTime.now().withMillisOfSecond(0);
        Long commentId = commentService.createComment(commentDTO);
        
        Comment comment = commentService.getComment(commentId);
        Assert.assertEquals(commentDTO.getComment(), comment.getComment());
        Assert.assertEquals(commentDTO.getName(), comment.getName());
        
        DateTime posted = comment.getPosted();
        Assert.assertTrue(posted.equals(baseline) || posted.isAfter(baseline));
    }
    
    @Test
    public void shouldNotPersistCommentWithMissingComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setName("Diane Lillis");
        
        Exception exception = null;
        try {
            commentService.createComment(commentDTO);
        } catch (Exception e) {
            exception = e;
        }
        
        Assert.assertEquals(DataIntegrityViolationException.class, exception.getClass());
    }
    
    @Test
    public void shouldNotPersistCommentWithMissingName() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My second comment");
        
        Exception exception = null;
        try {
            commentService.createComment(commentDTO);
        } catch (Exception e) {
            exception = e;
        }
        
        Assert.assertEquals(DataIntegrityViolationException.class, exception.getClass());
    }
    
}