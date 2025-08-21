package io.goorm.backend.command;

import io.goorm.backend.Board;
import io.goorm.backend.BoardDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BoardUpdateCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getMethod().equals("GET")) {
                // 로그인 확인
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    response.sendRedirect("front?command=login");
                    return null;
                }

                Long boardId = Long.parseLong(request.getParameter("id"));
                BoardDAO boardDAO = new BoardDAO();
                Board board = boardDAO.getBoardById(boardId);

                if (board == null) {
                    response.sendRedirect("front?command=boardList");
                    return null;
                }

                // 작성자 확인
                int currentUserId = (Integer) session.getAttribute("userId");
                if (board.getAuthorId() != currentUserId) {
                    request.setAttribute("error", "본인이 작성한 글만 수정할 수 있습니다.");
                    return "/board/view.jsp";
                }

                request.setAttribute("board", board);
                return "/board/update.jsp";
            } else {
                // POST 요청 - 게시글 수정
                // (기존 로직에 작성자 확인 추가)
                request.setAttribute("error", "게시글 수정에 실패했습니다.");
                return "/board/update.jsp";

            }
        } catch (Exception e) {
            request.setAttribute("error", "게시글 수정 중 오류가 발생했습니다.");
            return "/board/list.jsp";
        }
    }

}