package security.handler;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import security.handler.SecurityRole;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishir on 10/05/17.
 */
public class UserSubject implements Subject {


    String identifier;

    public List<SecurityRole> roles;
    public List<UserPermission> permissions;
    @Override
    public List<? extends Role> getRoles() {

        return roles;
    }

    public UserSubject(String identifier) {

        this.identifier = identifier;
        roles = new ArrayList<>();
        permissions = new ArrayList<>();
    }

    @Override
    public List<? extends Permission> getPermissions() {
        return permissions;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public void addRoles(SecurityRole role) {
        roles.add(role);
    }

    public void addPermissions(UserPermission perm) {
        permissions.add(perm);
    }
}
