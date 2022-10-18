package com.example.blog.models;

import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Username shouldn't be emty")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Password shouldn't be emty")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Field confirm password shouldn't be emty")
    @Transient
    private String confirmPassword;

    @OneToMany(mappedBy = "person")
    private List<Comment> comments;


    public Person(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Person() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(username, person.username) && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
