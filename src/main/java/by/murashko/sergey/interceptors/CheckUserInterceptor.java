package by.murashko.sergey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import by.murashko.sergey.entities.*;

public class CheckUserInterceptor extends HandlerInterceptorAdapter { // implements
																		// HandlerInterceptor{

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
try{
		if ((request.getSession().getAttribute("go") == null) && (request.getRequestURI().contains("exit")
				|| request.getRequestURI().contains("books") || (request.getRequestURI().contains("admin")
						&& ((Users) request.getSession().getAttribute("user")).getIsAdmin() != 1))) {

			response.sendRedirect(request.getContextPath());

		}
}catch(Exception e){ e.printStackTrace(); response.sendRedirect(request.getContextPath());}
	}

}
