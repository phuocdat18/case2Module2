package model;

import jdk.jshell.execution.Util;
import repository.IModel;
import utils.ValidateUtils;
import java.text.DateFormat;
import java.util.Date;

public class User implements IModel<User> {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private EGender gender;
    private Date birthDay;
    private String email;
    private String address;
    private Role Role;

    public User(int id, String username, String password, String fullName, String phoneNumber, EGender gender, String cccd, Date birthDay, String email, String address, Role Role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDay = birthDay;
        this.email = email;
        this.address = address;
        this.Role = Role;
    }

    public User() {

    }


    public void update(User obj) {
        this.id = obj.id;
        this.username = obj.username;
        this.password = obj.password;
        this.fullName = obj.fullName;
        this.phoneNumber = obj.phoneNumber;
        this.gender = obj.gender;
        this.birthDay = obj.birthDay;
        this.email = obj.email;
        this.address = obj.address;
        this.Role = obj.Role;
    }


    public User parseData(String line) {
        User user = new User();
        String[] strings = line.split(";");
        int id = Integer.parseInt(strings[0]);
        String username = strings[1];
        String password = strings[2];
        String fullName = strings[3];
        String phoneNumber = strings[4];
        EGender eGender = EGender.getEGenderByName(strings[5]);
        Date birthDay = utils.DateFormat.parseDate(strings[6]);
        String email = strings[7];
        String address = ValidateUtils.parseCharToComma(strings[8]);
        Role role = Role.getRoleByName(strings[9]);

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setGender(eGender);
        user.setBirthDay(birthDay);
        user.setEmail(email);
        user.setAddress(address);
        user.setRole(role);

        return user;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public model.Role getRole() {
        return Role;
    }

    public void setRole(model.Role role) {
        Role = role;
    }

    public String userView() {
        return String.format("            ║%7s║%-15s║ %-20s║ %-15s║ %-15s║%-15s║ %-15s║ %-36s║ %-30s║", this.id, this.username, this.fullName, this.phoneNumber, this.gender.getName(), utils.DateFormat.convertDateToString(this.birthDay), this.email, this.address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", birthDay=" + birthDay +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", Role=" + Role +
                '}';
    }
}