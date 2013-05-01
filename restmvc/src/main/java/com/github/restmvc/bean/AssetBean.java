package com.github.restmvc.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ManagedBean
public class AssetBean {

	public static final String PATH_CSS = "/css";
	public static final String PATH_IMAGES = "/images";
	public static final String PATH_JS = "/js";

	public String getCssUrl(String css) {
		if ((css == null) || (css.trim().length() == 0)) {
			return "";
		}

		return getAssetUrl(css, PATH_CSS);
	}

	public String getFontAwesome_IE7_CssUrl() {
		return getCssUrl("font-awesome-ie7.min.css");
	}

	public String getGroundworkCssUrl() {
		return getCssUrl("groundwork.css");
	}

	public String getGroundwork_IE_CssUrl() {
		return getCssUrl("groundwork-ie.css");
	}

	public String getNoDatauriCssUrl() {
		return getCssUrl("no-datauri.css");
	}

	public String getNoSvgCssUrl() {
		return getCssUrl("no-svg.css");
	}

	public String getPlaceholderPolyfillCssUrl() {
		return getCssUrl("placeholder_polyfill.min.css");
	}

	public String getImageUrl(String image) {
		if ((image == null) || (image.trim().length() == 0)) {
			return "";
		}

		return getAssetUrl(image, PATH_IMAGES);
	}

	public String getJsUrl(String js) {
		if ((js == null) || (js.trim().length() == 0)) {
			return "";
		}

		return getAssetUrl(js, PATH_JS);
	}

	public String getGroundworkAllJsUrl() {
		return getJsUrl("groundwork.all.js");
	}

	public String getJqueryJsUrl() {
		return getJsUrl("jquery-1.9.1.min.js");
	}

	public String getModernizrJsUrl() {
		return getJsUrl("modernizr-2.6.2.min.js");
	}

	public String getPluginsJqueryCycle2JsUrl() {
		return getJsUrl("plugins/jquery.cycle2.js");
	}

	public String getPluginsJqueryDataTablesJsUrl() {
		return getJsUrl("plugins/jquery.dataTables-1.9.4.min.js");
	}

	public String getPluginsJqueryJsonJsUrl() {
		return getJsUrl("plugins/jquery.json-2.4.min.js");
	}

	public String getPluginsJqueryRestJsUrl() {
		return getJsUrl("plugins/jquery.rest-0.0.4.min.js");
	}

	public String getPluginsJquerySerializeJsonJsUrl() {
		return getJsUrl("plugins/jquery.serializeJSON-1.0.1.min.js");
	}

	public String getPluginsPlaceholder_polyfillJqueryJsUrl() {
		return getJsUrl("plugins/placeholder_polyfill.jquery-2.0.6.min.js");
	}

	public String getRespondJsUrl() {
		return getJsUrl("respond-1.1.0.min.js");
	}

	public String getSelectivizrJsUrl() {
		return getJsUrl("selectivizr-1.0.2.min.js");
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
