package com.nikhil.autoconfigure;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

@Documented
@Retention(RUNTIME)
@Target({TYPE})
@Conditional(BootPropertyCondition.class)
public @interface ConditionalOnBootProperty {
  String value();
}
