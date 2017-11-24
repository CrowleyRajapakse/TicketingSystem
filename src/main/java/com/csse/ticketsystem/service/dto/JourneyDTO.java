package com.csse.ticketsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Journey entity.
 */
public class JourneyDTO implements Serializable {

    private Long id;

    @NotNull
    private String journey_id;

    @NotNull
    private String departure;

    @NotNull
    private LocalDate departure_time;

    private String arrival;

    private LocalDate arrival_time;

    @DecimalMin(value = "0")
    private Double amount;

    private Long vehicleId;

    private String vehicleVehicle_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJourney_id() {
        return journey_id;
    }

    public void setJourney_id(String journey_id) {
        this.journey_id = journey_id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public LocalDate getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalDate departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalDate getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(LocalDate arrival_time) {
        this.arrival_time = arrival_time;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

        JourneyDTO journeyDTO = (JourneyDTO) o;
        if(journeyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journeyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JourneyDTO{" +
            "id=" + getId() +
            ", journey_id='" + getJourney_id() + "'" +
            ", departure='" + getDeparture() + "'" +
            ", departure_time='" + getDeparture_time() + "'" +
            ", arrival='" + getArrival() + "'" +
            ", arrival_time='" + getArrival_time() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }
}
