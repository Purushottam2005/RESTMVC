package com.github.restmvc.jaxrs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.github.restmvc.RestmvcLogger;
import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public class RequestLoggingResourceFilter implements ResourceFilter,
		ContainerRequestFilter {

	private static final String NOTIFICATION_PREFIX = "* ";
	private static final String REQUEST_PREFIX = "> ";

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return null;
	}

	@Override
	public ContainerRequest filter(ContainerRequest request) {

		StringBuilder msg = new StringBuilder();

		// print request line
		msg.append(NOTIFICATION_PREFIX).append("Server in-bound request")
				.append('\n');
		msg.append(REQUEST_PREFIX).append(request.getMethod()).append(" ")
				.append(request.getRequestUri().toASCIIString()).append('\n');

		// print request headers
		for (Map.Entry<String, List<String>> e : request.getRequestHeaders()
				.entrySet()) {
			String header = e.getKey();

			for (String value : e.getValue()) {
				msg.append(REQUEST_PREFIX).append(header).append(": ")
						.append(value).append('\n');
			}
		}

		msg.append(REQUEST_PREFIX).append(" Entity:").append('\n');
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = request.getEntityInputStream();

		try {
			ReaderWriter.writeTo(in, out);
			byte[] requestEntity = out.toByteArray();

			// print entity
			msg.append(new String(requestEntity)).append("\n");

			request.setEntityInputStream(new ByteArrayInputStream(requestEntity));
			return request;
		} catch (IOException ex) {
			throw new ContainerException(ex);
		} finally {
			RestmvcLogger.LOGGER.info(msg.toString());
		}
	}

}
