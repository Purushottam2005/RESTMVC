package com.github.restmvc.bean;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class AssetBean {

	public static final String PATH_IMG = "/img";
	public static final String PATH_JS = "/js";

	public String getImgUrl(String image) {
		if ((image == null) || (image.trim().length() == 0)) {
			return "";
		}

		return getAssetUrl(image, PATH_IMG);
	}

	public String getJsUrl(String js) {
		if ((js == null) || (js.trim().length() == 0)) {
			return "";
		}

		return getAssetUrl(js, PATH_JS);
	}

	public String getJqueryJsUrl() {
		return getJsUrl("jquery-1.9.1.min.js");
	}

	public String getJqueryDataTablesJsUrl() {
		return getJsUrl("jquery.dataTables-1.9.4.min.js");
	}

	public String getJqueryJsonJsUrl() {
		return getJsUrl("jquery.json-2.4.min.js");
	}

	public String getJqueryRestJsUrl() {
		return getJsUrl("jquery.rest-0.0.4.min.js");
	}

	public String getJquerySerializeJsonJsUrl() {
		return getJsUrl("jquery.serializeJSON-1.0.1.min.js");
	}

	public String getModernizrJsUrl() {
		return getJsUrl("modernizr-2.6.2.min.js");
	}

	protected String getAssetUrl(String asset, String assetPath) {
		if ((asset == null) || (asset.trim().length() == 0)) {
			return "";
		}

		if ((FacesContext.getCurrentInstance() == null)
				|| !(FacesContext.getCurrentInstance().getExternalContext()
						.getRequest() instanceof HttpServletRequest)) {
			return "";
		}

		HttpServletRequest request = (HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
		StringBuilder url = new StringBuilder(request.getContextPath());

		if (url.toString().endsWith("/")) {
			url.deleteCharAt(url.length() - 1);
		}

		if ((assetPath != null) && (assetPath.trim().length() > 0)) {
			if (!assetPath.trim().startsWith("/")) {
				url.append("/");
			}

			url.append(assetPath.trim());
		}

		if (url.toString().endsWith("/")) {
			url.deleteCharAt(url.length() - 1);
		}

		if (!asset.trim().startsWith("/")) {
			url.append("/");
		}

		url.append(asset.trim());
		return url.toString();
	}
}
