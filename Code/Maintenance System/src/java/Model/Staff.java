/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author PC
 */
public class Staff {

    private int staffID;
    private String usernameS;
    private String passwordS;
    private int role;
    private String name;
    private String gender;
    private String date;
    private String email;
    private String phone;
    private String address;
    private String imgage;

    public Staff() {
    }

    public Staff(int staffID, String usernameS, String passwordS, int role, String name, String gender, String date, String email, String phone, String address, String imgage) {
        this.staffID = staffID;
        this.usernameS = usernameS;
        this.passwordS = passwordS;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.imgage = imgage;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getUsernameS() {
        return usernameS;
    }

    public void setUsernameS(String usernameS) {
        this.usernameS = usernameS;
    }

    public String getPasswordS() {
        return passwordS;
    }

    public void setPasswordS(String passwordS) {
        this.passwordS = passwordS;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgage() {
        return imgage;
    }

    public void setImgage(String imgage) {
        this.imgage = imgage;
    } 
}
