package school.sorokin.java.backend.minibank.spring.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("school.sorokin.java.backend.minibank.spring.core")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {
}
