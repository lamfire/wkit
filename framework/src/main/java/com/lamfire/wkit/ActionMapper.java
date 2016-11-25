package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Lists;
import com.lamfire.utils.ObjectFactory;
import com.lamfire.utils.TypeConvertUtils;
import com.lamfire.wkit.action.Action;
import com.lamfire.wkit.anno.MAPPING;
import com.lamfire.wkit.anno.PARAM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

@SuppressWarnings("unchecked")
public class ActionMapper {
	private static final Logger LOGGER = Logger.getLogger(ActionMapper.class);

	private String servletPath;
	private Class<Action> actionClass;
	private Method actionMethod;
	private ObjectFactory<Action> actionFactory;
	private final Set<String> permissions = new HashSet<String>();

    public ActionMapper(String servletPath, Class<Action> actionClass,Method actionMethod) {
        this.servletPath = servletPath;
        this.actionClass = actionClass;
        this.actionFactory = new ObjectFactory<Action>(actionClass);
		this.actionMethod = actionMethod;

    }

	public Object[] buildParams(Map<String, Object> parameters, HttpServletRequest request, HttpServletResponse response){
		Parameter[] params = actionMethod.getParameters();

		Object[] args = new Object[params.length];
		for(int i=0;i<params.length;i++){
			Class<?> type = params[i].getType();
			PARAM p = params[i].getAnnotation(PARAM.class);
			if(p != null){
				String id = p.value();
				Object val = parameters.get(id);
				args[i] = TypeConvertUtils.convert(val,type);
			}else{
				if(HttpSession.class == type){
					args[i] = request.getSession();
				}else if(HttpServletRequest.class == (type)){
					args[i] = request;
				}
				else if(HttpServletResponse.class==(type)){
					args[i] = response;
				}else if(OutputStream.class==(type)){
					try {
						args[i] = response.getOutputStream();
					}catch (Exception e){

					}
				}else if(PrintWriter.class==(type)){
					try {
						args[i] = response.getWriter();
					}catch (Exception e){

					}
				}
			}
		}
		return args;
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
