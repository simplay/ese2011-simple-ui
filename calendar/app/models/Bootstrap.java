package models;

import java.util.Date;

import play.jobs.*;

@OnApplicationStart
public class Bootstrap extends Job {
    
    public void doJob() {
    	// Event(Date start, Date end, String name, boolean is_visible)
    	
    	User user;
    	Event event;
    	Date now = new Date();
    	
    	
    	user = new User("simplay", "123");
    	event=new Event(now, now,"abc",true);
    	user.calendar.addEvent(event);
    	Database.addUser(user);
    	
    	user = new User("mib", "1337");
    	event=new Event(now, now,"abc",true);
    	user.calendar.addEvent(event);
    	
    	event=new Event(now, now,"aaa",true);
    	user.calendar.addEvent(event);
    	
    	event=new Event(now, now,"ddd",false);
    	user.calendar.addEvent(event);
    	
    	
    	Database.addUser(user);
    }
}