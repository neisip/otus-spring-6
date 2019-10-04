package com.alexsoft.bookstore.repository.comment;

import com.alexsoft.bookstore.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
