package com.alexsoft.bookstore.repository.comment;

import com.alexsoft.bookstore.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
