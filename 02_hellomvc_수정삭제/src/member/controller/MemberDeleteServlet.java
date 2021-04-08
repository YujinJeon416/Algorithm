package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.한글 깨짐 방지 인코딩처리
		//void javax.servlet.ServletRequest.setCharacterEncoding(String arg0) throws UnsupportedEncodingException
		request.setCharacterEncoding("UTF-8");//대소문자 상관없음. 요청한 view단의 charset값과 동일해야 한다.
		
		//2.사용자 입력값 처리
		//String javax.servlet.ServletRequest.getParameter(String arg0)
		String memberId = request.getParameter("memberId");
		
		//3.서비스로직호출
		int result = memberService.deleteMember(memberId);
		
		//4. 받은 결과에 따라 뷰페이지 내보내기
		HttpSession session = request.getSession();
		if(result>0) {
//			session.setAttribute("msg", "성공적으로 회원정보를 삭제했습니다.");
			//location으로 logout페이지 지정
			response.sendRedirect(request.getContextPath() + "/member/logout");
		}
		else {
			session.setAttribute("msg", "회원정보삭제에 실패했습니다.");
			response.sendRedirect(request.getContextPath());			
		}
	}

}
