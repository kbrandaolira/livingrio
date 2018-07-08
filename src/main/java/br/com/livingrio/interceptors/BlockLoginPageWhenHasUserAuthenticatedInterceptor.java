package br.com.livingrio.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Person;
 
public class BlockLoginPageWhenHasUserAuthenticatedInterceptor extends HandlerInterceptorAdapter {
	
	private PersonDAO personDAO;
	
	@Autowired
    public BlockLoginPageWhenHasUserAuthenticatedInterceptor(PersonDAO personDAO) {
		super();
		this.personDAO = personDAO;
	}

	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
      
    	HttpSession session = request.getSession();
    	
    	Person userAuthenticated = null;
    	
    	if(SecurityContextHolder.getContext().getAuthentication().getName() != null && !SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")){
    		
    		if (session.getAttribute("userAuthenticated") != null){
    			userAuthenticated = (Person) session.getAttribute("userAuthenticated");
    		}else{
    			
    			Person person = personDAO.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    			session.setAttribute("userAuthenticated",person);
    			
    			userAuthenticated = person;
    			
    		}
    		
    	}
    	
    	if(userAuthenticated != null){
    		
    		response.sendRedirect("index");
    		
    	}
    		
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {}
 
}