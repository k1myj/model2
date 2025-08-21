package io.goorm.backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import io.goorm.backend.config.DatabaseConfig;

public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    public UserDAO() {
        this.jdbcTemplate = new JdbcTemplate(DatabaseConfig.getDataSource());
    }

    // 사용자 등록
    public boolean insertUser(User user) {
        String sql = "INSERT INTO users (username, password, name, email) VALUES (?, ?, ?, ?)";
        try {
            int result = jdbcTemplate.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getEmail());
            return result > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // 사용자명으로 사용자 조회 (중복 확인용)
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, username);
        } catch (Exception e) {
            return null;
        }
    }

    // RowMapper 정의
    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setRegDate(rs.getTimestamp("reg_date"));
        return user;
    };


    // 로그인 검증 (아이디/비밀번호 확인)
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, username, password);
        } catch (Exception e) {
            return null;
        }
    }

    // 사용자 ID로 사용자 조회
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, userId);
        } catch (Exception e) {
            return null;
        }
    }
}