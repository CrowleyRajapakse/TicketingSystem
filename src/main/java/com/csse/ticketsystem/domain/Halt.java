package com.csse.ticketsystem.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Halt.
 */
@Entity
@Table(name = "halt")
@Document(indexName = "halt")
public class Halt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_halt", nullable = false)
    private String start_halt;

    @NotNull
    @Column(name = "end_halt", nullable = false)
    private String end_halt;

    @NotNull
    @Column(name = "halt_distance", nullable = false)
    private Double halt_distance;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(optional = false)
    @NotNull
    private Route route;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart_halt() {
        return start_halt;
    }

    public Halt start_halt(String start_halt) {
        this.start_halt = start_halt;
        return this;
    }

    public void setStart_halt(String start_halt) {
        this.start_halt = start_halt;
    }

    public String getEnd_halt() {
        return end_halt;
    }

    public Halt end_halt(String end_halt) {
        this.end_halt = end_halt;
        return this;
    }

    public void setEnd_halt(String end_halt) {
        this.end_halt = end_halt;
    }

    public Double getHalt_distance() {
        return halt_distance;
    }

    public Halt halt_distance(Double halt_distance) {
        this.halt_distance = halt_distance;
        return this;
    }

    public void setHalt_distance(Double halt_distance) {
        this.halt_distance = halt_distance;
    }

    public Double getPrice() {
        return price;
    }

    public Halt price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Route getRoute() {
        return route;
    }

    public Halt route(Route route) {
        this.route = route;
        return this;
    }

    public void setRoute(Route route) {
        this.route = route;
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
        Halt halt = (Halt) o;
        if (halt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), halt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Halt{" +
            "id=" + getId() +
            ", start_halt='" + getStart_halt() + "'" +
            ", end_halt='" + getEnd_halt() + "'" +
            ", halt_distance='" + getHalt_distance() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
