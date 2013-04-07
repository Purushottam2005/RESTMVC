package com.github.restmvc;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.github.restmvc.view.ErrorBean;
import com.github.restmvc.view.WelcomeBean;

@ApplicationPath(RestmvcApplication.PATH)
public class RestmvcApplication extends Application {

	public static final String PATH = "/app";
	public static final String URI_TEMPLATE_PREFIX_VIEW = "/view";
	public static final String URI_TEMPLATE_PREFIX_RESOURCE = "/resource";

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> jaxrsClasses = new HashSet<Class<?>>();

		// JAX-RS Views
		jaxrsClasses.add(ErrorBean.class);
		jaxrsClasses.add(WelcomeBean.class);

		// JAX-RS Providers, MessageBodyWriters, and MessageBodyReaders
		jaxrsClasses.add(JsonMessageProvider.class);
		jaxrsClasses.add(ViewableTemplateMessageBodyWriter.class);

		return jaxrsClasses;
	}
}
