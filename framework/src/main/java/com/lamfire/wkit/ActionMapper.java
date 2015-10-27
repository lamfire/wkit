package com.lamfire.wkit;

import java.util.HashMap;
import java.util.Map;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ClassLoaderUtils;
import com.lamfire.utils.ObjectFactory;
import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.action.Action;

@SuppressWarnings("unchecked")
public class ActionMapper {
	private static final Logger LOGGER = Logger.getLogger(ActionMapper.class);
	private static final Map<String, ActionMapper> mappers = new HashMap<String, ActionMapper>();
	
	public static ActionMapper getMapper(String actionRoot,String servletPath){
		ActionMapper mapper = mappers.get(servletPath);
		if(mapper == null){
			mapper = new ActionMapper(actionRoot, servletPath);
			if(mapper.validate()){
				mappers.put(servletPath, mapper);
				LOGGER.info("[ActionMapper] : " + mapper.getActionClassName() +" - " + servletPath);
			}
		}
		return mapper;
	}
	
	private String actionRoot;
	private String servletPath;
	private String actionClassName;
	private Class<Action> actionClass;
	private ObjectFactory<Action> actionFactory;
	
	private ActionMapper(String actionRoot,String servletPath){
		this.actionRoot = actionRoot;
		this.servletPath = servletPath;
	}
	
	public Action newAction() throws ClassNotFoundException{
		if(actionFactory == null){
			actionFactory = new ObjectFactory<Action>(getActionClass());
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
	
	
	public Class<Action> getActionClass() throws ClassNotFoundException{
		if(actionClass != null){
			return actionClass;
		}
		String actionClassName = getActionClassName();
		if (actionClassName == null) {
			throw new ActionException("Not mapping action class from path : " + servletPath);
		}
		actionClass = ClassLoaderUtils.loadClass(actionClassName, ActionMapper.class);
		return actionClass;
	}

	public String getActionClassName() {
		if (StringUtils.isBlank(servletPath)) {
			return null;
		}
		
		if(actionClassName != null){
			return actionClassName;
		}

		try {
			int index = servletPath.lastIndexOf("/");
			int dotIndex = servletPath.lastIndexOf(".");
			String actionName = servletPath.substring(index + 1);
			if (dotIndex > 0) {
				actionName = actionName.substring(0, actionName.lastIndexOf("."));
			}

			if (StringUtils.isBlank(actionName)) {
				return null;
			}

			String nameSpace = null;
			if (servletPath.indexOf("/") != index) {
				nameSpace = servletPath.substring(1, index);
				nameSpace = nameSpace.replace('/', '.');
			}

			String simpleActionClassName = actionName.substring(0, 1).toUpperCase() + actionName.substring(1) + "Action";
			StringBuilder builder = new StringBuilder();

			if (actionRoot != null && !"".equals(actionRoot.trim())) {
				builder.append(actionRoot.trim());
				builder.append('.');
			}
			if (nameSpace != null && !"".equals(nameSpace.trim())) {
				builder.append(nameSpace.trim());
				builder.append('.');
			}
			builder.append(simpleActionClassName);

			this.actionClassName =  builder.toString();
			return actionClassName;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return null;
	}
	
	protected boolean validate() {
		try{
			return getActionClass() != null;
		}catch(Exception e){
			
		}
		return false;
	}
}
