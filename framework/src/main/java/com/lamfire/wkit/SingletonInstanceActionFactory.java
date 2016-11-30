package com.lamfire.wkit;

import com.lamfire.wkit.action.Action;


public class SingletonInstanceActionFactory implements ActionFactory {
    Class<? extends Action> actionClass;
    private Action instance;

    public SingletonInstanceActionFactory(Class<? extends Action> actionClass){
        this.actionClass = actionClass;
        try {
            this.instance = actionClass.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Action getActionInstance()throws ActionException{
        if(instance == null){
            throw new ActionException("Cannot create instance :" + actionClass);
        }
        return this.instance;
    }
}
