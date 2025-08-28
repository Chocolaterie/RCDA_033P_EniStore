package fr.eni.eni_store.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloWorldAspect {

    @Before("@annotation(helloWorld)")
    public void printHelloWorld(HelloWorld helloWorld){
        System.out.println("Hello World - " + helloWorld.message());
    }
}
