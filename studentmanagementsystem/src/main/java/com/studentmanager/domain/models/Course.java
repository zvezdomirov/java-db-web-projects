package com.studentmanager.domain.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
    private String name;
    private int totalHours;
    private Teacher teacher;
    private List<Enrollment> studentsWithGrades;

    public Course() {
        super();
    }

    public Course(String name,
                  int totalHours) {
        super();
        this.name = name;
        this.totalHours = totalHours;
        this.studentsWithGrades = new ArrayList<>();
    }

    @OneToOne(fetch = FetchType.LAZY,
            targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id",
            referencedColumnName = "id")
    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "total_hours")
    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    @OneToMany(targetEntity = Enrollment.class,
            fetch = FetchType.LAZY,
            mappedBy = "course")
    public List<Enrollment> getEnrolledStudentsWithGrades() {
        return studentsWithGrades;
    }

    public void setEnrolledStudentsWithGrades(List<Enrollment> studentsWithGrades) {
        this.studentsWithGrades = studentsWithGrades;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
