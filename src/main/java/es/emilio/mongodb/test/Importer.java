package es.emilio.mongodb.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Importer {

	private final static Logger logger  = LoggerFactory.getLogger(Importer.class);


	public static void parseCsv(String path, int number, TaxiEntryRepo repository )
	{
		try {
			List<TaxyEntry> list = new ArrayList<TaxyEntry>();
			Reader in = new FileReader(path);
			int counter = 0;
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader().withDelimiter(',').parse(in);

			repository.deleteAll();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(CSVRecord record : records)
			{
				String firstName = record.get("hack_license");
				String lastName = record.get("vendor_id");
				Double pickup_long = Double.parseDouble(record.get("pickup_longitude"));
				Double pickup_lat = Double.parseDouble(record.get("pickup_latitude"));
				if( !(pickup_lat < -180.0 || 
						pickup_lat > 180.0 ||  
						pickup_long < -180. ||
						pickup_long > 180.0) )
				{
					double [] pick_up = {pickup_long, pickup_lat};
					Date date = format.parse(record.get("pickup_datetime"));
					TaxyEntry taxy = new TaxyEntry(firstName, lastName,pick_up, date);

					//logger.info(record.toString());
					list.add(taxy);

					counter++;
					if(counter>number)
					{
						break;
					}
					if(counter % 100000==0)
					{
						logger.info("saving ... {} {}  ", list.size(), counter);
						repository.save(list);
						list.clear();
					}
					if(number % counter==0)
						logger.info(" total {} of {} ", counter, number);
				}else
				{
					logger.info(" error lat {} lot {} ", pickup_lat, pickup_long);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
