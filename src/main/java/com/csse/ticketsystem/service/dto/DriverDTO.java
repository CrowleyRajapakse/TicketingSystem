package com.csse.ticketsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Driver entity.
 */
public class DriverDTO implements Serializable {

    private Long id;

    @NotNull
    private String driver_name;

    @NotNull
    private String license;

    @NotNull
    private LocalDate date_of_birth;

    private String driver_email;

    private Integer driver_phone_no;

    @NotNull
    private Boolean status;

    private Long vehicleId;

    private String vehicleVehicle_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDriver_email() {
        return driver_email;
    }

    public void setDriver_email(String driver_email) {
        this.driver_email = driver_email;
    }

    public Integer getDriver_phone_no() {
        return driver_phone_no;
    }

    public void setDriver_phone_no(Integer driver_phone_no) {
        this.driver_phone_no = driver_phone_no;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleVehicle_name() {
        return vehicleVehicle_name;
    }

    public void setVehicleVehicle_name(String vehicleVehicle_name) {
        this.vehicleVehicle_name = vehicleVehicle_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DriverDTO driverDTO = (DriverDTO) o;
        if(driverDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
            "id=" + getId() +
            ", driver_name='" + getDriver_name() + "'" +
            ", license='" + getLicense() + "'" +
            ", date_of_birth='" + getDate_of_birth() + "'" +
            ", driver_email='" + getDriver_email() + "'" +
            ", driver_phone_no='" + getDriver_phone_no() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
