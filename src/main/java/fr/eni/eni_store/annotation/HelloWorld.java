package fr.eni.eni_store.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HelloWorld {

    // 1 Type: 2 : nomDuParam() : default la valeur par defaut
    String message() default "TODO";
}
