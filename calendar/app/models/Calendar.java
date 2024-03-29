package models;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class Calendar {
	private String name;
	public User owner;
	private PriorityQueue<Event> events;
	public long id;
	private static long counter;
	
	public Calendar(String name, User owner){
		this.name = name;
		this.owner = owner;
		events = new PriorityQueue<Event>();
		counter++;
		this.id = counter;
	}
	
	public User getOwner(){
		return this.owner;
	}
	
	public String getName(){
		return this.name;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void addEvent(Date startDate, Date endDate, String name, boolean is_visible){
		Event ev = new Event(startDate, endDate, name, is_visible);
		events.add(ev);
	}
	
	public void addEvent(Event e){
		events.add(e);
	}
	
	/**
	 * obtain a list of events a user is allowed to see in a calendar for a given date.
	 **/
	public LinkedList<Event> getDayEvents(Date day, User requester){
		// temporary result linked list
		LinkedList<Event> events_tmp = new LinkedList<Event>();
		
		// test if requester references to same object as owner. if true, we have the same user,
		// therefore, the requester gets full access to its calendar data (since he is the owner of it)
		boolean is_owner = owner == requester; 
		
		// requester is owner
		if(is_owner){
			for(Event e : events)
				if(e.getStart().equals(day)) events_tmp.add(e);
			
		// requester is not the owner of the calendar ==> do visibility check.	
		}else{
			for(Event e : events)
				if(e.isVisible() && e.getStart().equals(day)) events_tmp.add(e);
		}
		return events_tmp;
	}
	
	public Iterator<Event> getEventList(Date start, User requester){
		// temporary result linked list
		LinkedList<Event> events_tmp = new LinkedList<Event>();
		
	
		// test if requester references to same object as owner. if true, we have the same user,
		// therefore, the requester gets full access to its calendar data (since he is the owner of it)
		boolean is_owner = owner == requester; 
		
		// requester is owner
		if(is_owner){
			for(Event e : events)
				if((e.getStart().after(start) || e.getStart().equals(start))) events_tmp.add(e);
			
		// requester is not the owner of the calendar ==> do visibility check.	
		}else{
			for(Event e : events)
				if(e.isVisible() && (e.getStart().after(start) || e.getStart().equals(start)) ) events_tmp.add(e);
		}
		// Iterator itr = al.iterator();
		Iterator<Event> iter = events_tmp.iterator();
		return iter;
	}
	
	// return all visible events of a given months => for graphical calendar
	public LinkedList<Event> getEventsOfMonth(Date month){
		return null;
	}
	
	public Event getEventById(long id){
		Event result = null;
		for(Event e : events) if(e.getId() == id) result = e; 
		return result;
	}
	
	// removes an event by its id
	public void removeEvent(long id){
		for(Event e : events) if(e.getId() == id) events.remove(e); 
	}
	
}
