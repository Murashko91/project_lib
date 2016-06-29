package by.murashko.sergey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import by.murashko.sergey.entities.*;



public class CheckUserInterceptor extends HandlerInterceptorAdapter { // implements HandlerInterceptor{

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {
		
	
		
		
		
		if ((request.getSession().getAttribute("go")==null)
				&&(request.getRequestURI().contains("main")||request.getRequestURI().contains("books"))) {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
							response.sendRedirect(request.getContextPath() );
			
		}
	}

}
