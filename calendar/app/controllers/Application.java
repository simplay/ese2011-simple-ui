package controllers;
import java.util.*;

import play.*;
import play.mvc.*;

import java.util.*;
import play.data.validation.Required;
import models.*;
import models.Calendar;

//so far: jeder user kann neue user erzeugen mit default password 123

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
    	User me = Database.users.get(Security.connected());
    	List<User> users = Database.getUserList();
    	// todo: remove ourself from list
        render(users,me);
    }
    
    public static void showUsers(){
    	User me = Database.users.get(Security.connected());
        List<User> users = Database.getUserList();
        Calendar calendars = me.getCalendar();
        render(me, users, calendars);
    }
    
    
    public static void showCalendars(String username) {
        User me = Database.users.get(Security.connected());
        User user = Database.users.get(username);
        Calendar calendars = null;
        if(me != null && user != null) {
        	calendars = user.getCalendar();
       	}
        render(me, user, calendars);
    }
    
    public static void showEvents(String username, String calendarName) {
    	User me = Database.users.get(Security.connected());
    	User user = Database.users.get(username);
    	Date d = new Date(1,1,1);
    	Iterator allVisibleEvents = user.getCalendar().getEventList(d, me);
    	Calendar calendars = user.getCalendar();
    	
    	LinkedList<Event> events = new LinkedList<Event>();
    	
    	
    	while(allVisibleEvents.hasNext()){
    		events.add((Event) allVisibleEvents.next());
    	}
    	render(me, user, events, calendarName, calendars);
    }
    
    public static void newEvent(String calendarName, String oldEventName, 
    		String newEventName, String start, String end, boolean isPublic){
    	
    }
    
    public static void newUser(@Required String name){
    	User user;
    	Event event;
    	Date now = new Date();
    	
    	if(!name.isEmpty()){
    		user = new User(name, "123");
        	event=new Event(now, now,"abc",true);
        	user.calendar.addEvent(event);
        	Database.addUser(user);
        	//Data d = new Data(value);
        	
        	renderJSON(user);
    	}
    }
    
    public static void addEvent(Calendar calendar, String name, Date start, Date end, boolean is_visible){
    	Event e = new Event(start, end, name, is_visible);
    	calendar.addEvent(e);
    }
}