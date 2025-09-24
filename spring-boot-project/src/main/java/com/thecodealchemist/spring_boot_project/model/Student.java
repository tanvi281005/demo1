package com.thecodealchemist.spring_boot_project.model;
import java.time.LocalDate;

public class Student {
    private Integer studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private Integer year;
    private String branch;
    private Gender gender; // Male, Female, Other
    private String hostel;
    private String guardianName;
    private String guardianNumber;
    private Float wallet;
    private LocalDate dob;
    private Integer age;
    public enum Gender{
        Male,Female,Other
    }

    // No-argument constructor
    public Student() {}

    // All-argument constructor
    public Student(Integer studentId, String firstName, String middleName, String lastName,
                   String email, String phone, Integer year, String branch, Gender gender,
                   String hostel, String guardianName, String guardianNumber,
                   Float wallet, LocalDate dob, Integer age) {
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
                ", gender='" + gender + '\'' +
                ", hostel='" + hostel + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", guardianNumber='" + guardianNumber + '\'' +
                ", wallet=" + wallet +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
