package com.csse.ticketsystem.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Route.
 */
@Entity
@Table(name = "route")
@Document(indexName = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "route_name", nullable = false)
    private String route_name;

    @NotNull
    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "remarks")
    private String remarks;

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

    public String getRoute_name() {
        return route_name;
    }

    public Route route_name(String route_name) {
        this.route_name = route_name;
        return this;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public Double getDistance() {
        return distance;
    }

    public Route distance(Double distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getRemarks() {
        return remarks;
    }

    public Route remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Route vehicle(Vehicle vehicle) {
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
        Route route = (Route) o;
        if (route.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), route.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Route{" +
            "id=" + getId() +
            ", route_name='" + getRoute_name() + "'" +
            ", distance='" + getDistance() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
