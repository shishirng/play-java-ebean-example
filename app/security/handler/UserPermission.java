package security.handler;

import be.objectify.deadbolt.java.models.Permission;

/**
 * Created by shishir on 01/06/17.
 */
public class UserPermission implements Permission {

    String value;
    @Override
    public String getValue() {
        return value;
    }
    public UserPermission(String value) {
        this.value = value;
    }
}
