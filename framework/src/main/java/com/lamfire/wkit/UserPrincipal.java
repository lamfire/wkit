package com.lamfire.wkit;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;

/**
 * UserPrincipal
 * Created by lamfire on 16/11/23.
 */
public class UserPrincipal implements Principal {
    private String name;
    private String password;
    private final Collection<String> permissions = new HashSet<String>();


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getPermissions() {
        return permissions;
    }

    public void addPermissions(String... userPermissions){
        for(String p : userPermissions){
            this.permissions.add(p);
        }
    }

    public void addPermissions(Collection<String> userPermissions) {
        this.permissions.addAll(userPermissions);
    }

    public boolean hashPermissions(String permission){
        if(permission == null){
            return true;
        }
        return permissions.contains(permission);
    }

    public boolean hashPermissions(Collection<String> permissions){
        if(permissions == null){
            return true;
        }
        for(String p : permissions){
            if(!hashPermissions(p)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
