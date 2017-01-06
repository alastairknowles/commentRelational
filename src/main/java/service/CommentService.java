package service;

import domain.Comment;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CommentRepository;
import rest.CommentDTO;

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
