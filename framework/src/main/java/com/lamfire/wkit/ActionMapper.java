package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ObjectFactory;
import com.lamfire.wkit.action.Action;

import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("unchecked")
public class ActionMapper {
	private static final Logger LOGGER = Logger.getLogger(ActionMapper.class);

	private String servletPath;
	private Class<Action> actionClass;
	private Method actionMethod;
	private ObjectFactory<Action> actionFactory;
	private final Set<String> permissions = new HashSet<String>();
	private MethodArgumentResolver argumentResolver;

    public ActionMapper(String servletPath, Class<Action> actionClass,Method actionMethod) {
        this.servletPath = servletPath;
        this.actionClass = actionClass;
        this.actionFactory = new ObjectFactory<Action>(actionClass);
		this.actionMethod = actionMethod;
		this.argumentResolver = new MethodArgumentResolver(actionMethod);

    }

	public Object[] resolveMethodArguments(ActionContext context,Map<String, Object> parameters){
		return argumentResolver.resolveArguments(context.getHttpServletRequest(),context.getHttpServletResponse(),parameters);
	}

    public Action newAction() throws ClassNotFoundException{
		if(actionFactory == null){
			actionFactory = new ObjectFactory<Action>(actionClass);
		}
		try {
			return actionFactory.newInstance();
		} catch (Exception e) {
			throw new ActionException("Action not found:" +actionClass.getName());
		}
	}

	public Method getActionMethod() {
		return actionMethod;
	}

	public String getServletPath() {
        return servletPath;
    }

    public Class<Action> getActionClass() {
        return actionClass;
    }

    public ObjectFactory<Action> getActionFactory() {
        return actionFactory;
    }


	public void addPermission(String ... permissions){
		for(String p : permissions) {
			this.permissions.add(p);
		}
	}

	public boolean isNonePermissionAuthorities(){
		return this.permissions.isEmpty();
	}

	public Set<String> getPermissions() {
		return permissions;
	}
}
