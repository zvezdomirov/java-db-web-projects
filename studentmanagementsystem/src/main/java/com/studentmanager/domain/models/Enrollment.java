package com.studentmanager.domain.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains the information for a
 * student and his grades in a specific course
 */
@Entity
@Table(name = "enrollments")
public class Enrollment extends BaseEntity {
    private Course course;

    private Student student;

    private List<Double> grades;

    public Enrollment() {
        super();
    }

    public Enrollment(Course course, Student student) {
        super();
        this.course = course;
        this.student = student;
        this.grades = new ArrayList<>();
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "course_id",
            referencedColumnName = "id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name = "student_id",
            referencedColumnName = "id")
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Column(name = "grade")
    @ElementCollection
    public List<Double> getGrades() {
        return this.grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }
}
