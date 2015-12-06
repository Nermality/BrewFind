package coldcoffee.brewfind.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class BrewFindApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { 
	 	return application.sources(BrewFindApplication.class); 
	 } 
	 
	 
	public static void main(String[] args) { 
	 	new BrewFindApplication() 
	 		.configure(new SpringApplicationBuilder(BrewFindApplication.class)) 
			.run(args); 
	} 

}
