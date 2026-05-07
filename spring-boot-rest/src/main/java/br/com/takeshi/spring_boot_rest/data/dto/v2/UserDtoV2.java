package br.com.takeshi.spring_boot_rest.data.dto.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserDtoV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String address;
    private String gender;

    public UserDtoV2() {
    }

    public UserDtoV2(Long id, String firstName, String lastName, String address, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserDtoV2 userDtoV2)) return false;
        return Objects.equals(getId(), userDtoV2.getId()) && Objects.equals(getFirstName(), userDtoV2.getFirstName()) && Objects.equals(getLastName(), userDtoV2.getLastName()) && Objects.equals(getBirthday(), userDtoV2.getBirthday()) && Objects.equals(getAddress(), userDtoV2.getAddress()) && Objects.equals(getGender(), userDtoV2.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getBirthday(), getAddress(), getGender());
    }
}
