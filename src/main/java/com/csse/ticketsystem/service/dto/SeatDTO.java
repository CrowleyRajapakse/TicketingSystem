package com.csse.ticketsystem.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Seat entity.
 */
public class SeatDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer seat_id;

    private String remark;

    private Long vehicleId;

    private String vehicleVehicle_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

        SeatDTO seatDTO = (SeatDTO) o;
        if(seatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatDTO{" +
            "id=" + getId() +
            ", seat_id='" + getSeat_id() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
