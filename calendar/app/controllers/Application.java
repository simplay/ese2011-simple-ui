package controllers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    
    
    public static void newUser(@Required String name){
    	User user;
    	Event event;
    	Date now = new Date();
    	
    	if(!name.isEmpty()){
    		// mache user mit default daten:
    		user = new User(name, "123");
        	event=new Event(now, now,"abc",true);
        	user.calendar.addEvent(event);
        	Database.addUser(user);
        	//Data d = new Data(value);
        	
        	renderJSON(user);
    	}
    }
    
    public static void creatEvent(@Required String name,@Required String start,@Required String end, boolean is_visible){
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarByName(me);
    	
    	// covert dates
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	Date d_start = null;
        Date d_end = null;
        
        try {
        	d_start = dateFormat.parse(start);
        	d_end = dateFormat.parse(end);
        }catch (Exception e) {
        	d_start = new Date(1,1,1);
        	d_end = new Date(1,1,1);
        }
           
    	
    	Event e = new Event(d_start, d_end, name, is_visible);
    	calendar.addEvent(e);
    }
    
    public static void addEvent(String name){
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarByName(me);
    	
    	render(me, calendar);
    }
}