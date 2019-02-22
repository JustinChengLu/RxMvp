package rx.mvp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author justin on 2019/02/21 16:54
 * @version V1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Contract {
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    @interface Presenter {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    @interface Model {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    @interface View {
    }
}


