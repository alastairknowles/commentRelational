package uk.co.comment.relational.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.comment.relational.domain.Comment;

@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
    
}
