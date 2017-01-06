package uk.co.comment.relational.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.co.comment.relational.rest.CommentDTO;

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
    public void shouldCreateComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My second comment");
        commentDTO.setName("Alastair Knowles");
        verifyCreateComment(commentDTO, MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    public void shouldNotCreateCommentWithMissingComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setName("Diane Lillis");
        verifyCreateComment(commentDTO, MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test
    public void shouldNotCreateCommentWithMissingName() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("My third comment");
        verifyCreateComment(commentDTO, MockMvcResultMatchers.status().isBadRequest());
    }
    
    private void verifyCreateComment(CommentDTO commentDTO, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(resultMatcher);
    }
    
}
