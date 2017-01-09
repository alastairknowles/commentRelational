package uk.co.comment.relational.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.comment.relational.domain.Comment;
import uk.co.comment.relational.rest.CommentDTO;

import java.util.List;

@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
    
    @Query(value = "select new uk.co.comment.relational.rest.CommentDTO(c.id, c.comment, c.name, c.posted, count(cl.id)) " +
            "from Comment c " +
            "left join c.likes cl " +
            "group by c.id order " +
            "by c.id desc")
    List<CommentDTO> findAllWithLikeCountsOrderByIdDesc();
    
}

