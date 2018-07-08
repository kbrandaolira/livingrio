package br.com.livingrio.interceptors;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.livingrio.dao.impl.QuestionDAO;
import br.com.livingrio.model.Person;
import br.com.livingrio.utils.CalendarUtils;

public class NotificationInterceptor extends HandlerInterceptorAdapter {

	private QuestionDAO questionDAO;

	public NotificationInterceptor(){}
	
	@Autowired
	public NotificationInterceptor( QuestionDAO questionDAO ){
		this.questionDAO = questionDAO;
		
	}
	
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
    	HttpSession session = request.getSession();
    	
    	if ( session != null  ){
    		Person person = (Person) session.getAttribute("userAuthenticated");
    		Calendar lastUpdateTime = (Calendar) session.getAttribute("lastUpdateTime");
    		
    		if ( person != null && 
    				(lastUpdateTime == null || CalendarUtils.secondsDifferenceBetweenTwoDates(lastUpdateTime, Calendar.getInstance()) > 3 ) 
    				){
    			int n = questionDAO.withoutAnswer( person.getId() );
    			session.setAttribute("questionsToAnswer", n);
    			session.setAttribute("lastUpdateTime", Calendar.getInstance());
    			
    		}
    		
    		
    	}

    	
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	
    }

	
}
