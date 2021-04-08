package admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class AdminMemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자입력값 처리
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");
		final int numPerPage = 10;
		int cPage = 1;
		try {
			cPage =	Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
			// 처리 코드 없음. 기본값 1 유지.
		}
		Map<String, String> param = new HashMap<>();
		param.put("searchType", searchType);
		param.put("searchKeyword", searchKeyword);
		param.put("start", String.valueOf((cPage -1) * numPerPage + 1));
		param.put("end", String.valueOf(cPage * numPerPage));
		System.out.println("param@servlet = " + param);
		
		//2. 업무 로직
		List<Member> list = memberService.searchMember(param);
		System.out.println("list@servlet = " + list);
		
		int totalContents = memberService.searchMemberCount(param);
		System.out.println("totalContents@servlet = " + totalContents);
		
		//3. pageBar영역 작업
		String url = request.getRequestURI() + "?searchType=" + searchType + "&searchKeyword=" + searchKeyword ; 
		// /mvc/admin/memberFinder?searchType=gender&serarchKeyword=M
		String pageBar = MvcUtils.getPageBar(
					cPage,
					numPerPage,
					totalContents,
					url
				);
		
		//4. jsp에 html응답메세지 작성 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp")
			   .forward(request, response);
		
		
		
		
		
		
		
	}

}
