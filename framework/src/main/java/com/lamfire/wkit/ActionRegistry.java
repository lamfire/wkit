package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ClassLoaderUtils;
import com.lamfire.utils.ClassUtils;
import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.action.Action;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;

import java.lang.reflect.Method;
import java.util.Collection;
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

    public void register(Class<Action> actionClass){
        if(!Action.class.isAssignableFrom(actionClass)){
            return;
        }
        ACTION actionAnno = actionClass.getAnnotation(ACTION.class);
        if(actionAnno == null){
            LOGGER.warn("["+actionClass.getName() + "] is assignable from Action,but not found 'ACTOIN' annotation.");
            return;
        }
        String path = actionAnno.path();
        String annoPermissions = actionAnno.permissions();

        String[] perminnions = null;
        if(StringUtils.isNotBlank(annoPermissions)){
            perminnions = StringUtils.split(annoPermissions,',');
        }
        register(path,actionClass,perminnions);
    }

    public synchronized void register(String path ,Class<Action> actionClass,String[] permissions){
        if(!Action.class.isAssignableFrom(actionClass)){
            return;
        }

        if(StringUtils.equals("/",path)){
            path ="";
        }

        Collection<Method> methods = ClassUtils.getAllDeclaredMethodsByAnnotation(actionClass,MAPPING.class);
        for(Method m : methods) {
            MAPPING mapping = m.getAnnotation(MAPPING.class);
            String uri = null;
            if(StringUtils.isBlank(path) || StringUtils.equals("/",path)){
                uri = mapping.path();
            }else{
                uri = path +"/" + mapping.path();
            }

            ActionMapper mapper = new ActionMapper(path, actionClass,m);
            if (permissions != null) {
                mapper.addPermission(permissions);
            }
            String annoPermissions = mapping.permissions();
            if(StringUtils.isNotBlank(annoPermissions)){
                mapper.addPermission(StringUtils.split(annoPermissions,','));
            }
            mappers.put(uri, mapper);
            LOGGER.debug("[ACTION]" + uri + " -> " + actionClass.getName()+"."+m.getName() + " >> " + StringUtils.join(mapper.getPermissions(),','));
        }
    }


    public ActionMapper lookup(String servletName){
        return mappers.get(servletName);
    }

}
