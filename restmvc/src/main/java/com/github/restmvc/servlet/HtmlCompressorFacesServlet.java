package com.github.restmvc.servlet;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ProjectStage;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.servlet.BufferedHttpServletResponse;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

// RESTMVC-Start
// import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFWebConfigParam;
// RESTMVC-End

/**
 * Copied from the final Java class javax.faces.webapp.FacesServlet.java from
 *
 * http://grepcode.com/file/repo1 .maven.org/maven2/org.apache.myfaces.core
 * /myfaces-api/2.1.6/javax/faces/webapp/FacesServlet.java
 *
 * so that a HTML compressor could be integrated for compressing the HTML,
 * in-line CSS, and in-line JavaScript. The compressing can be controlled with
 * init params and the javax.faces.PROJECT_STAGE. When the project stage is set
 * to Development, then compressing is turned off all together. Changes marked
 * with RESTMVC-Start and RESTMVC-End.
 *
 * see Javadoc of <a
 * href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html"
 * >JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: struberg $)
 * @version $Revision: 1188235 $ $Date: 2011-10-24 12:09:33 -0500 (Mon, 24 Oct
 *          2011) $
 */
public class HtmlCompressorFacesServlet implements Servlet {

	// RESTMVC-Start
	public static final String PARAM_COMPRESS_CSS = "HtmlCompressorFacesServlet.COMPRESS_CSS";
	public static final String PARAM_COMPRESS_JAVASCRIPT = "HtmlCompressorFacesServlet.COMPRESS_JAVASCRIPT";

	private boolean _compressCss = true;
	private boolean _compressJS = true;
	// RESTMVC-End

	private static final Logger log = Logger.getLogger(FacesServlet.class
			.getName());

	/**
	 * Comma separated list of URIs of (additional) faces config files. (e.g.
	 * /WEB-INF/my-config.xml)See JSF 1.0 PRD2, 10.3.2 Attention: You do not
	 * need to put /WEB-INF/faces-config.xml in here.
	 */
	// RESTMVC-Start
	// @JSFWebConfigParam(since="1.1")
	// RESTMVC-End
	public static final String CONFIG_FILES_ATTR = "javax.faces.CONFIG_FILES";

	/**
	 * Identify the Lifecycle instance to be used.
	 */
	// RESTMVC-Start
	// @JSFWebConfigParam(since="1.1")
	// RESTMVC-End
	public static final String LIFECYCLE_ID_ATTR = "javax.faces.LIFECYCLE_ID";

	private static final String SERVLET_INFO = "HtmlCompressorFacesServlet of the MyFaces API implementation";

	private ServletConfig _servletConfig;
	private FacesContextFactory _facesContextFactory;
	private Lifecycle _lifecycle;

	public HtmlCompressorFacesServlet() {
		super();
	}

	public void destroy() {
		_servletConfig = null;
		_facesContextFactory = null;
		_lifecycle = null;
		if (log.isLoggable(Level.FINEST)) {
			log.finest("destroy");
		}
	}

	public ServletConfig getServletConfig() {
		return _servletConfig;
	}

	public String getServletInfo() {
		return SERVLET_INFO;
	}

	private String getLifecycleId() {
		// 1. check for Servlet's init-param
		// 2. check for global context parameter
		// 3. use default Lifecycle Id, if none of them was provided
		String serLifecycleId = _servletConfig
				.getInitParameter(LIFECYCLE_ID_ATTR);
		String appLifecycleId = _servletConfig.getServletContext()
				.getInitParameter(LIFECYCLE_ID_ATTR);
		appLifecycleId = serLifecycleId == null ? appLifecycleId
				: serLifecycleId;
		return appLifecycleId != null ? appLifecycleId
				: LifecycleFactory.DEFAULT_LIFECYCLE;
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		if (log.isLoggable(Level.FINEST)) {
			log.finest("init begin");
		}
		_servletConfig = servletConfig;
		_facesContextFactory = (FacesContextFactory) FactoryFinder
				.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		// TODO: null-check for Weblogic, that tries to initialize Servlet
		// before ContextListener

		// Javadoc says: Lifecycle instance is shared across multiple
		// simultaneous requests, it must be implemented in a
		// thread-safe manner.
		// So we can acquire it here once:
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
				.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		_lifecycle = lifecycleFactory.getLifecycle(getLifecycleId());
		if (log.isLoggable(Level.FINEST)) {
			log.finest("init end");
		}

		// RESTMVC-Start
		String compressCssStr = servletConfig.getServletContext()
				.getInitParameter(PARAM_COMPRESS_CSS);

		if (compressCssStr != null) {
			_compressCss = Boolean.valueOf(compressCssStr);
		}

		String compressJsStr = servletConfig.getServletContext()
				.getInitParameter(PARAM_COMPRESS_JAVASCRIPT);

		if (compressJsStr != null) {
			_compressJS = Boolean.valueOf(compressJsStr);
		}
		// RESTMVC-End
	}

