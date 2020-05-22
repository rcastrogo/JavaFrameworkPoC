
package app.filters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import app.utils.Utils;


@WebFilter("/*")
public class LogFilter implements Filter {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private FilterConfig filterConfig;
	
	public void doFilter(ServletRequest request, 
			                 ServletResponse response, 
			                 FilterChain filterChain) throws IOException, ServletException {
		try {
			var req = (HttpServletRequest) request;
	
			System.out.printf("-----> %1$s %2$s %3$s\n", 
					              df.format(new Date()),
					              req.getMethod(),
					              req.getRequestURI())
			          .print(Utils.getParameters(req));			
		} finally {
			filterChain.doFilter(request, response);			
		}
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		
	}

}