package coldcoffee.brewfind.api.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import coldcoffee.brewfind.api.Repositories.RepositoryPackage;

// Configuration for our MongoDB instance
// The 'EnableMongoRepositories' annotation is to find where the @Repository objects are
// RepositoryPackage is just a placeholder

@Configuration
@EnableMongoRepositories(basePackageClasses=RepositoryPackage.class)
public class SpringMongoConfig extends AbstractMongoConfiguration{

	@Override
	protected String getDatabaseName() {
		return "brewfind";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient("localhost", 27017);
	}
	
	// Defined so it knows where to find the @Document objects
	@Override
    protected String getMappingBasePackage() {
	    return "coldcoffee.brewfind.api.Objects";
	}
}
