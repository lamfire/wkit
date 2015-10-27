package com.lamfire.wkit;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public final class RequestMap extends AbstractMap<String, Object> implements Serializable {

	private static final long serialVersionUID = -6033101710803248003L;
	
	protected Set entries;
	protected HttpServletRequest request;

	public RequestMap(HttpServletRequest request) {
		this.request = request;
	}

	public void clear() {
		this.entries = null;
		Enumeration<String> keys = this.request.getAttributeNames();

		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			this.request.removeAttribute(key);
		}
	}

	
	public Set entrySet() {
		if (this.entries == null) {
			this.entries = new HashSet();

			Enumeration enumeration = this.request.getAttributeNames();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString() ;
				Object value = this.request.getAttribute(key);
				
				this.entries.add(new Entry(key, value) );
			}
		}
		return this.entries;
	}

	public Object get(Object key) {
		return this.request.getAttribute (key.toString());
	}

	public Object put(String key, Object value) {
		this.entries = null;
		this.request.setAttribute(key, value);

		return get(key);
	}

	public Object remove(Object key) {
		this.entries = null;

		Object value = get(key);
		this.request.removeAttribute(key.toString());

		return value;
	}
	
	class Entry implements Map.Entry<String, Object>{

		private final String val$key;
		private final Object val$value;
		
		public Entry(String key,Object value){
			val$key = key;
			val$value = value;
		}

		public boolean equals(Object obj) {
			Map.Entry entry = (Map.Entry) obj;

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
			RequestMap.this.request.setAttribute(this.val$key.toString(), obj);

			return this.val$value;
		}
		
	}
}