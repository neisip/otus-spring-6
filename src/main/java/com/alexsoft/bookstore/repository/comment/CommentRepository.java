package com.alexsoft.bookstore.repository.comment;

import com.alexsoft.bookstore.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

}
