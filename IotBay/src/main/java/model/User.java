package model;
import java.io.Serializable;
import java.util.Date;
public class User implements Serializable{
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Date dateOfBirth;
    private String gender;
    public User(){

    }
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
public Date getDateOfBirth() {
        return dateOfBirth;
}
public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
}
public String getGender() {
        return gender;
}
public void setGender(String gender) {
        this.gender = gender;
}
}


