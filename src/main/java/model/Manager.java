package model;

import java.util.List;
/*
    Manages the data by storing all the departments into a list which contains further information
 */
public class Manager {
    List<Department> departmentList;
    CreateDepartmentList departmentListMaker;

    public Manager() {
        departmentListMaker = new CreateDepartmentList();
        departmentList = departmentListMaker.getDepartmentList();
        sortAllLists();
    }
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void dumpModel() {
        printModel();
    }
    private void printModel() {
        for (Department department : departmentList) {
            printCourses(department.getCourseList(), department.getName());
        }
    }

    private void printCourses(List<Course> courseList, String departmentName) {
        for (Course course : courseList) {
            System.out.println(departmentName + " " + course.getCatalogNumber());
            printOfferings(course.getOfferingList());
        }
    }

    private void printOfferings(List<Offering> offeringList) {
        for (Offering offering : offeringList) {
            int semesterCode = offering.getSemesterCode();
            String location = offering.getLocation();
            String instructor = cleansedInstructorString(offering);
            System.out.println("\t" + semesterCode + " in " + location + " by " + instructor);
            printSections(offering.getAggregatedSectionList());
        }
    }

    private String cleansedInstructorString(Offering offering) {
        String result = offering.getInstructors().replaceAll("\\s+", " ");
        if (!result.isEmpty()) {
            if (result.charAt(0) == ',') {
                result = result.substring(2);
            }
        }
        return result;
    }

    private void printSections(List<Section> aggregatedSectionList) {
        for (Section section: aggregatedSectionList) {
            String componentCode = section.getType();
            int enrollmentTotal = section.getEnrollmentTotal();
            int enrollmentCap = section.getEnrollmentCap();
            System.out.println("\t\t Type=" + componentCode + ", Enrollment=" + enrollmentTotal + "/" + enrollmentCap);
        }
    }

    private void sortAllLists() {
        departmentList.sort((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()));
        int i = 0;
        for (Department department : departmentList) {
            department.setDeptId(i);
            i++;
        }
        sortCourseList();
    }

    private void sortCourseList() {
        for (Department department : departmentList) {
            sortOfferingList(department.getCourseList());
            department.sortCourseList();
        }
    }

    private void sortOfferingList(List<Course> courseList) {
        for (Course course : courseList) {
            sortSectionList(course.getOfferingList());
            course.sortOfferingList();
        }
    }

    private void sortSectionList(List<Offering> offeringList) {
        for (Offering offering : offeringList) {
            offering.sortSectionList();
            offering.aggregateSectionList();
        }
    }

    public void addOffering(String semester, String subjectName, String catalogNumber, String location,
                            String enrollmentCap, String component, String enrollmentTotal, String instructor)
    {
        departmentListMaker.addOffering(semester, subjectName, catalogNumber, location, enrollmentCap, component,
                enrollmentTotal, instructor);
        departmentList = departmentListMaker.getDepartmentList();
        sortAllLists();
    }

    public Department getDepartment(int id) {
        for (Department dept : departmentList) {
            if (id == dept.getDeptId()) {
                return dept;
            }
        }
        return null;
    }
}
