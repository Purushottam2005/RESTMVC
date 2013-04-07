package com.github.restmvc;

import java.util.HashMap;
import java.util.Map;

import com.sun.jersey.api.view.Viewable;

public class ViewableTemplate extends Viewable {

	private Map<String, Object> attributes = null;

	public ViewableTemplate(String templateName) {
		super(templateName);
	}

	public ViewableTemplate(String templateName, Object model,
			Class<?> resolvingClass) throws IllegalArgumentException {
		super(templateName, model, resolvingClass);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttribute(String name, Object value) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}

		attributes.put(name, value);
	}

	public void removeAttribute(String name) {
		if (attributes != null) {
			attributes.remove(name);
		}
	}
}
