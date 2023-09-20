package com.prgrms.wadiz.global.annotation;

import com.prgrms.wadiz.global.util.exception.ErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExample {
    Class<ErrorCode> value();

    String domain();
}
