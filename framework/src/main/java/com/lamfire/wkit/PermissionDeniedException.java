package com.lamfire.wkit;

import com.lamfire.utils.StringUtils;

import java.util.Set;

/**
 * Created by lamfire on 16/11/28.
 */
public class PermissionDeniedException extends Exception {
    private Set<String> permissions;
    public PermissionDeniedException(Set<String> permissions) {
        super("Need Permissions : "+StringUtils.join(permissions,','));
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
