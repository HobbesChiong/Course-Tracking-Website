package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
/*
    A specific offering of a class in a given semester
 */
public class Offering {
    // Assume there is at most one offering of each course at each campus during a single
    //semester. Therefore aggregate all lectures, all tutorials, etc for a single course/campus
    //pair.
    private int courseOfferingId;
    // i.e SURREY
    private String location;
    private String instructors;
    private int year;
    private int semesterCode;
    private String term;
    private final List<Section> sectionList;
    private List<Section> aggregatedSectionList;

    public Offering(String location, String instructors, String semesterCode) {
        this.location = location;
        if (instructors.equals("(null)") || instructors.equals("<null>")) {
            this.instructors = "";
        } else {
            this.instructors = instructors;
        }
        this.semesterCode = Integer.parseInt(semesterCode);
        this.sectionList = new ArrayList<>();
        int x = Integer.parseInt(String.valueOf(semesterCode.charAt(0)));
        int y = Integer.parseInt(String.valueOf(semesterCode.charAt(1)));
        int z = Integer.parseInt(String.valueOf(semesterCode.charAt(2)));
        int a = Integer.parseInt(String.valueOf(semesterCode.charAt(3)));
        this.year = 1900 + (100*x) + (10*y) + z;
        switch (a) {
            case 1 -> this.term = "Spring";
            case 4 -> this.term = "Summer";
            case 7 -> this.term = "Fall";
        }
    }

    public int getYear() {
        return year;
    }

    public String getTerm() {
        return term;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(int courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public void addInstructor(String instructor) {
        this.instructors = this.instructors + ", " + instructor;
    }

    @JsonIgnore
    public List<Section> getSectionList() {
        return sectionList;
    }

    @JsonIgnore
    public List<Section> getAggregatedSectionList() {
        return aggregatedSectionList;
    }

    public void addToSectionList(Section section) {
        section.setSectionId(sectionList.size());
        sectionList.add(section);
    }

    private boolean isInSectionList(Section section) {
        for (Section sec : sectionList) {
            if (sec.equals(section)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Offering otherOffering) {
        return this.semesterCode == otherOffering.semesterCode
                && this.location.equals(otherOffering.location);
    }

    public void sortSectionList() {
        sectionList.sort((s1, s2) -> s1.getType().compareToIgnoreCase(s2.getType()));
        int i = 0;
        for (Section section : sectionList) {
            section.setSectionId(i);
            i++;
        }
    }

    public void aggregateSectionList() {
        List<Section> aggregatedSectionList = new ArrayList<>();
        for (Section section : sectionList) {
            if (aggregatedSectionList.isEmpty()) {
                aggregatedSectionList.add(section);
            } else {
                updateAggregatedSectionList(aggregatedSectionList, section);
            }
        }
        this.aggregatedSectionList  = aggregatedSectionList;
    }

    private static void updateAggregatedSectionList(List<Section> aggregatedSectionList, Section section) {
        boolean isInside = false;
        for (Section aggregatedSection : aggregatedSectionList) {
            String componentCode = section.getType();
            int newEnrollmentCap = section.getEnrollmentCap();
            int newEnrollmentTotal = section.getEnrollmentTotal();
            if (aggregatedSection.getType().equals(componentCode)) {
                aggregatedSection.increaseEnrollmentCap(newEnrollmentCap);
                aggregatedSection.increaseEnrollmentTotal(newEnrollmentTotal);
                isInside = true;
                break;
            }
        }
        if (!isInside) {
            aggregatedSectionList.add(section);
        }
    }

    @Override
    public String toString() {
        return "Offering{" +
                "location=" + location +
                ", instructor='" + instructors +
                ", semesterCode=" + semesterCode +
                '}';
    }
}
