package es.emilio.mongodb.test;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

public class TaxyEntry {

	@Id
	private String id;
	
	@PersistenceConstructor
	public TaxyEntry(String firstName, String lastName,
			double[] pick_up, Date pick_up_date) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.pick_up = pick_up;
		this.pick_up_date = pick_up_date;
	}

	private String firstName;
	
	private String lastName;
	
	private double [] pick_up;
	
	private Date pick_up_date;
	
	public TaxyEntry(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString()
	{
		return String.format("Taxi[id=%s], firstName=[%s], lastName=[%s], pickUp=[%s,%s], pickUpDate=[%s]", id, firstName, lastName, pick_up[0], pick_up[1], pick_up_date.toString());
	}
	
}
