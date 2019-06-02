
/*
 * User:liveGreen
 * Date: 2019/5/25
 */

package com.green.lc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emp")
public class Emp {

    @Id
    @GeneratedValue
    private long empId;
    private String empName;
    private String empGender;
    private String email;
    private String department;

    public Emp() {
    }

    public Emp(String empName, String empGender, String email, String department) {
        this.empName = empName;
        this.empGender = empGender;
        this.email = email;
        this.department = department;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(String empGender) {
        this.empGender = empGender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empGender='" + empGender + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
