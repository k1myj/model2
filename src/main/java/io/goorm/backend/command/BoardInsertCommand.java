package io.goorm.backend.command;

import io.goorm.backend.Board;
import io.goorm.backend.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * 게시글 등록 처리 Servlet
 * Model 2 아키텍처의 컨트롤러 역할
 */
//@WebServlet("/board/insert")
public class BoardInsertCommand implements Command {
    @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // POST 요청 처리
            request.setCharacterEncoding("UTF-8");

            String title = request.getParameter("title");
            String writer = request.getParameter("writer");
            String content = request.getParameter("content");
            HttpSession session = request.getSession(false);

            if (title == null || title.trim().isEmpty()) {
                request.setAttribute("error", "제목을 입력해주세요.");
                request.setAttribute("title", title);
                request.setAttribute("Au", writer);
                request.setAttribute("content", content);
                return "/board/write.jsp";
            }

            Board board = new Board();
            board.setTitle(title);
            board.setAuthor(writer);
            board.setContent(content);
            board.setAuthorId(((Long) session.getAttribute("userId")).intValue());
            board.setCreatedAt(new Timestamp(System.currentTimeMillis()));


            BoardDAO dao = new BoardDAO();
            dao.insertBoard(board);

            // 목록으로 리다이렉트
            response.sendRedirect("front?command=boardList");
            return null; // 리다이렉트 시 null 반환

        } catch (Exception e) {
            request.setAttribute("error", "게시글 등록에 실패했습니다: " + e.getMessage());
            return "/board/write.jsp";
        }
  }
}
