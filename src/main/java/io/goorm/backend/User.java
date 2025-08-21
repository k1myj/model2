package io.goorm.backend;
import java.sql.Timestamp;

public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private Timestamp regDate;

    // ✅ 기본 생성자
    public User() {
    }

    // ✅ 모든 필드를 받는 생성자
    public User(Long id, String username, String password, String name, String email, Timestamp regDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.regDate = regDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getRegDate() {
        return regDate;
    }
}
