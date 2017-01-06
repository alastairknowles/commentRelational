package uk.co.comment.relational.service;

import uk.co.comment.relational.domain.Comment;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.comment.relational.repository.CommentRepository;
import uk.co.comment.relational.rest.CommentDTO;

@Service
@Transactional
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    public Comment getComment(Long id) {
        return commentRepository.findOne(id);
    }
    
    public Long createComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setName(commentDTO.getName());
        comment.setPosted(DateTime.now());
        commentRepository.save(comment);
        return comment.getId();
    }
    
}
