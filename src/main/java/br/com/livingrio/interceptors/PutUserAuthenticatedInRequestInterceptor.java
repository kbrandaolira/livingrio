package br.com.livingrio.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.dao.impl.QuestionDAO;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Question;
 
public class PutUserAuthenticatedInRequestInterceptor extends HandlerInterceptorAdapter {
	
	private PersonDAO personDAO;
	
	private EvaluationDAO evaluationDAO;
	
	private QuestionDAO questionDAO;
	
	@Autowired
    public PutUserAuthenticatedInRequestInterceptor( PersonDAO personDAO,EvaluationDAO evaluationDAO, QuestionDAO questionDAO ) {
		super();
		this.personDAO = personDAO;
		this.evaluationDAO = evaluationDAO;
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
    	
    	if (modelAndView != null){
    	
	    	if(SecurityContextHolder.getContext().getAuthentication().getName() != null && !SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")){
	    		
	    		Person person;
	    		
	    		if (session.getAttribute("userAuthenticated") != null){
	    			
	    			modelAndView.addObject("userAuthenticated",session.getAttribute("userAuthenticated"));
	    			
	    			person =  (Person) session.getAttribute("userAuthenticated");
	    			
	    		}else{
	    			
	    			person = personDAO.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
	
	    			session.setAttribute("userAuthenticated",person);
	    			
	    			modelAndView.addObject("userAuthenticated",person);
	    			
	    		}
	    		
	    		// TODO Possível gargalo... verificar...
	    		
	    		List<Question> questions = evaluationDAO.getQuestionsToUserWithoutAnswer(person);
	    		
	    		session.setAttribute("hasQuestionsWithoutAnswer", questions == null || questions.size() == 0 ? false : true);
	    		
    			List<Question> myQuestions = questionDAO.getQuestionsFromUser(person.getId());
	    		
	    		session.setAttribute("iHaveQuestions", myQuestions == null || myQuestions.size() == 0 ? false : true);
	    		
	    	}
	    	
    	}
    	
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
 
}