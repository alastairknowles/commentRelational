package service;

import domain.Comment;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rest.CommentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentServiceTest {
    
    @Autowired
    private CommentService commentService;
    
    @Test
    public void shouldPersistComment() {
        DateTime baseline = DateTime.now();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My first comment");
        commentDTO.setName("Alastair Knowles");
        
        Long commentId = commentService.createComment(commentDTO);
        Assert.assertEquals(new Long(1), commentId);
        
        Comment comment = commentService.getComment(commentId);
        Assert.assertEquals(commentDTO.getComment(), comment.getComment());
        Assert.assertEquals(commentDTO.getName(), comment.getName());
        
        DateTime posted = comment.getPosted();
        Assert.assertTrue(posted.equals(baseline) || posted.isAfter(baseline));
    }
    
    @Test
    public void shouldNotPersistCommentWithMissingComment() {
        
    }
    
    @Test
    public void shouldNotPersistCommentWithMissingName() {
        
    }
    
}
