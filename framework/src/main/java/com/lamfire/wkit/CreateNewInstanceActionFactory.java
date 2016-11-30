package com.lamfire.wkit;

import com.lamfire.wkit.action.Action;


public class CreateNewInstanceActionFactory implements ActionFactory {
    Class<? extends Action> actionClass;

    public CreateNewInstanceActionFactory(Class<? extends Action> actionClass) {
        this.actionClass = actionClass;
    }

    public Action getActionInstance() throws ActionException {
        try {
            return actionClass.newInstance();
        }catch (Exception e){
           throw new ActionException(e);
        }
    }
}
