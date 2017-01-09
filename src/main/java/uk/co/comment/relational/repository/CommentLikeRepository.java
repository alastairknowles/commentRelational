package uk.co.comment.relational.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.comment.relational.domain.CommentLike;

@Transactional
public interface CommentLikeRepository extends CrudRepository<CommentLike, Long> {
}
