package br.com.livingrio.config.auth.facebook;

import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import br.com.livingrio.config.ApplicationInfo;

public class CustomFacebookConnectionFactory extends FacebookConnectionFactory {

	public CustomFacebookConnectionFactory() {
		super(ApplicationInfo.FACEBOOK_AUTH_ID, ApplicationInfo.FACEBOOK_AUTH_SECRET);
	}

}
