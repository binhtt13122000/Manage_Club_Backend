package binhtt.dev.manage.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = UserUniqueValidator.class)
@Retention(RUNTIME)
public @interface Unique {
    String message() default "This value is taken!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String fieldName() default "";
    String className() default "";
}
