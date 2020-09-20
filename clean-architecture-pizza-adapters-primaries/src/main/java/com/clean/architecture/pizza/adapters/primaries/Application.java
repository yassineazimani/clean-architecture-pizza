package com.clean.architecture.pizza.adapters.primaries;

import com.clean.architecture.pizza.adapters.primaries.environment.Environment;
import com.clean.architecture.pizza.adapters.primaries.environment.EnvironmentEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        EnvironmentEnum env = EnvironmentEnum.CONSOLE;
        if(env == EnvironmentEnum.SPRING_BOOT){
            SpringApplication.run(Application.class, args);
        }else if (env == EnvironmentEnum.CONSOLE){
            Environment.runConsoleApp();
        }
    }// main()

}// Application
