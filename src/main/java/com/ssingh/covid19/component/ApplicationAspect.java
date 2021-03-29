package com.ssingh.covid19.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApplicationAspect {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationAspect.class);

	@Before("@annotation(com.ssingh.covid19.annotation.Loggable)")
	public void beforeAdvice(JoinPoint joinPoint) {
		LOGGER.debug("Entering : {}-{} with {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), joinPoint.getArgs());
	}

	@AfterReturning(pointcut = "@annotation(com.ssingh.covid19.annotation.Loggable)", returning = "returnValue")
	public void afterAdvice(JoinPoint joinPoint, Object returnValue) {
		LOGGER.debug("Exiting : {}-{} with {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), returnValue);
	}
}
