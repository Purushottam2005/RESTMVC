package com.github.restmvc.jaxrs.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;

import com.github.restmvc.jaxrs.ViewableTemplate;
import com.sun.jersey.spi.template.ResolvedViewable;
import com.sun.jersey.spi.template.TemplateContext;

public class ViewableTemplateMessageBodyWriter implements
		MessageBodyWriter<ViewableTemplate> {

	@Context
	private UriInfo ui;

	@Context
	private TemplateContext tc;

	@Context
	private HttpServletRequest request;

	@Override
	public long getSize(ViewableTemplate t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return ViewableTemplate.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(ViewableTemplate t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		final ResolvedViewable<?> rv = tc.resolveViewable(t, ui);

		if (rv == null) {
			throw new IOException(
					"The Facelets name, "
							+ t.getTemplateName()
							+ ", could not be resolved to a fully qualified Facelets template name.");
		}

		if (t.getAttributes() != null) {
			for (String name : t.getAttributes().keySet()) {
				Object value = t.getAttributes().get(name);

				if (value != null) {
					request.setAttribute(name, value);
				}
			}
		}

		rv.writeTo(entityStream);
	}
}
