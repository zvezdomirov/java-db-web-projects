package com.studentmanager.domain.models;

import com.studentmanager.domain.enums.Degree;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity {
    private String name;
    private int age;
    private Degree degree;
    private Course course;

    public Teacher() {
        super();
    }

    public Teacher(String name, int age, Degree degree) {
        super();
        this.name = name;
        this.age = age;
        this.degree = degree;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age")
    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Enumerated(value = EnumType.STRING)
    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    @OneToOne(targetEntity = Course.class,
            mappedBy = "teacher")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
