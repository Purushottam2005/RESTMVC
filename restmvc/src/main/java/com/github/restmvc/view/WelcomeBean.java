package com.github.restmvc.view;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.github.restmvc.AbstractJaxrs;
import com.github.restmvc.RestmvcApplication;
import com.github.restmvc.ViewableTemplate;

@Path(RestmvcApplication.URI_TEMPLATE_PREFIX_VIEW + WelcomeBean.URI_TEMPLATE)
@Named
@ManagedBean
@Stateless
public class WelcomeBean extends AbstractJaxrs {

	public static final String URI_TEMPLATE = "/welcome";

	@GET
	public ViewableTemplate render() {
		ViewableTemplate view = new ViewableTemplate(TEMPLATE_BASE_DIR
				+ URI_TEMPLATE + ".xhtml");
		return view;
	}

	public String getDiscountCodeUrl() {
		return getViewContextPath(null, DiscountCodeBean.URI_TEMPLATE);
	}
}
