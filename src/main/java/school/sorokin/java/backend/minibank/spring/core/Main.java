package school.sorokin.java.backend.minibank.spring.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school.sorokin.java.backend.minibank.spring.core.config.ApplicationConfiguration;
import school.sorokin.java.backend.minibank.spring.core.console.OperationsConsoleListener;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);

        consoleListener.listen();

        context.close();
    }
}
