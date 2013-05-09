package com.github.restmvc.jaxrs.provider;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.github.restmvc.RestmvcLogger;
import com.github.restmvc.jaxrs.RestmvcApplication;
import com.github.restmvc.view.ErrorBean;
import com.sun.jersey.api.NotFoundException;

@Provider
public class NotFoundExceptionMapper implements
		ExceptionMapper<NotFoundException> {

	@Context
	private UriInfo uriInfo;

	@Context
	private HttpServletRequest request;

	/**
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(NotFoundException exc) {
		RestmvcLogger.LOGGER.log(Level.SEVERE, "The mapping rule for the URI ("
				+ request.getRequestURI()
				+ ") could not be found, because JAX-RS did not "
				+ "have it registered with the Java @Path annotation.", exc);
		StringBuilder errorUriStr = new StringBuilder(uriInfo.getAbsolutePath()
				.toString());
		int contextPos = errorUriStr.indexOf(request.getContextPath());

		if ((contextPos > -1)
				&& (contextPos + request.getContextPath().length() < errorUriStr
						.length())) {
			errorUriStr.delete(contextPos + request.getContextPath().length(),
					errorUriStr.length());
		}

		errorUriStr.append(RestmvcApplication.PATH);
		errorUriStr.append(RestmvcApplication.URI_TEMPLATE_PREFIX_VIEW);
		errorUriStr.append(ErrorBean.URI_TEMPLATE);

		try {
			return Response.seeOther(new URI(errorUriStr.toString())).build();
		} catch (URISyntaxException e) {
		}

		return Response.serverError().build();
	}

}
