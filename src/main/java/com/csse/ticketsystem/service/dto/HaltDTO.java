package com.csse.ticketsystem.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Halt entity.
 */
public class HaltDTO implements Serializable {

    private Long id;

    @NotNull
    private String start_halt;

    @NotNull
    private String end_halt;

    @NotNull
    private Double halt_distance;

    @NotNull
    @DecimalMin(value = "0")
    private Double price;

    private Long routeId;

    private String routeRoute_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart_halt() {
        return start_halt;
    }

    public void setStart_halt(String start_halt) {
        this.start_halt = start_halt;
    }

    public String getEnd_halt() {
        return end_halt;
    }

    public void setEnd_halt(String end_halt) {
        this.end_halt = end_halt;
    }

    public Double getHalt_distance() {
        return halt_distance;
    }

    public void setHalt_distance(Double halt_distance) {
        this.halt_distance = halt_distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getRouteRoute_name() {
        return routeRoute_name;
    }

    public void setRouteRoute_name(String routeRoute_name) {
        this.routeRoute_name = routeRoute_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HaltDTO haltDTO = (HaltDTO) o;
        if(haltDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), haltDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HaltDTO{" +
            "id=" + getId() +
            ", start_halt='" + getStart_halt() + "'" +
            ", end_halt='" + getEnd_halt() + "'" +
            ", halt_distance='" + getHalt_distance() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
