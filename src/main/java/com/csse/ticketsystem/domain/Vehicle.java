package com.csse.ticketsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Document(indexName = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vehicle_name", nullable = false)
    private String vehicle_name;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @OneToOne(mappedBy = "vehicle")
    @JsonIgnore
    private Driver driver;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private Set<Route> routes = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private Set<Seat> seats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public Vehicle vehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
        return this;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getType() {
        return type;
    }

    public Vehicle type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Vehicle capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Driver getDriver() {
        return driver;
    }

    public Vehicle driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public Vehicle routes(Set<Route> routes) {
        this.routes = routes;
        return this;
    }

    public Vehicle addRoute(Route route) {
        this.routes.add(route);
        route.setVehicle(this);
        return this;
    }

    public Vehicle removeRoute(Route route) {
        this.routes.remove(route);
        route.setVehicle(null);
        return this;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public Vehicle seats(Set<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public Vehicle addSeat(Seat seat) {
        this.seats.add(seat);
        seat.setVehicle(this);
        return this;
    }

    public Vehicle removeSeat(Seat seat) {
        this.seats.remove(seat);
        seat.setVehicle(null);
        return this;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
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
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", vehicle_name='" + getVehicle_name() + "'" +
            ", type='" + getType() + "'" +
            ", capacity='" + getCapacity() + "'" +
            "}";
    }
}
