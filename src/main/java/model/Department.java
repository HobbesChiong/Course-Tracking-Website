package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/*
    A specific department at SFU
    ex: MACM
 */
public class Department {
    private int deptId;
    // "Subject" in CSV File ex CMPT
    private String name;

    private List<Course> courseList;

    public Department (String departmentName) {
        this.name = departmentName;
        this.courseList = new ArrayList<>();
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public int getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public List<Course> getCourseList() {
        return courseList;
    }

    public void addToCourseList(Course course, Offering offering, Section section) {
        if (isInCourseList(course)) {
            for (Course catalogNumber : courseList) {
                if (catalogNumber.getCatalogNumber().equals(course.getCatalogNumber())) {
                    catalogNumber.addToOfferingList(offering, section);
                }
            }
        } else {
            course.setCourseId(courseList.size());
            courseList.add(course);
            course.addToOfferingList(offering, section);
        }
    }

    private boolean isInCourseList(Course course) {
        for (Course catalogNumber : courseList) {
            if (catalogNumber.getCatalogNumber().equals(course.getCatalogNumber())) {
                return true;
            }
        }
        return false;
    }

    public void sortCourseList() {
        courseList.sort(Comparator.comparing(Course::getCatalogNumber));
        int i = 0;
        for (Course course : courseList) {
            course.setCourseId(i);
            i++;
        }
    }

    public Course getCourse(int id) {
        for (Course course : courseList) {
            if (id == course.getCourseId()) {
                return course;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", name='" + name +
                ", courseList=" + courseList +
                '}';
    }
}
