package uk.co.comment.relational.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.comment.relational.domain.Comment;
import uk.co.comment.relational.rest.CommentDTO;

import java.util.List;

@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
    
    List<Comment> findAllByOrderByIdDesc();
    
    @Query(value = "select new uk.co.comment.relational.rest.CommentDTO(cl.comment.id, count(cl.id)) from CommentLike cl group by cl.comment.id")
    List<CommentDTO> findCommentLikeCounts();
    
}
