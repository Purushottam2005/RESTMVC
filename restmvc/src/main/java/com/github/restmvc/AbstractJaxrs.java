package com.github.restmvc;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.omnifaces.util.Faces;

public abstract class AbstractJaxrs {

	public static final String TEMPLATE_BASE_DIR = "/WEB-INF/view";

	@Context
	protected HttpServletRequest request;

	protected boolean isValidRequest() {
		if (request != null) {
			return true;
		}

		try {
			request = Faces.getRequest();
		} catch (Exception e) {
			return false;
		}

		if (request != null) {
			return true;
		}

		return false;
	}

	protected void setAttribute(String name, Object value) {
		if (!isValidRequest()) {
			return;
		}

		request.setAttribute(name, value);
	}

	protected Object getAttribute(String name) {
		if (!isValidRequest()) {
			return null;
		}

		return request.getAttribute(name);
	}

	protected void removeAttribute(String name) {
		if (!isValidRequest()) {
			return;
		}

		request.removeAttribute(name);
	}

	protected Enumeration<String> getParameterNames() {
		if (!isValidRequest()) {
			return null;
		}

		return request.getParameterNames();
	}

	protected String[] getParameterValues(String name) {
		if (!isValidRequest()) {
			return null;
		}

		return request.getParameterValues(name);
	}

	protected HttpSession getSession() {
		if (!isValidRequest()) {
			return null;
		}

		return request.getSession();
	}

	protected HttpSession getSession(boolean create) {
		if (!isValidRequest()) {
			return null;
		}

		return request.getSession(create);
	}

	protected String getViewContextPath(String additionalUriTemplatePrefix,
			String viewUriTemplate) {
		if ((viewUriTemplate == null) || (viewUriTemplate.trim().length() == 0)) {
			return "";
		}

		if (!isValidRequest()) {
			return "";
		}

		StringBuilder path = new StringBuilder(request.getContextPath());
		path.append(RestmvcApplication.PATH);

		if ((additionalUriTemplatePrefix != null)
				&& (additionalUriTemplatePrefix.trim().length() > 0)
				&& !RestmvcApplication.URI_TEMPLATE_PREFIX_VIEW
						.equals(additionalUriTemplatePrefix.trim())) {
			if (!additionalUriTemplatePrefix.trim().startsWith("/")) {
				path.append("/");
			}

			path.append(additionalUriTemplatePrefix.trim());

			if (path.toString().endsWith("/")) {
				path.deleteCharAt(path.length() - 1);
			}
		}

		path.append(RestmvcApplication.URI_TEMPLATE_PREFIX_VIEW);

		if (!viewUriTemplate.trim().startsWith("/")) {
			path.append("/");
		}

		path.append(viewUriTemplate.trim());
		return path.toString();
	}

	protected String getResourceContextPath(String additionalUriTemplatePrefix,
			String resourceUriTemplate) {
		if ((resourceUriTemplate == null)
				|| (resourceUriTemplate.trim().length() == 0)) {
			return "";
		}

		if (!isValidRequest()) {
			return "";
		}

		StringBuilder path = new StringBuilder(request.getContextPath());
		path.append(RestmvcApplication.PATH);

		if ((additionalUriTemplatePrefix != null)
				&& (additionalUriTemplatePrefix.trim().length() > 0)
				&& !RestmvcApplication.URI_TEMPLATE_PREFIX_RESOURCE
						.equals(additionalUriTemplatePrefix.trim())) {
			if (!additionalUriTemplatePrefix.trim().startsWith("/")) {
				path.append("/");
			}

			path.append(additionalUriTemplatePrefix.trim());

			if (path.toString().endsWith("/")) {
				path.deleteCharAt(path.length() - 1);
			}
		}

		path.append(RestmvcApplication.URI_TEMPLATE_PREFIX_RESOURCE);

		if (!resourceUriTemplate.trim().startsWith("/")) {
			path.append("/");
		}

		path.append(resourceUriTemplate.trim());
		return path.toString();
	}
}
