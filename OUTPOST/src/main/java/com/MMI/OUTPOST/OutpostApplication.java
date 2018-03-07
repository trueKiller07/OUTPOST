package com.MMI.OUTPOST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
*
* @author Sameer Sharma
*/

@SpringBootApplication
@EnableResourceServer
public class OutpostApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OutpostApplication.class, args);
	}
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
 
    private static Class<OutpostApplication> applicationClass = OutpostApplication.class;
}

@RestController
class TestController{
	
		@RequestMapping(method=RequestMethod.POST,value = "/protected/hello")
		public String HelloWorld() {
			
			return "BRAVO";
		}
}
