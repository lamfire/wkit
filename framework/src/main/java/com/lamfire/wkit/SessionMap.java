package com.lamfire.wkit;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SuppressWarnings("unchecked")
public final class SessionMap extends AbstractMap<String,Object> implements Serializable {

	private static final long serialVersionUID = 7613413134669355241L;
	protected HttpSession session;
	protected Set entries;
	protected HttpServletRequest request;

	public SessionMap(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession(false);
	}

	public void invalidate() {
		if (this.session == null) {
			return;
		}

		synchronized (this.session) {
			this.session.invalidate();
			this.session = null;
			this.entries = null;
		}
	}

	public void clear() {
		if (this.session == null) {
			return;
		}
		synchronized (this.session) {
			this.entries = null;
			Enumeration attributeNamesEnum = this.session.getAttributeNames();
			while (attributeNamesEnum.hasMoreElements())
				this.session.removeAttribute((String) attributeNamesEnum.nextElement());
		}
	}

	public Set entrySet() {
		if (this.session == null) {
			return Collections.EMPTY_SET;
		}

		synchronized (this.session) {
			if (this.entries == null) {
				this.entries = new HashSet();

				Enumeration enumeration = this.session.getAttributeNames();

				while (enumeration.hasMoreElements()) {
					String key = enumeration.nextElement().toString();
					Object value = this.session.getAttribute(key);
					this.entries.add(new Entry(key, value));
				}
			}
		}
		return this.entries;
	}

	public Object get(Object key) {
		if (this.session == null) {
			return null;
		}

		synchronized (this.session) {
			return this.session.getAttribute(key.toString());
		}
	}

	public Object put(String key, Object value) {
		synchronized (this) {
			if (this.session == null) {
				this.session = this.request.getSession(true);
			}
		}

		synchronized (this.session) {
			this.entries = null;
			this.session.setAttribute(key.toString(), value);

			return get(key);
		}
	}

	public Object remove(Object key) {
		if (this.session == null) {
			return null;
		}

		synchronized (this.session) {
			this.entries = null;

			Object value = get(key);
			this.session.removeAttribute(key.toString());

			return value;
		}
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
			SessionMap.this.session.setAttribute(this.val$key.toString(), obj);

			return this.val$value;
		}
	
	}
}
