package com.min.edu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Spring &lt;mvc:interceptor&gt;를 통해 특정한 RequestMapping전에 
 * 실행되어 로직을 수행하고 수행 겨로가에 따라 흐름제어를 함
 * ex) 로그인 되어 있어야지만 흐름이 되는 화면을 제어 함 
 * @author Ym_m
 *
 */
public class Interceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// *.do 실행되기 전에 전처리를 해주는 handler
	// 로그인 정보(ServletRequest > HttpServletRequest > HttpServlet)
	// 로그인 정보를 담는 session의 "mem" = null
	
	
	// 컨트롤러 실행 전 수행 로직
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("ಠ_ಠ 인터셉터 시작");
		
		try {
			if(request.getSession().getAttribute("mem") == null) {
				response.sendRedirect("./loginform.do");
				return false;
			}
		} catch (Exception e) {
			logger.info("ಠ_ಠ 인터셉터 이름 확인");
			e.printStackTrace();
		}
		
		return true;
	}
	
	// 컨트롤러 실행 직후 수행 로직
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("(⊙_⊙;) 인터셉터종료");
		super.postHandle(request, response, handler, modelAndView);
	}

	// View 렌더링이 끝난 직후 수행
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("(⊙_⊙;) 인터셉터View 렌더링이 끝나는 직후 수행");
		super.afterCompletion(request, response, handler, ex);
	}

	
	// 비동기 호출 시 수행됨
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	
}
