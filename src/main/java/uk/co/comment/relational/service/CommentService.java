package uk.co.comment.relational.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.comment.relational.domain.Comment;
import uk.co.comment.relational.domain.CommentLike;
import uk.co.comment.relational.repository.CommentLikeRepository;
import uk.co.comment.relational.repository.CommentRepository;
import uk.co.comment.relational.rest.CommentDTO;
import uk.co.comment.relational.rest.CommentsDTO;

@Service
@Transactional
public class CommentService {
    
    private CommentRepository commentRepository;
    
    private CommentLikeRepository commentLikeRepository;
    
    public CommentService(CommentRepository commentRepository, CommentLikeRepository commentLikeRepository) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
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
        return new CommentsDTO(commentRepository.findAllWithLikeCountsOrderByIdDesc());
    }
    
    public Long likeComment(Long id) {
        Comment comment = getComment(id);
        CommentLike like = new CommentLike();
        like.setComment(comment);
        like = commentLikeRepository.save(like);
        comment.getLikes().add(like);
        return like.getId();
    }
    
}
