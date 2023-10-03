package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
/*
    a specific course in a department differentiated by its catalog number
    Ex: 250 in CMPT 250
 */
public class Course {
    private int courseId;
    private String catalogNumber;
    private List<Offering> offeringList;

    public Course (String catalogNumber) {
        this.catalogNumber = catalogNumber;
        this.offeringList = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    @JsonIgnore
    public List<Offering> getOfferingList() {
        return offeringList;
    }

    public void setOfferingList(List<Offering> offeringList) {
        this.offeringList = offeringList;
    }

    public void addToOfferingList(Offering offering, Section section) {
        if (isInOfferingList(offering)) {
            for (Offering off : offeringList) {
                if (off.equals(offering)) {
                    String oldInstructor = off.getInstructors();
                    String newInstructor = offering.getInstructors().replaceAll("\\s+", " ");
                    updateInstructorsString(off, oldInstructor, newInstructor);
                    off.addToSectionList(section);
                }
            }
        } else {
            offering.setCourseOfferingId(offeringList.size());
            offeringList.add(offering);
            offering.addToSectionList(section);
        }
    }

    private static void updateInstructorsString(Offering off, String oldInstructor, String newInstructor) {
        if (oldInstructor.equals("")) {
            off.setInstructors(newInstructor);
        }
        else if (!(oldInstructor.contains(newInstructor))) {
            int res = oldInstructor.compareTo(newInstructor);
            off.setInstructors(res < 0 ? oldInstructor + ", " + newInstructor : newInstructor + ", " + oldInstructor);
        }
    }

    private boolean isInOfferingList(Offering offering) {
        for (Offering off : offeringList) {
            if (off.equals(offering)) {
                return true;
            }
        }
        return false;
    }

    public void sortOfferingList() {
        offeringList.sort((o1, o2) -> {
            int res = Integer.compare(o1.getSemesterCode(), o2.getSemesterCode());
            if (res == 0) {
                return o1.getLocation().compareTo(o2.getLocation());
            }
            else {
                return res;
            }
        });

        int i = 0;
        for (Offering offering : offeringList) {
            offering.setCourseOfferingId(i);
            i++;
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", catalogNumber='" + catalogNumber +
                ", offeringList=" + offeringList +
                '}';
    }
}
