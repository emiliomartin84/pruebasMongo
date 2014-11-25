package es.emilio.mongodb.test;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TaxiEntryRepo extends MongoRepository<TaxyEntry, String>{

	public TaxyEntry findByFirstName(String firstName);
	
	public List<TaxyEntry> findByLastName(String LastName);
	
}
