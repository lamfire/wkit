package com.lamfire.wkit;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public final class ParameterMap extends AbstractMap<String, Object> implements Serializable {

	private static final long serialVersionUID = -3101710803248004563L;

	protected Set entries;
	protected HttpServletRequest request;

	public ParameterMap(HttpServletRequest request) {
		this.request = request;
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public Set entrySet() {
		if (this.entries == null) {
			this.entries = new HashSet();
			Enumeration enumeration = this.request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString();
				String[] values = this.request.getParameterValues(key);
				if (values == null) {
					continue;
				}
				if (values.length > 1) {
					this.entries.add(new Entry(key, values));
					continue;
				}
				this.entries.add(new Entry(key, values[0]));
			}
			if(request instanceof ServletRequestWrapper){
    			ServletRequestWrapper requestWrapper = (ServletRequestWrapper) request;
    			if (requestWrapper.getRequest() instanceof MultiPartRequestWrapper) {
    				MultiPartRequestWrapper mul = (MultiPartRequestWrapper) requestWrapper.getRequest();
    				enumeration = mul.getFileParameterNames();
    				while (enumeration.hasMoreElements()) {
    					String key = enumeration.nextElement().toString();
    					MultiPartFile[] values = mul.getMultiPartFile(key);
    					if (values == null) {
    						continue;
    					}
    					if (values.length > 1) {
    						this.entries.add(new Entry(key, values));
    						continue;
    					}
    					this.entries.add(new Entry(key, values[0]));
    				}
    			}
			}
		}
		return this.entries;
	}

	public Object get(Object key) {
		Object[] values = this.request.getParameterValues(key.toString());

		if (values == null && request instanceof ServletRequestWrapper) {
			ServletRequestWrapper requestWrapper = (ServletRequestWrapper) request;
			ServletRequest req = requestWrapper.getRequest();
			if (req instanceof MultiPartRequestWrapper) {
				MultiPartRequestWrapper mul = (MultiPartRequestWrapper) req;
				values = mul.getMultiPartFile(key.toString());
			}
		}

		if (values == null) {
			return null;
		}
		if (values.length > 1) {
			return values;
		}
		return values[0];
	}

	public Object put(String key, Object value) {
		throw new UnsupportedOperationException("read only");
	}

	public Object remove(Object key) {
		throw new UnsupportedOperationException("read only");
	}

	class Entry implements Map.Entry<String, Object> {

		private final String val$key;
		private final Object val$value;

		public Entry(String key, Object value) {
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
			throw new UnsupportedOperationException("read only");
		}

	}

}