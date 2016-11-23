package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ObjectFactory;
import com.lamfire.wkit.action.Action;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ActionMapper {
	private static final Logger LOGGER = Logger.getLogger(ActionMapper.class);

	private String servletPath;
	private Class<Action> actionClass;
	private ObjectFactory<Action> actionFactory;
	private final Set<String> permissions = new HashSet<String>();

    public ActionMapper(String servletPath, Class<Action> actionClass) {
        this.servletPath = servletPath;
        this.actionClass = actionClass;
        this.actionFactory = new ObjectFactory<Action>(actionClass);
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
	
	public Action newAction(Map<String, Object> propertys) throws ClassNotFoundException{
		Action action = newAction() ;
		actionFactory.setProperties(action, propertys);
		return action;
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
