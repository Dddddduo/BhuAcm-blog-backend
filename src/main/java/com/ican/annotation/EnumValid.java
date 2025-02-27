package com.ican.annotation;

import com.ican.validator.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 评论类型注解
 *
 * @author Dduo
 **/
@Documented
@Constraint(validatedBy = {EnumValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {

    String message() default "{javax.validation.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return 评论类型
     */
    int[] values() default {};

}