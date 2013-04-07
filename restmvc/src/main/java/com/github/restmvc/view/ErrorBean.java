package com.github.restmvc.view;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.github.restmvc.AbstractJaxrs;
import com.github.restmvc.RestmvcApplication;
import com.github.restmvc.ViewableTemplate;

@Path(RestmvcApplication.URI_TEMPLATE_PREFIX_VIEW + ErrorBean.URI_TEMPLATE)
@Named
@Stateless
public class ErrorBean extends AbstractJaxrs {

	public static final String URI_TEMPLATE = "/error";

	@GET
	public ViewableTemplate render() {
		return new ViewableTemplate(TEMPLATE_BASE_DIR + "/error.xhtml");
	}
}
