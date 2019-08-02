package kr.or.connect.homepage.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DBConfig.class})
@ComponentScan(basePackages = { "kr.or.connect.homepage.dao" })
public class ApplicationConfig {

}
