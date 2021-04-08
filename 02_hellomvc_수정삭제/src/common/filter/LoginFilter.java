package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(urlPatterns = { 
	"/member/memberView", 
	"/member/memberUpdate", 
	"/member/memberDelete", 
	"/member/updatePassword",
	"/board/boardForm"
})
public class LoginFilter implements Filter {


	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//로그인 여부 확인하기
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpRes = (HttpServletResponse)response;
		
		HttpSession session = httpReq.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		if(loginMember == null) {
			session.setAttribute("msg", "로그인후 사용할 수 있습니다.");
			httpRes.sendRedirect(httpReq.getContextPath());
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

}
