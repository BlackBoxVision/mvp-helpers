package io.blackbox_vision.mvphelpers.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface PresenterFactory {

    Class<?> name() default Object.class;
}
