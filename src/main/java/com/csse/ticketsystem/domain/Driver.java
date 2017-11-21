package com.csse.ticketsystem.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Document(indexName = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "driver_name", nullable = false)
    private String driver_name;

    @NotNull
    @Column(name = "license", nullable = false)
    private String license;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate date_of_birth;

    @Column(name = "driver_email")
    private String driver_email;

    @Column(name = "driver_phone_no")
    private Integer driver_phone_no;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public Driver driver_name(String driver_name) {
        this.driver_name = driver_name;
        return this;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getLicense() {
        return license;
    }

    public Driver license(String license) {
        this.license = license;
        return this;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public Driver date_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
        return this;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDriver_email() {
        return driver_email;
    }

    public Driver driver_email(String driver_email) {
        this.driver_email = driver_email;
        return this;
    }

    public void setDriver_email(String driver_email) {
        this.driver_email = driver_email;
    }

    public Integer getDriver_phone_no() {
        return driver_phone_no;
    }

    public Driver driver_phone_no(Integer driver_phone_no) {
        this.driver_phone_no = driver_phone_no;
        return this;
    }

    public void setDriver_phone_no(Integer driver_phone_no) {
        this.driver_phone_no = driver_phone_no;
    }

    public Boolean isStatus() {
        return status;
    }

    public Driver status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Driver vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        if (driver.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Driver{" +
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
