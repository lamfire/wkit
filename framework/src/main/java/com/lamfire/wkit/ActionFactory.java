package com.lamfire.wkit;

import com.lamfire.wkit.action.Action;


public interface ActionFactory {

    Action getActionInstance() throws ActionException;
}
