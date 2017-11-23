package com.csse.ticketsystem.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserExtra entity.
 */
public class UserExtraDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer contactNo;

    @NotNull
    private String address;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getContactNo() {
        return contactNo;
    }

    public void setContactNo(Integer contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserExtraDTO userExtraDTO = (UserExtraDTO) o;
        if(userExtraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
