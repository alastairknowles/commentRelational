package uk.co.comment.relational.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.co.comment.relational.rest.CommentDTO;
import uk.co.comment.relational.rest.CommentsDTO;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldCreateComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My second comment");
        commentDTO.setName("Alastair Knowles");
        verifyCreateComment(commentDTO, HttpStatus.CREATED);
    }
    
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldRetrieveComments() throws Exception {
        int likes = 0;
        for (String comment : new String[]{"My First Comment", "My Second Comment", "My Third Comment"}) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setComment(comment);
            commentDTO.setName("Alastair Knowles");
            Long id = verifyCreateComment(commentDTO, HttpStatus.CREATED);
            
            likes++;
            for (int i = 0; i < likes; i++) {
                verifyLikeComment(id);
            }
        }
        
        CommentsDTO commentsDTO = objectMapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(), CommentsDTO.class);
        
        Assert.assertEquals(3, commentsDTO.size());
        String[] expectedComments = new String[]{"My Third Comment", "My Second Comment", "My First Comment"};
        Long[] expectedLikes = new Long[]{new Long(3), new Long(2), new Long(1)};
        
        for (int i = 0; i < commentsDTO.size(); i++) {
            CommentDTO commentDTO = commentsDTO.get(i);
            Assert.assertEquals(expectedComments[i], commentDTO.getComment());
            Assert.assertEquals("Alastair Knowles", commentDTO.getName());
            Assert.assertEquals(expectedLikes[i], commentDTO.getLikes());
        }
    }
    
    @Test
    public void shouldNotCreateCommentWithMissingComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setName("Diane Lillis");
        verifyCreateComment(commentDTO, HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void shouldNotCreateCommentWithMissingName() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My third comment");
        verifyCreateComment(commentDTO, HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void shouldLikeComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My fourth comment");
        commentDTO.setName("Diane Lillis");
        Long id = verifyCreateComment(commentDTO, HttpStatus.CREATED);
        verifyLikeComment(id);
    }
    
    private Long verifyCreateComment(CommentDTO commentDTO, HttpStatus expectedStatus) throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(commentDTO)));
        
        if (expectedStatus.equals(HttpStatus.CREATED)) {
            resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
        } else if (expectedStatus.equals(HttpStatus.BAD_REQUEST)) {
            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
            return null;
        }
        
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Long.class);
    }
    
    private Long verifyLikeComment(Long id) throws Exception {
        return objectMapper.readValue(mockMvc.perform(MockMvcRequestBuilders.post("/api/comments/" + id + "/like")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString(), Long.class);
    }
    
}