	public void service(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		// If the request and response arguments to this method are not
		// instances of HttpServletRequest and
		// HttpServletResponse, respectively, the results of invoking this
		// method are undefined.
		// In this case ClassCastException
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String pathInfo = httpRequest.getPathInfo();

		// if it is a prefix mapping ...

		/*
		 * This method must respond to requests that start with the following
		 * strings by invoking the sendError method on the response argument
		 * (cast to HttpServletResponse), passing the code
		 * HttpServletResponse.SC_NOT_FOUND as the argument.
		 *
		 * /WEB-INF/ /WEB-INF /META-INF/ /META-INF
		 */
		if (pathInfo != null
				&& (pathInfo.startsWith("/WEB-INF") || pathInfo
						.startsWith("/META-INF"))) {
			StringBuffer buffer = new StringBuffer();

			buffer.append(" Someone is trying to access a secure resource : ")
					.append(pathInfo);
			buffer.append("\n remote address is ").append(
					httpRequest.getRemoteAddr());
			buffer.append("\n remote host is ").append(
					httpRequest.getRemoteHost());
			buffer.append("\n remote user is ").append(
					httpRequest.getRemoteUser());
			buffer.append("\n request URI is ").append(
					httpRequest.getRequestURI());

			log.warning(buffer.toString());

			// Why does RI return a 404 and not a 403, SC_FORBIDDEN ?

			((HttpServletResponse) response)
					.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// If none of the cases described above in the specification for this
		// method apply to the servicing of this
		// request, the following action must be taken to service the request:
		if (log.isLoggable(Level.FINEST)) {
			log.finest("service begin");
		}

		// Acquire a FacesContext instance for this request.
		FacesContext facesContext = prepareFacesContext(request, response);

		// RESTMVC-Start
		HttpServletResponse originalResponse = null;
		BufferedHttpServletResponse bufferedWrapper = null;
		ProjectStage projectStage = facesContext.getApplication()
				.getProjectStage();

		if (!ProjectStage.Development.equals(projectStage)) {
			originalResponse = (HttpServletResponse) response;
			bufferedWrapper = new BufferedHttpServletResponse(originalResponse);
			response = bufferedWrapper;
			facesContext = prepareFacesContext(request, response);
		}
		// RESTMVC-End

		try {
			// jsf 2.0 : get the current ResourceHandler and
			// check if it is a resource request, if true
			// delegate to ResourceHandler, else continue with
			// the lifecycle.
			// Acquire the ResourceHandler for this request by calling
			// Application.getResourceHandler().
			ResourceHandler resourceHandler = facesContext.getApplication()
					.getResourceHandler();

			// Call
			// ResourceHandler.isResourceRequest(javax.faces.context.FacesContext).
			if (resourceHandler.isResourceRequest(facesContext)) {
				// If this returns true call
				// ResourceHandler.handleResourceRequest(javax.faces.context.FacesContext).
				resourceHandler.handleResourceRequest(facesContext);
			} else {
				// If this returns false, handle as follows:
				// call Lifecycle.execute(javax.faces.context.FacesContext)
				_lifecycle.execute(facesContext);
				// followed by
				// Lifecycle.render(javax.faces.context.FacesContext).
				_lifecycle.render(facesContext);
			}
		} catch (FacesException e) {
			// If a FacesException is thrown in either case

			// extract the cause from the FacesException
			Throwable cause = e.getCause();
			if (cause == null) {
				// If the cause is null extract the message from the
				// FacesException, put it inside of a new
				// ServletException instance, and pass the FacesException
				// instance as the root cause, then
				// rethrow the ServletException instance.
				throw new ServletException(e.getLocalizedMessage(), e);
			} else if (cause instanceof ServletException) {
				// If the cause is an instance of ServletException, rethrow the
				// cause.
				throw (ServletException) cause;
			} else if (cause instanceof IOException) {
				// If the cause is an instance of IOException, rethrow the
				// cause.
				throw (IOException) cause;
			} else {
				// Otherwise, create a new ServletException instance, passing
				// the message from the cause,
				// as the first argument, and the cause itself as the second
				// argument.
				throw new ServletException(cause.getLocalizedMessage(), cause);
			}
		} finally {
			// In a finally block, FacesContext.release() must be called.
			facesContext.release();
		}

		// RESTMVC-Start
		if (!ProjectStage.Development.equals(projectStage)) {
			String html = bufferedWrapper.getBufferAsString();
			HtmlCompressor compressor = new HtmlCompressor();

			if (_compressCss) {
				compressor.setCompressCss(true);
				// --line-break param for Yahoo YUI Compressor
				compressor.setYuiCssLineBreak(80);
			}

			if (_compressJS) {
				compressor.setCompressJavaScript(true);
				// --disable-optimizations param for Yahoo YUI Compressor
				compressor.setYuiJsDisableOptimizations(true);
				// --line-break param for Yahoo YUI Compressor
				compressor.setYuiJsLineBreak(-1);
				// --nomunge param for Yahoo YUI Compressor
				compressor.setYuiJsNoMunge(true);
				// --preserve-semi param for Yahoo YUI Compressor
				compressor.setYuiJsPreserveAllSemiColons(true);
			}

			html = compressor.compress(html);
			originalResponse.setContentType(bufferedWrapper.getContentType());
			originalResponse.setContentLength(html.length());
			originalResponse.getWriter().write(html);
		}

		projectStage = null;
		bufferedWrapper = null;
		// RESTMVC-End

		if (log.isLoggable(Level.FINEST)) {
			log.finest("service end");
		}
	}

	private FacesContext prepareFacesContext(ServletRequest request,
			ServletResponse response) {
		FacesContext facesContext = _facesContextFactory.getFacesContext(
				_servletConfig.getServletContext(), request, response,
				_lifecycle);
		return facesContext;
	}
}