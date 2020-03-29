package cz.muni.fi.pa165.plpm.dao.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Jakub Doczy
 */
@Constraint(validatedBy = { BadgeValidator.class })
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TrainerIsNotGymLeader {

    String message() default "Trainer must not be target gym leader";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
