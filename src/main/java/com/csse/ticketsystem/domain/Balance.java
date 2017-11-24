package com.csse.ticketsystem.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Balance.
 */
@Entity
@Table(name = "balance")
@Document(indexName = "balance")
public class Balance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "current_amount", nullable = false)
    private Double currentAmount;

    @NotNull
    @Column(name = "reload_amount", nullable = false)
    private Double reloadAmount;

    @NotNull
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @NotNull
    @Column(name = "total", nullable = false)
    private Double total;

    @NotNull
    @Column(name = "jhi_time", nullable = false)
    private ZonedDateTime time;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private SmartCard smartCardID;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public Balance currentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
        return this;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getReloadAmount() {
        return reloadAmount;
    }

    public Balance reloadAmount(Double reloadAmount) {
        this.reloadAmount = reloadAmount;
        return this;
    }

    public void setReloadAmount(Double reloadAmount) {
        this.reloadAmount = reloadAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Balance paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getTotal() {
        return total;
    }

    public Balance total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Balance time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public SmartCard getSmartCardID() {
        return smartCardID;
    }

    public Balance smartCardID(SmartCard smartCard) {
        this.smartCardID = smartCard;
        return this;
    }

    public void setSmartCardID(SmartCard smartCard) {
        this.smartCardID = smartCard;
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
        Balance balance = (Balance) o;
        if (balance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Balance{" +
            "id=" + getId() +
            ", currentAmount='" + getCurrentAmount() + "'" +
            ", reloadAmount='" + getReloadAmount() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", total='" + getTotal() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
