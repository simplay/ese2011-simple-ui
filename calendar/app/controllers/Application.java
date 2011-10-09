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
    
    public static void showMe(){
    	User me = Database.users.get(Security.connected());
        List<User> users = Database.getUserList();
        Calendar defaultCalendar = me.getdefaultCalendar();
        LinkedList<Calendar> calendars = me.getCalendars();
        render(me, users, calendars, defaultCalendar);
    }
    
    
    public static void showCalendars(String username) {
        User me = Database.users.get(Security.connected());
        User user = Database.users.get(username);
        LinkedList<Calendar> calendars = null;
        if(me != null && user != null) {
        	calendars = user.getCalendars();
       	}
        render(me, user, calendars);
    }
    
    public static void showEvents(long calendarId, String username, String calendarName) {
    	User me = Database.users.get(Security.connected());
    	User user = Database.users.get(username);
    	Date d = new Date(1,1,1);
    	Iterator allVisibleEvents = user.getCalendarById(calendarId).getEventList(d, me);
    	Calendar calendars = user.getCalendarById(calendarId);
    	
    	LinkedList<Event> events = new LinkedList<Event>();
    	
    	
    	while(allVisibleEvents.hasNext()){
    		events.add((Event) allVisibleEvents.next());
    	}
    	
    	render(me, user, events, calendarName, calendars, calendarId);
    }
    
    
    public static void newUser(@Required String name){
    	User user;
    	Event event;
    	Date now = new Date();
    	
    	if(!name.isEmpty()){
    		// mache user mit default daten:
    		user = new User(name, "123");
        	event=new Event(now, now,"abc",true);
        	//user.calendar.
        	user.getdefaultCalendar().addEvent(event);
        	
        	Database.addUser(user);
        	//Data d = new Data(value);
        	
        	renderJSON(user);
    	}
    }
    
    public static void creatEvent(@Required long calendarID, @Required String name,
    		@Required String start,@Required String end, boolean is_visible){
    	
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarById(calendarID);
    	
    	
    	
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
    
    
    public static void saveEditedEvent(@Required long eventID, @Required long calendarID, @Required String name,
    		@Required String start,@Required String end, boolean is_visible){
    	
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarById(calendarID);
    	
    	
    	
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
        
       
    	Event event = calendar.getEventById(eventID);
    	
    	event.edit(d_start, d_end, name, is_visible);
    	
    }
    
    public static void editEvent(long eventID, long calendarID, String name){
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarById(calendarID);
    	Event event = calendar.getEventById(eventID);
    	render(me, calendar, event, calendarID, eventID);
    	
    }
    
    public static void addEvent(long calendarID, String name){
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarById(calendarID);
    	render(me, calendar, calendarID);
    }
    
    public static void removeEvent(long calendarID, long eventID){
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarById(calendarID);
    	calendar.removeEvent(eventID);
    }
}