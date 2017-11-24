package com.csse.ticketsystem.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Journey.
 */
@Entity
@Table(name = "journey")
@Document(indexName = "journey")
public class Journey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "journey_id", nullable = false)
    private String journey_id;

    @NotNull
    @Column(name = "departure", nullable = false)
    private String departure;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private LocalDate departure_time;

    @Column(name = "arrival")
    private String arrival;

    @Column(name = "arrival_time")
    private LocalDate arrival_time;

    @DecimalMin(value = "0")
    @Column(name = "amount")
    private Double amount;

    @ManyToOne(optional = false)
    @NotNull
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJourney_id() {
        return journey_id;
    }

    public Journey journey_id(String journey_id) {
        this.journey_id = journey_id;
        return this;
    }

    public void setJourney_id(String journey_id) {
        this.journey_id = journey_id;
    }

    public String getDeparture() {
        return departure;
    }

    public Journey departure(String departure) {
        this.departure = departure;
        return this;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public LocalDate getDeparture_time() {
        return departure_time;
    }

    public Journey departure_time(LocalDate departure_time) {
        this.departure_time = departure_time;
        return this;
    }

    public void setDeparture_time(LocalDate departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival() {
        return arrival;
    }

    public Journey arrival(String arrival) {
        this.arrival = arrival;
        return this;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalDate getArrival_time() {
        return arrival_time;
    }

    public Journey arrival_time(LocalDate arrival_time) {
        this.arrival_time = arrival_time;
        return this;
    }

    public void setArrival_time(LocalDate arrival_time) {
        this.arrival_time = arrival_time;
    }

    public Double getAmount() {
        return amount;
    }

    public Journey amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Journey vehicle(Vehicle vehicle) {
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
        Journey journey = (Journey) o;
        if (journey.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journey.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Journey{" +
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
