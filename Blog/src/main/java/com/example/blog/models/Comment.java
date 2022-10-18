package com.example.blog.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @NotEmpty()
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "post_date_and_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDateAndTime;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;


    public Comment(int commentId, String commentText, Date postDateAndTime, Person person) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.postDateAndTime = postDateAndTime;
        this.person = person;
    }

    public Comment() {}


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getPostDateAndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        String format = simpleDateFormat.format(postDateAndTime);
        return format;
    }

    public void setPostDateAndTime(Date postDateAndTime) {
        this.postDateAndTime = postDateAndTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId && Objects.equals(commentText, comment.commentText) && Objects.equals(postDateAndTime, comment.postDateAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentText, postDateAndTime);
    }
}
