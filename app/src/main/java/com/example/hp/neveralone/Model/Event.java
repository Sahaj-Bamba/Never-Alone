package com.example.hp.neveralone.Model;

public class Event {
    private String EventName ,Description,Organiser;
    private int Day,Month,Year,Hour,Minutes;

    public Event() {
    }

    public Event(String eventName, String description, String organiser, int day, int month, int year, int hour, int minutes) {
        EventName = eventName;
        Description = description;
        Organiser = organiser;
        Day = day;
        Month = month;
        Year = year;
        Hour = hour;
        Minutes = minutes;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOrganiser() {
        return Organiser;
    }

    public void setOrganiser(String organiser) {
        Organiser = organiser;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinutes() {
        return Minutes;
    }

    public void setMinutes(int minutes) {
        Minutes = minutes;
    }
}
