package com.thecodealchemist.spring_boot_project.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Column
    private Integer year;  // Validate 1–5 in service layer

    @Column(length = 50)
    private String branch;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(length = 50)
    private String hostel;

    @Column(length = 100)
    private String guardianName;

    @Column(length = 15)
    private String guardianNumber;

    @Column
    private Float wallet ;

    @Column
    private LocalDate dob;

    @Column
    private Integer age;

    public enum Gender {
        Male, Female, Other
    }

    // No-argument constructor
    public Student() {}

    // All-argument constructor
    public Student(Integer studentId, String firstName, String middleName, String lastName, String email,
                   String phone, Integer year, String branch, Gender gender, String hostel,
                   String guardianName, String guardianNumber, Float wallet, LocalDate dob, Integer age) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.year = year;
        this.branch = branch;
        this.gender = gender;
        this.hostel = hostel;
        this.guardianName = guardianName;
        this.guardianNumber = guardianNumber;
        this.wallet = wallet;
        this.dob = dob;
        this.age = age;
    }

    // Getters and Setters
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getHostel() { return hostel; }
    public void setHostel(String hostel) { this.hostel = hostel; }

    public String getGuardianName() { return guardianName; }
    public void setGuardianName(String guardianName) { this.guardianName = guardianName; }

    public String getGuardianNumber() { return guardianNumber; }
    public void setGuardianNumber(String guardianNumber) { this.guardianNumber = guardianNumber; }

    public Float getWallet() { return wallet; }
    public void setWallet(Float wallet) { this.wallet = wallet; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", year=" + year +
                ", branch='" + branch + '\'' +
                ", gender=" + gender +
                ", hostel='" + hostel + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", guardianNumber='" + guardianNumber + '\'' +
                ", wallet=" + wallet +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}