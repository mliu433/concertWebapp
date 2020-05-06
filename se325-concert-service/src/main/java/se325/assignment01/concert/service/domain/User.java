package se325.assignment01.concert.service.domain;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Version
    @Column(name = "VERSION")
    private long version;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "TOKEN")
    private String token;
    protected User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setToken(String token ){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
