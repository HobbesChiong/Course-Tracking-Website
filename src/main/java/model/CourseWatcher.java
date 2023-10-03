package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class CourseWatcher {
    private int id;
    private Department department;
    private Course course;
    private final ArrayList<String> events = new ArrayList<>();

    public CourseWatcher(Department department, Course course) {
        this.department = department;
        this.course = course;
    }

    public void updateEvents(Section newSection, Offering newOffering) {
        StringBuilder event = new StringBuilder();
        // format from https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        String formattedDate = formatter.format(new Date());

        event.append(formattedDate).append(": Added section ").append(newSection.getType())
                .append(" with enrollment ").append("(").append(newSection.getEnrollmentTotal()).append(" / ")
                .append(newSection.getEnrollmentCap()).append(") to offering ").append(newOffering.getTerm())
                .append(" ").append(newOffering.getYear());

        events.add(event.toString());
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public Course getCourse() {
        return course;
    }

    public ArrayList<String> getEvents() {
        return events;
    }
    public int getId() {
        return id;
    }
}
