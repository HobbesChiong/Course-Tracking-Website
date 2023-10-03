package model;

public class CourseWatcherRequestBody {
    private final int deptId;
    private final int courseId;

    public CourseWatcherRequestBody(int deptId, int courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getDeptId() {
        return deptId;
    }
}
