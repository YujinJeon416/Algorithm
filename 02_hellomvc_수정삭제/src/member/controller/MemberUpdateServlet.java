package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/member/memberUpdate")
public class MemberUpdateServlet extends HttpServlet {
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
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
//		String memberRole = request.getParameter("memberRole"); 없어도 되는 구문 
		String _birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		//체크박스같은 경우 선택된 복수의 값이 배열로 전달된다.
		//String[] javax.servlet.ServletRequest.getParameterValues(String arg0)
		String[] hobbies = request.getParameterValues("hobby");
		
		String hobby = "";
		//String java.lang.String.join(CharSequence delimiter, CharSequence... elements)
		//hobbies가 null이면, NullPointerException유발.
		if(hobbies != null) 
			hobby = String.join(",", hobbies);

		//날짜타입으로 변경 : 1990-09-09
		Date birthday = null;
		if(_birthday != null && !"".equals(_birthday))
			birthday = Date.valueOf(_birthday);
		
		Member member = new Member(memberId, password, memberName, null, gender, birthday, email, phone, address, hobby, null);
		System.out.println("member@servlet = " + member);
		
		//3.업무로직
		int result = memberService.updateMember(member);  

		//4. 사용자피드백 및 리다이렉트 처리
		HttpSession session = request.getSession();
		String msg = "";

		if(result > 0){
			msg = "성공적으로 회원정보를 수정했습니다.";
			//세션의 정보도 갱신
			session.setAttribute("loginMember", memberService.selectOne(memberId));
		}
		else 
			msg = "회원정보수정에 실패했습니다.";	
		
		session.setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath() + "/member/memberView");
	}


}
