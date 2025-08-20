package io.goorm.backend.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 게시글 작성 폼 표시 Servlet
 * Model 2 아키텍처의 컨트롤러 역할
 */
//@WebServlet("/board/write")
public class BoardWriteCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 글쓰기 폼 JSP로 포워딩
        return "/board/write.jsp";
    }
}