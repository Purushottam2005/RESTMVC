package com.github.restmvc.jaxrs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.github.restmvc.RestmvcLogger;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ContainerResponseWriter;
import com.sun.jersey.spi.container.ResourceFilter;

public class ResponseLoggingResourceFilter implements ResourceFilter,
		ContainerResponseFilter {

	private static final String NOTIFICATION_PREFIX = "* ";
	private static final String RESPONSE_PREFIX = "< ";

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return null;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return this;
	}

	@Override
	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		response.setContainerResponseWriter(new Adapter(response
				.getContainerResponseWriter()));
		return response;
	}

	private final class Adapter implements ContainerResponseWriter {

		private final ContainerResponseWriter crw;
		private ContainerResponse response;
		private ByteArrayOutputStream baos;
		private StringBuilder msg = new StringBuilder();

		Adapter(ContainerResponseWriter crw) {
			this.crw = crw;
		}

		public OutputStream writeStatusAndHeaders(long contentLength,
				ContainerResponse response) throws IOException {
			// print response line
			msg.append(NOTIFICATION_PREFIX).append("Server out-bound response")
					.append('\n');
			msg.append(RESPONSE_PREFIX)
					.append(Integer.toString(response.getStatus()))
					.append('\n');

			// print response headers
			for (Map.Entry<String, List<Object>> e : response.getHttpHeaders()
					.entrySet()) {
				String header = e.getKey();

				for (Object value : e.getValue()) {
					msg.append(RESPONSE_PREFIX).append(header).append(": ")
							.append(ContainerResponse.getHeaderValue(value))
							.append('\n');
				}
			}

			msg.append(RESPONSE_PREFIX).append(" Entity:").append('\n');
			this.response = response;
			return this.baos = new ByteArrayOutputStream();
		}

		public void finish() throws IOException {
			byte[] entity = baos.toByteArray();

			// print entity
			msg.append(new String(entity)).append("\n");

			// Output to log
			RestmvcLogger.LOGGER.info(msg.toString());

			// Write out the headers and buffered entity
			OutputStream out = crw.writeStatusAndHeaders(-1, response);
			out.write(entity);
			crw.finish();
		}
	}

}
