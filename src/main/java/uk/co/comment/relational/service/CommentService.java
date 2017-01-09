package uk.co.comment.relational.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.comment.relational.domain.Comment;
import uk.co.comment.relational.repository.CommentRepository;
import uk.co.comment.relational.rest.CommentDTO;
import uk.co.comment.relational.rest.CommentsDTO;

import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {
    
    private CommentRepository commentRepository;
    
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    public Comment getComment(Long id) {
        return commentRepository.findOne(id);
    }
    
    public Long createComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setName(commentDTO.getName());
        comment.setPosted(DateTime.now());
        comment = commentRepository.save(comment);
        return comment.getId();
    }
    
    public CommentsDTO getComments() {
        return new CommentsDTO(commentRepository.findAllByOrderByIdDesc().stream().map(comment ->
                new CommentDTO().id(comment.getId()).comment(comment.getComment()).name(comment.getName()).posted(comment.getPosted()))
                .collect(Collectors.toList()));
    }
    
}
