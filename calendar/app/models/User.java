package models;

public class User {
	public String name;
	public Calendar calendar;
	public String password;
	public long id;
	private static long counter;
	
	public User(String name, String password){
		// preconditions
		assert name != null : "Parameter not allowed to be null";
		assert name.isEmpty()==false: "Empty name, User must have a name";
		
		this.name = name;
		this.password = password;
		counter++;
		this.id = counter;
		calendar = new Calendar(name+"'s first calendar", this);
		
		// postconditions
		assert this.name.equals(name);
		assert calendar != null;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public Calendar getCalendar(){
		return this.calendar;
	}
	
	
}
