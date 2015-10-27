package com.lamfire.wkit;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;


@SuppressWarnings("unchecked")
public class ApplicationContextMap extends AbstractMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 2250446553042266858L;
	
	protected ServletContext context;
	protected Set entries;

	public ApplicationContextMap(ServletContext ctx) {
		this.context = ctx;
	}

	public void clear() {
		this.entries = null;

		Enumeration e = this.context.getAttributeNames();

		while (e.hasMoreElements())
			this.context.removeAttribute(e.nextElement().toString());
	}

	public Set entrySet() {
		if (this.entries == null) {
			this.entries = new HashSet();

			Enumeration enumeration = this.context.getAttributeNames();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString();
				Object value = this.context.getAttribute(key);
				this.entries.add(new Entry(key, value));
			}

			enumeration = this.context.getInitParameterNames();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString();
				Object value = this.context.getInitParameter(key);
				this.entries.add(new Entry(key, value));
			}
		}
		return this.entries;
	}

	public Object get(Object key) {
		String keyString = key.toString();
		Object value = this.context.getAttribute(keyString);

		return value == null ? this.context.getInitParameter(keyString) : value;
	}

	public Object put(String key, Object value) {
		this.entries = null;
		this.context.setAttribute(key.toString(), value);

		return get(key);
	}

	public Object remove(Object key) {
		this.entries = null;

		Object value = get(key);
		this.context.removeAttribute(key.toString());

		return value;
	}

	class Entry implements Map.Entry<String, Object>{

		private final String val$key;
		private final Object val$value;

		public Entry(String key, Object value) {
			val$key = key;
			val$value = value;
		}

		public boolean equals(Object obj) {
			Entry entry = (Entry) obj;

			return (this.val$key == null ? entry.getKey() == null : this.val$key.equals(entry.getKey()))
					&& (this.val$value == null ? entry.getValue() == null : this.val$value.equals(entry.getValue()));
		}

		public int hashCode() {
			return (this.val$key == null ? 0 : this.val$key.hashCode()) ^ (this.val$value == null ? 0 : this.val$value.hashCode());
		}

		public String getKey() {
			return this.val$key;
		}

		public Object getValue() {
			return this.val$value;
		}

		public Object setValue(Object obj) {
			ApplicationContextMap.this.context.setAttribute(this.val$key.toString(), obj);

			return this.val$value;
		}

	}
}