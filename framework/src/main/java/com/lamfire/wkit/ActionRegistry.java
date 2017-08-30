package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ClassLoaderUtils;
import com.lamfire.utils.ClassUtils;
import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.action.Action;
import com.lamfire.wkit.action.ErrorListener;
import com.lamfire.wkit.action.ErrorListenerSupport;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Action registry
 * User: linfan
 * Date: 16-4-18
 * Time: 下午5:05
 */
public class ActionRegistry {
    private static final Logger LOGGER = Logger.getLogger(ActionRegistry.class);
    private final Map<String, ActionMapper> mappers = new HashMap<String, ActionMapper>();

    private ErrorListener errorListener = new ErrorListenerSupport();

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
                try {
                    Class<Action> actionClass = (Class<Action>) clzz;
                    register(actionClass);
                }catch (Throwable t){
                    LOGGER.warn(t.getMessage(),t);
                }
            }
        }catch (Exception e){
            LOGGER.warn(e.getMessage(),e);
        }
    }

    public void register(Class<Action> actionClass) throws MappingException {
        if(!Action.class.isAssignableFrom(actionClass)){
            return;
        }

        if(ErrorListener.class.isAssignableFrom(actionClass)){
            try {
                this.errorListener = (ErrorListener) actionClass.newInstance();
                LOGGER.info("[ERROR_ACTION] : " + actionClass.getName());
            }catch (Exception e){
                LOGGER.warn(e.getMessage(),e);
            }
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
        ActionFactory factory;
        if(actionAnno.singleton()){
            factory = new SingletonInstanceActionFactory(actionClass);
            LOGGER.info("[FOUND] : " + actionClass.getName() +" - singleton" );
        }else{
            factory = new CreateNewInstanceActionFactory(actionClass);
            LOGGER.info("[FOUND] : " + actionClass.getName());
        }
        register(path,actionClass,perminnions,factory);
    }

    public synchronized void register(String path ,Class<Action> actionClass,String[] permissions,ActionFactory factory) throws MappingException {
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
                uri = path + mapping.path();
            }

            if(mappers.containsKey(uri)){
                ActionMapper mapper = mappers.get(uri);
                throw new MappingException("Error create mapper["+uri+"] with class[" + actionClass.getName() +"],There is already defined in class '"+mapper.getActionClass().getName()+"'");
            }

            ActionMapper mapper = new ActionMapper(factory,path, actionClass,m);
            if (permissions != null) {
                mapper.addPermission(permissions);
            }
            String annoPermissions = mapping.permissions();
            if(StringUtils.isNotBlank(annoPermissions)){
                mapper.addPermission(StringUtils.split(annoPermissions,','));
            }
            mappers.put(uri, mapper);
            LOGGER.info("[MAPPING] : " + uri + " -> " + actionClass.getName()+"."+m.getName() + " >> " + StringUtils.join(mapper.getPermissions(),','));
        }
    }


    public ActionMapper lookup(String servletName){
        return mappers.get(servletName);
    }

    public ErrorListener getErrorListener() {
        return errorListener;
    }
}
