package io.goorm.backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import io.goorm.backend.config.DatabaseConfig;

import java.util.List;
import java.util.ArrayList;

public class BoardDAO {

    private JdbcTemplate jdbcTemplate;

    public BoardDAO() {
        this.jdbcTemplate = new JdbcTemplate(DatabaseConfig.getDataSource());
    }

    // RowMapper 수정
    private RowMapper<Board> boardRowMapper = (rs, rowNum) -> {
        Board board = new Board();
        board.setId(rs.getLong("id"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setAuthor(rs.getString("author"));
        board.setAuthorId(rs.getInt("author_id"));
        board.setAuthorName(rs.getString("author_name"));
        board.setCreatedAt(rs.getTimestamp("created_at"));
        return board;
    };
    // getBoardList 메서드 수정 (작성자 이름 포함)
    public List<Board> getBoardList() {
        String sql = "SELECT b.*, u.name as author_name FROM board b " +
                "LEFT JOIN users u ON b.author_id = u.id " +
                "ORDER BY b.id DESC";
        try {
            return jdbcTemplate.query(sql, boardRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public Board getBoardById(Long id) {
        String sql = "SELECT b.*, u.name as author_name FROM board b " +
                "LEFT JOIN users u ON b.author_id = u.id " +
                "WHERE b.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, boardRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }
    public boolean insertBoard(Board board) {
        String sql = "INSERT INTO board (title, content,author, author_id, created_at) VALUES (?, ?, ?, ?, ?)";
        try {
            int result = jdbcTemplate.update(sql,
                    board.getTitle(),
                    board.getContent(),
                    board.getAuthor(),
                    board.getAuthorId(),
                    board.getCreatedAt());

            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // BoardDAO 클래스에 추가할 메서드
    public List<Board> searchByTitle(String keyword) {
        String sql = "SELECT * FROM board WHERE title LIKE ? ORDER BY id DESC";
        String searchKeyword = "%" + keyword + "%";
        return jdbcTemplate.query(sql, boardRowMapper, searchKeyword);
    }

    // 게시글 수정 메서드도 추가
    public void updateBoard(Board board) {
        String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, board.getTitle(), board.getContent(), board.getId());
    }

    // 게시글 삭제 메서드도 추가
    public boolean deleteBoard(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        int result = jdbcTemplate.update(sql, id);
        return result > 0;
    }
}