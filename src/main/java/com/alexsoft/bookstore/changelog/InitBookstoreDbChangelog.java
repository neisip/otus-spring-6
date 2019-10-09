package com.alexsoft.bookstore.changelog;

import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.domain.Comment;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.LinkedList;
import java.util.List;


@ChangeLog(order = "001")
public class InitBookstoreDbChangelog {

    private List<Author> makeAuthors(MongoTemplate template) {
        String[] authorNames = {"Egor Bugaenko", "Mike Brown", "Robert Martin"};
        List<Author> al = new LinkedList<>();

        for (String e :authorNames) {
            val a = new Author(e);
            template.save(a);
            al.add(a);
        }
        return al;
    }

    private List<Comment> makeComments(MongoTemplate template) {
        String[] comments = {"Best book in da world", "Shittiest book ever read"};
        List<Comment> commentList = new LinkedList<>();

        for (String c :comments) {
            val comment = new Comment(c);
            template.save(comment);
            commentList.add(comment);
        }
        return commentList;
    }

    @ChangeSet(order = "000", id = "dropDB", author = "alex", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "init books", author = "alex", runAlways = true)
    public void initBooks(MongoTemplate template) {

        val authorList = makeAuthors(template);
        val commentList = makeComments(template);

        val elegantObjects = new Book();
        elegantObjects.setAuthor(authorList.get(0));
        elegantObjects.setTitle("Elegant objects");
        elegantObjects.setGenre("Psychological thriller");
        elegantObjects.setCommentList(commentList);
        template.save(elegantObjects);

        val adaptology = new Book();
        adaptology.setAuthor(authorList.get(1));
        adaptology.setTitle("Adaptology for intensive chair pressers");
        adaptology.setGenre("Sport");
        template.save(adaptology);

        val cleanBrain = new Book();
        cleanBrain.setAuthor(authorList.get(2));
        cleanBrain.setTitle("Clean Brain");
        cleanBrain.setGenre("Programming");
        template.save(cleanBrain);
    }

}

