package uk.co.comment.relational.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.co.comment.relational.rest.CommentDTO;
import uk.co.comment.relational.rest.CommentsDTO;

import java.util.List;

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
        verifyCreateComment(commentDTO, MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldRetrieveComments() throws Exception {
        for (String comment : new String[]{"My First Comment", "My Second Comment", "My Third Comment"}) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setComment(comment);
            commentDTO.setName("Alastair Knowles");
            verifyCreateComment(commentDTO, MockMvcResultMatchers.status().isCreated());
        }
        
        CommentsDTO commentsDTO = objectMapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(), CommentsDTO.class);
        
        Assert.assertEquals(3, commentsDTO.size());
        List<String> expectations = Lists.newArrayList("My Third Comment", "My Second Comment", "My First Comment");
        for (int i = 0; i < commentsDTO.size(); i++) {
            CommentDTO commentDTO = commentsDTO.get(i);
            Assert.assertEquals(expectations.get(i), commentDTO.getComment());
            Assert.assertEquals("Alastair Knowles", commentDTO.getName());
        }
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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(resultMatcher);
    }
    
}
