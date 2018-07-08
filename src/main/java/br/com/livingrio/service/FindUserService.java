package br.com.livingrio.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Person;

@Service
public class FindUserService implements UserDetailsService{

    private PersonDAO personDAO;
    
    @Autowired
    public FindUserService(PersonDAO personDAO) {
		super();
		this.personDAO = personDAO;
	}
	public UserDetails loadUserByUsername(String username){

		final Person person = personDAO.findByEmail(username);
		
		UserDetails userDetails = new UserDetails() {
			
			private static final long serialVersionUID = 1L;

			public boolean isEnabled() {
				return true;
			}
			
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			public boolean isAccountNonLocked() {
				return true;
			}
			
			public boolean isAccountNonExpired() {

				return true;
			}
			
			public String getUsername() {
				return person.getEmail();
			}
			
			public String getPassword() {
				return person.getPassword();
			}
			
			public Collection<? extends GrantedAuthority> getAuthorities() {
				
				List<GrantedAuthority> authorities;
				
				return null;
			}
		};
		
        return userDetails;

    }
}