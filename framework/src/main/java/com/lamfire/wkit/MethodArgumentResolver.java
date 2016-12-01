package com.lamfire.wkit;

import com.lamfire.json.JSON;
import com.lamfire.utils.StringUtils;
import com.lamfire.utils.TypeConvertUtils;
import com.lamfire.wkit.anno.PARAM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Resolver HttpRequest parameters to method arguments
 * Created by lamfire on 16/11/28.
 */
public class MethodArgumentResolver {
    private MethodParameter[] methodParameters;

    public MethodArgumentResolver(Method method){
        Parameter[] parameters = method.getParameters();
        this.methodParameters = new MethodParameter[parameters.length];
        for(int i=0;i<parameters.length;i++){
            MethodParameter mp = new MethodParameter();
            mp.setMethod(method);
            mp.setIndex(i);
            mp.setParameterType(parameters[i].getType());
            mp.setParameterAnnotations(parameters[i].getAnnotations());

            PARAM p = parameters[i].getAnnotation(PARAM.class);
            if(p != null){
                String name = p.value();
                mp.setParameterName(name);
            }

            this.methodParameters[i] = mp;
        }
    }

    public Object[] resolveArguments(ActionContext context,HttpServletRequest request, HttpServletResponse response,Map<String,Object> requestParameters){
        Object[] args = new Object[methodParameters.length];
        for(int i=0;i<methodParameters.length;i++){
            String parameterName = methodParameters[i].getParameterName();
            Class<?> type = methodParameters[i].getParameterType();

            if(StringUtils.isNotBlank(parameterName)){
                Object val = requestParameters.get(parameterName);
                if(val == null){
                    continue;
                }
                if(type.isInstance(val)){
                    args[i] = val;
                }else {
                    args[i] = TypeConvertUtils.convert(val, type);
                }
                continue;
            }

            if(JSON.class == type){
                args[i] = new JSON(requestParameters);
                continue;
            }

            if(Parameters.class == type){
                args[i] = new Parameters(requestParameters);
                continue;
            }

            if(HttpSession.class == type){
                args[i] = request.getSession();
                continue;
            }

            if(ActionContext.class == type){
                args[i] = context;
                continue;
            }

            if(HttpServletRequest.class == (type)){
                args[i] = request;
                continue;
            }


            if(HttpServletResponse.class==(type)){
                args[i] = response;
                continue;
            }

            if(OutputStream.class==(type)){
                try {
                    args[i] = response.getOutputStream();
                }catch (Exception e){

                }
                continue;
            }

            if(PrintWriter.class==(type)){
                try {
                    args[i] = response.getWriter();
                }catch (Exception e){

                }
                continue;
            }

        }
        return args;
    }
}
