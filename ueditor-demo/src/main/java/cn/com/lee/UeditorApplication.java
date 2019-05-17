package cn.com.lee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class UeditorApplication {
	
private static final Logger logger = LoggerFactory.getLogger(UeditorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(UeditorApplication.class);
        application.run(args);
        logger.info("Ueditor application started!!!");
	}

}
