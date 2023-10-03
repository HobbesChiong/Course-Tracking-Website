package model;
/*
    A specific section of an offering in a semester
 */
public class Section {
    private int sectionId;
    private String type;
    private int enrollmentTotal;
    private int enrollmentCap;

    public Section(String type, String enrollmentTotal, String enrollmentCap) {
        this.type = type;
        this.enrollmentTotal = Integer.parseInt(enrollmentTotal);
        this.enrollmentCap = Integer.parseInt(enrollmentCap);
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public boolean equals(Section otherSection) {
        return this.type.equals(otherSection.type)
                && this.enrollmentTotal == otherSection.enrollmentTotal
                && this.enrollmentCap == otherSection.enrollmentCap;
    }

    public void increaseEnrollmentCap(int val){
        enrollmentCap += val;
    }

    public void increaseEnrollmentTotal(int val) {
        enrollmentTotal += val;
    }

    @Override
    public String toString() {
        return "Section{" +
                "componentCode=" + type +
                ", enrollmentTotal='" + enrollmentTotal +
                ", enrollmentCap=" + enrollmentCap +
                '}';
    }
}

