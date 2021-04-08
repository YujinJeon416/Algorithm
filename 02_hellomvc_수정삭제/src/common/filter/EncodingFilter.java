package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * web.xml에 등록된 순서대로 처리됨.
 * web.xml이 @WebFilter 보다 우선처리됨.
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//인코딩 처리
		request.setCharacterEncoding("utf-8");
		System.out.println("[utf-8] encoding 처리함.");

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}



}
