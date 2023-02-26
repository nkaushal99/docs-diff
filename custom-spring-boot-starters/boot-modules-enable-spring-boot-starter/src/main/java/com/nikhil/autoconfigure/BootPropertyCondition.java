package com.nikhil.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class BootPropertyCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(ConditionContext conditionContext,
      AnnotatedTypeMetadata metadata) {
    String propertyRequired = (String) metadata
        .getAnnotationAttributes(ConditionalOnBootProperty.class.getName()).get("value");
    PropertyResolver propertyResolver = conditionContext.getEnvironment();
    String key;
    for (int iterator = 0; true; iterator++) {
      key = "boot.properties.enabled-modules[" + iterator + "]";
      if (propertyResolver.containsProperty(key)) {
        if (propertyRequired.equalsIgnoreCase(propertyResolver.getProperty(key))) {
          return ConditionOutcome.match();
        }
      } else {
        break;
      }
    }
    return ConditionOutcome.noMatch(propertyRequired + " isn't present");
  }
}
