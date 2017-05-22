package models;

import java.lang.reflect.Method;
import java.util.*;
import javax.inject.Inject;

import javax.persistence.*;

import cache.PsbCachable;
import com.avaje.ebean.Model;
import org.aopalliance.intercept.MethodInvocation;
import play.data.validation.*;
import play.Logger;



/**
 * Company entity managed by Ebean
 */
@Entity
public class Company extends Model {

    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Constraints.Required
    public String name;

    /**
     * Generic query helper for entity Company with id Long
     */
    public static Find<Long,Company> find = new Find<Long,Company>(){};

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Company c: Company.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }

    public static Object finder(Long key) {
        return find.byId(key);
    }

}
