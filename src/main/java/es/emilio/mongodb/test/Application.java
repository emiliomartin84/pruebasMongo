package es.emilio.mongodb.test;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.NearQuery;

import com.mongodb.Mongo;


@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	private static final int MAX = 200000;
	@Autowired
	private TaxiEntryRepo repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {

		boolean importData = false;
		if(importData){
			Importer.parseCsv(getClass().getClassLoader().getResource("trip_data_1.csv").getPath().toString(),MAX, repository);
		}
		@SuppressWarnings("deprecation")
		MongoTemplate template = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "test"));
		System.out.println("lol");
		Point point = new Point(-74.006134, 40.739868);

		NearQuery query = NearQuery.near(point).maxDistance(new Distance(0.001, Metrics.KILOMETERS));


		GeoResults<TaxyEntry> results = template.geoNear(query, TaxyEntry.class, "taxyEntry");

		Iterator<GeoResult<TaxyEntry>> it = results.iterator();
		int counter=0;
		while(it.hasNext())
		{
			counter++;
			GeoResult<TaxyEntry> entry = it.next();
			logger.info("result {}",entry.toString());
		}
		logger.info("total results {}",counter);
	}
}