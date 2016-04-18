package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ClassLoaderUtils;
import com.lamfire.wkit.action.Action;
import com.lamfire.wkit.anno.ACTION;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-4-18
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
public class ActionRegistry {
    private static final Logger LOGGER = Logger.getLogger(ActionRegistry.class);
    private final Map<String, ActionMapper> mappers = new HashMap<String, ActionMapper>();

    private static final ActionRegistry  instance = new ActionRegistry();

    public static final ActionRegistry getInstance(){
        return instance;
    }

    private ActionRegistry(){

    }

    public void registerAll(String packageName){
        try{
        Set<Class<?>> set = ClassLoaderUtils.getClasses(packageName);
        for(Class<?> clzz : set){
            if(!Action.class.isAssignableFrom(clzz)){
                continue;
            }
            Class<Action> actionClass =  (Class<Action>)clzz;
            register(actionClass);
        }
        }catch (Exception e){
            LOGGER.warn(e.getMessage(),e);
        }
    }

    public ActionMapper register(Class<Action> actionClass){
        if(!Action.class.isAssignableFrom(actionClass)){
            return null;
        }
        ACTION actionAnno = actionClass.getAnnotation(ACTION.class);
        if(actionAnno == null){
            LOGGER.warn("["+actionClass.getName() + "] is assignable from Action,but not found 'ACTOIN' annotation.");
            return null;
        }
        String path = actionAnno.path();
        return register(path,actionClass);
    }

    public synchronized ActionMapper register(String servletName ,Class<Action> actionClass){
        if(!Action.class.isAssignableFrom(actionClass)){
            return null;
        }
        ActionMapper mapper = new ActionMapper(servletName,actionClass);
        mappers.put(servletName, mapper);
        LOGGER.debug("[register]" + servletName +" -> " + actionClass.getName());
        return mapper;
    }


    public ActionMapper lookup(String servletName){
        return mappers.get(servletName);
    }

}
