package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class UpdatePasswordServlet
 */
@WebServlet("/member/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * 비밀번호 변경페이지 제공
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/updatePassword.jsp")
			   .forward(request, response);
	}

	/**
	 * 비밀번호 변경처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String location = request.getContextPath();
		String msg = null;
		int result = 0;
		
		//1. 사용자 입력값 처리 : 기존비밀번호/신규비밀번호 암호화처리 필수
		String oldPassword = MvcUtils.getSha512(request.getParameter("password"));
		String newPassword = MvcUtils.getSha512(request.getParameter("newPassword"));
		
		//2. 기존비밀번호 비교 : session의 loginMember객체 이용할 것
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");

		if(oldPassword.equals(loginMember.getPassword())) {
			
			//3. 업무로직 : 기존비밀번호가 일치한 경우만 신규비밀번호로 업데이트한다.
			loginMember.setPassword(newPassword);
			result = memberService.updatePassword(loginMember);
			msg = (result > 0) ? 
					"비밀번호를 성공적으로 변경했습니다." : 
						"비밀번호를 변경에 실패했습니다.";
			location += "/member/memberView";
		}
		else {
			msg = "비밀번호가 일치하지 않습니다.";				
			location += "/member/updatePassword";
		}
		
		//4. 사용자경고창 및 리다이렉트 처리
		//기존비밀번호가 일치하지 않았다면, "비밀번호가 일치하지 않습니다." 안내 & /mvc/member/updatePassword 리다이렉트
		//기존비밀번호가 일치하고, 신규비밀번호 변경에 성공했다면, "비밀번호를 성공적으로 변경했습니다." 안내 & /mvc/member/memberView 리다이렉트 
		session.setAttribute("msg", msg);
		response.sendRedirect(location);
		
	}
}
		
