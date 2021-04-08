package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollServlet
 */
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 회원가입페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp")
			   .forward(request, response);
	}

	/**
	 * 회원가입 처리 - db에 저장
	 * 
	 * java.sql.Date객체 생성하기 : Date.valueOf("1990-09-09")
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.전송값에 한글이 있을 경우 인코딩처리해야함.
		//void javax.servlet.ServletRequest.setCharacterEncoding(String arg0) throws UnsupportedEncodingException
		//request.setCharacterEncoding("UTF-8");//대소문자 상관없음. 요청한 view단의 charset값과 동일해야 한다.
		
		//2.전송값 꺼내서 변수에 기록하기.
		//String javax.servlet.ServletRequest.getParameter(String arg0)
		String memberId = request.getParameter("memberId");
		String password = MvcUtils.getSha512(request.getParameter("password"));
		String memberName = request.getParameter("memberName");
		String _birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		//체크박스같은 경우 선택된 복수의 값이 배열로 전달된다.
		//String[] javax.servlet.ServletRequest.getParameterValues(String arg0)
		String[] hobbyArr = request.getParameterValues("hobby");
		
		String hobby = "";
		//String java.lang.String.join(CharSequence delimiter, CharSequence... elements)
		//파라미터로 전달한 문자열배열이 null이면, NullPointerException유발.
		if(hobbyArr != null) hobby = String.join(",", hobbyArr);

		//날짜타입으로 변경 : 1990-09-09
		Date birthday = null;
		if(_birthday != null && !"".equals(_birthday))
			birthday = Date.valueOf(_birthday);
		
		Member member = 
			new Member(memberId, password, memberName, MemberService.MEMBER_ROLE, gender, birthday, email, phone, address, hobby, null);
		

		System.out.println("입력한 회원정보 : "+member);
		
		//3.서비스로직호출
		int result = new MemberService().insertMember(member);
		
		//4. 사용자 피드백 및 페이지 리다이렉트
		String msg = "";
		if(result>0)
			msg = "성공적으로 회원가입되었습니다.";
		else 
			msg = "회원등록에 실패했습니다.";	
		
		request.getSession().setAttribute("msg", msg);
		
		response.sendRedirect(request.getContextPath());
	}

}
