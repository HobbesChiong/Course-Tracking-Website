package model;

import java.util.ArrayList;
import java.util.List;
/*
     instantiates our model class with the data from CSV Reader
 */
public class CreateDepartmentList {
    List<Department> departmentList = new ArrayList<>();
    private final static int SEMESTER_INDEX = 0;
    private final static int DEPARTMENT_INDEX = 1;
    private final static int COURSE_INDEX = 2;
    private final static int LOCATION_INDEX = 3;
    private final static int ENROLLMENT_CAP_INDEX = 4;
    private final static int ENROLLMENT_TOTAL_INDEX = 5;
    private final static int INSTRUCTOR_INDEX = 6;
    private final static int COMPONENT_CODE_INDEX = 7;
    private final static String FILE_NAME = "data\\course_data_2018.csv";

    public CreateDepartmentList() {
        createDepartmentList();
    }
    private void createDepartmentList() {
        CsvReader courseData = new CsvReader(FILE_NAME);
        List<String[]> courseDataList = courseData.getListOfCsvRows();

        for (String[] index : courseDataList) {
            String curDeptName = index[DEPARTMENT_INDEX];
            Department newDepartment = new Department(curDeptName);
            Course curCourse = new Course(index[COURSE_INDEX]);
            Offering curOffering = new Offering(index[LOCATION_INDEX], index[INSTRUCTOR_INDEX], index[SEMESTER_INDEX]);
            Section curSection = new Section(index[COMPONENT_CODE_INDEX], index[ENROLLMENT_TOTAL_INDEX], index[ENROLLMENT_CAP_INDEX]);

            createModel(curDeptName, newDepartment, curCourse, curOffering, curSection);
        }
    }

    private void createModel(String curDeptName, Department newDepartment, Course curCourse, Offering curOffering,
                             Section curSection)
    {
        if (isInDepartmentList(newDepartment)) {
            for (Department dept : departmentList) {
                if (dept.getName().equals(curDeptName)) {
                    dept.addToCourseList(curCourse, curOffering, curSection);
                }
            }
        } else {
            newDepartment.setDeptId(departmentList.size());
            departmentList.add(newDepartment);
            newDepartment.addToCourseList(curCourse, curOffering, curSection);
        }
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public boolean isInDepartmentList(Department department) {
        for (Department dept : departmentList) {
            if (dept.getName().equals(department.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addOffering (String semester, String subjectName, String catalogNumber, String location,
                             String enrollmentCap, String component, String enrollmentTotal, String instructor)
    {
        Department newDepartment = new Department(subjectName);
        Course newCourse = new Course(catalogNumber);
        Offering newOffering = new Offering(location, instructor, semester);
        Section newSection = new Section(component, enrollmentTotal, enrollmentCap);

        createModel(subjectName, newDepartment, newCourse, newOffering, newSection);
    }
}
