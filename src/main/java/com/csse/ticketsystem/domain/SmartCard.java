package com.csse.ticketsystem.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SmartCard.
 */
@Entity
@Table(name = "smart_card")
@Document(indexName = "smartcard")
public class SmartCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "smart_card_id", nullable = false)
    private String smartCardID;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmartCardID() {
        return smartCardID;
    }

    public SmartCard smartCardID(String smartCardID) {
        this.smartCardID = smartCardID;
        return this;
    }

    public void setSmartCardID(String smartCardID) {
        this.smartCardID = smartCardID;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public SmartCard expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public SmartCard status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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
        SmartCard smartCard = (SmartCard) o;
        if (smartCard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smartCard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmartCard{" +
            "id=" + getId() +
            ", smartCardID='" + getSmartCardID() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
