package com.studentmanager.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "students")
public class Student extends BaseEntity {
    private String name;
    private int age;
    private List<Enrollment> courseEnrollments;

    public Student() {
        super();
    }

    public Student(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @OneToMany(mappedBy = "student",
            targetEntity = Enrollment.class)
    public List<Enrollment> getCourseEnrollments() {
        return courseEnrollments;
    }

    public void setCourseEnrollments(List<Enrollment> courseEnrollments) {
        this.courseEnrollments = courseEnrollments;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
