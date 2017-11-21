package com.csse.ticketsystem.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Route entity.
 */
public class RouteDTO implements Serializable {

    private Long id;

    @NotNull
    private String route_name;

    @NotNull
    private Double distance;

    private String remarks;

    private Long vehicleId;

    private String vehicleVehicle_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

        RouteDTO routeDTO = (RouteDTO) o;
        if(routeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), routeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RouteDTO{" +
            "id=" + getId() +
            ", route_name='" + getRoute_name() + "'" +
            ", distance='" + getDistance() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
