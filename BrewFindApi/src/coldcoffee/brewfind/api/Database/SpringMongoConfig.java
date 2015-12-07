package coldcoffee.brewfind.api.Database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import coldcoffee.brewfind.api.Repositories.RepositoryPackage;

@Configuration
@EnableMongoRepositories(basePackageClasses=RepositoryPackage.class)
public class SpringMongoConfig extends AbstractMongoConfiguration{

	@Override
	protected String getDatabaseName() {
		return "brewFind";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient("localhost", 27017);
	}
	
	@Override
    protected String getMappingBasePackage() {
	    return "coldcoffee.brewfind.api.Objects";
	}

}
