package security.handler;

import javax.inject.Inject;
import com.google.inject.Singleton;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishir on 07/06/17.
 */
@Singleton
public class userdb {

    static List<UserSubject> subjects ;


    @Inject
     public void Init() {

        subjects = new ArrayList<>();

        Logger.info("Initializing user db");
        UserSubject guest = new UserSubject("guest");
        guest.addPermissions(new UserPermission("read"));
        guest.addRoles(new SecurityRole("guest"));

        subjects.add(0, guest);

        UserSubject user = new UserSubject("user");
        user.addPermissions(new UserPermission("execute"));
        user.addRoles(new SecurityRole("user"));

        subjects.add(1, user);
    }


    public static UserSubject find_subject(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            Logger.info("No uuid present in request, assigning Guest role");
            return subjects.get(0);
        }
        Logger.info("Found uuid {}, assigning User role", uuid);
        return subjects.get(1);
    }
}
