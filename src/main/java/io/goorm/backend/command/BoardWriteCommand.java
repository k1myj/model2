package io.goorm.backend.command;

import io.goorm.backend.Board;
import io.goorm.backend.BoardDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * 게시글 작성 폼 표시 Servlet
 * Model 2 아키텍처의 컨트롤러 역할
 */
//@WebServlet("/board/write")
public class BoardWriteCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getMethod().equals("GET")) {
                // 로그인 확인
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    // 로그인하지 않은 사용자는 로그인 페이지로 이동
                    response.sendRedirect("front?command=login");
                    return null;
                }
                return "/board/write.jsp";
            } else {
                // POST 요청 - 게시글 작성
                request.setCharacterEncoding("UTF-8");

                // 로그인 확인
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    response.sendRedirect("front?command=login");
                    return null;
                }

                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String author = request.getParameter("writer");

                if (title == null || title.trim().isEmpty() ||
                        content == null || content.trim().isEmpty()) {
                    request.setAttribute("error", "제목과 내용을 모두 입력해주세요.");
                    return "/board/write.jsp";
                }

                Board board = new Board();
                board.setTitle(title);
                board.setContent(content);
                board.setAuthor(author);
                board.setAuthorId(((Long) session.getAttribute("userId")).intValue());
                board.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                BoardDAO boardDAO = new BoardDAO();
                if (boardDAO.insertBoard(board)) {
                    response.sendRedirect("front?command=boardList");
                    return null;
                } else {
                    request.setAttribute("error", "게시글 작성에 실패했습니다.");
                    return "/board/write.jsp";
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "게시글 작성 중 오류가 발생했습니다.");
            return "/board/write.jsp";
        }
    }
}