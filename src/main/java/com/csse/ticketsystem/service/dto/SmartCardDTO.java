package com.csse.ticketsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SmartCard entity.
 */
public class SmartCardDTO implements Serializable {

    private Long id;

    @NotNull
    private String smartCardID;

    @NotNull
    private LocalDate expiryDate;

    @NotNull
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmartCardID() {
        return smartCardID;
    }

    public void setSmartCardID(String smartCardID) {
        this.smartCardID = smartCardID;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SmartCardDTO smartCardDTO = (SmartCardDTO) o;
        if(smartCardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smartCardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmartCardDTO{" +
            "id=" + getId() +
            ", smartCardID='" + getSmartCardID() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
