package cache;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by shishir on 11/05/17.
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME) @Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface cacheable {

}
