package com.ican.validator;

import com.ican.annotation.EnumValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 枚举类型校验器
 *
 * @author Dduo
 **/
public class EnumValidator implements ConstraintValidator<EnumValid, Integer> {

    private final Set<Integer> set = new HashSet<>();

    /**
     * 初始化
     *
     * @param constraintAnnotation 评论类型注解
     */
    @Override
    public void initialize(EnumValid constraintAnnotation) {
        for (int value : constraintAnnotation.values()) {
            set.add(value);
        }
    }

    /**
     * 校验方法
     *
     * @param value                      校验的值
     * @param constraintValidatorContext 上下文
     * @return 是否校验成功
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(value);
    }
}