package com.revature.web.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

//Logging cross-cutting concern.
@Aspect
@Component
public class LoggingAspect {

	@Pointcut("within(com.revature..*)")
	public void logAll(){}

	@Before("logAll()") // reuse logAll's pointcut
	public void logMethodStart(JoinPoint jp){
		String methodSig = extractMethodSignature(jp);
		String argStr = Arrays.toString(jp.getArgs());
		System.out.println(methodSig + " invoked at " + LocalDateTime.now());
		System.out.println("Input Arguments: " + argStr);
	}

	@AfterReturning(pointcut = "logAll()", returning = "returned")
	public void logMethodReturn(JoinPoint jp, Object returned){
		String methodSig = extractMethodSignature(jp);
		System.out.println(methodSig + " successfully returned at " + LocalDateTime.now());
		System.out.println("Object returned: " + returned);
	}

	@AfterThrowing(pointcut = "logAll()", throwing = "e")
	public void errorOccurrence(JoinPoint jp, Exception e){
		String methodSig = extractMethodSignature(jp);
		System.out.println(e + "was thrown in method " + methodSig + " at " + LocalDateTime.now());
	}

	public String extractMethodSignature(JoinPoint jp){
		return  jp.getTarget().getClass().toString()
				+ "."
				+ jp.getSignature().getName();
	}

}
