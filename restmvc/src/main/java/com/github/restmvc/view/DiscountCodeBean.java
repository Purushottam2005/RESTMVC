package com.github.restmvc.view;

import java.util.List;
import java.util.logging.Level;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.codehaus.jackson.map.ObjectMapper;

import com.github.restmvc.AbstractJaxrs;
import com.github.restmvc.RestmvcApplication;
import com.github.restmvc.RestmvcLogger;
import com.github.restmvc.ViewableTemplate;
import com.github.restmvc.model.DiscountCode;

@Path(RestmvcApplication.URI_TEMPLATE_PREFIX_VIEW
		+ DiscountCodeBean.URI_TEMPLATE)
@Named
@ManagedBean
@Stateless
public class DiscountCodeBean extends AbstractJaxrs {

	public static final String URI_TEMPLATE = "/discountCode";
	public static final String REQ_ATTR_DISCOUNT_CODE_LIST = "DiscountCodeBean.discountCodeList";

	@PersistenceContext
	EntityManager em;

	@GET
	public ViewableTemplate render() {
		ViewableTemplate view = new ViewableTemplate(TEMPLATE_BASE_DIR
				+ URI_TEMPLATE + ".xhtml");

		TypedQuery<DiscountCode> query = em.createNamedQuery(
				DiscountCode.FIND_ALL, DiscountCode.class);
		List<DiscountCode> disCodeList = query.getResultList();
		setAttribute(REQ_ATTR_DISCOUNT_CODE_LIST, disCodeList);

		return view;
	}

	public String getDataTablesJson() {
		if (!isValidRequest()
				|| (getAttribute(REQ_ATTR_DISCOUNT_CODE_LIST) == null)) {
			RestmvcLogger.LOGGER.log(Level.SEVERE,
					"DiscountCodeBean - getDataTablesJson - "
							+ "There is no List of DiscountCode Objects.");
			return "[]";
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper
					.writeValueAsString(getAttribute(REQ_ATTR_DISCOUNT_CODE_LIST));
		} catch (Exception e) {
			RestmvcLogger.LOGGER
					.log(Level.SEVERE,
							"DiscountCodeBean - getDataTablesJson - "
									+ "Writing List of DiscountCode Objects to JSON exception: "
									+ e.getMessage(), e);
		}

		return "[]";
	}

	public String getWelcomeUrl() {
		return getViewContextPath(null, WelcomeBean.URI_TEMPLATE);
	}
}
