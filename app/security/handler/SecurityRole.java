package security.handler;

import be.objectify.deadbolt.java.models.Role;

/**
 * Created by shishir on 10/05/17.
 */
public class SecurityRole implements Role {

    public String name;
    @Override
    public String getName() {
        return name;
    }

    public SecurityRole(String name) {
        this.name = name;
    }
}
