package by.murashko.sergey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class CheckUserInterceptor extends HandlerInterceptorAdapter { // implements HandlerInterceptor{

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (request.getSession().isNew()&&(request.getRequestURI().contains("check-user")||request.getRequestURI().contains("mainpage"))) {

			//User user = (User) modelAndView.getModel().get("user");
			/*if (false) {
				//response.sendRedirect(request.getContextPath() + "/login");
			}*/

		}
	}

}
