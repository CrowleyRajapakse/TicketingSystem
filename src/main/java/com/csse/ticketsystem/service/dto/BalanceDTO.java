package com.csse.ticketsystem.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Balance entity.
 */
public class BalanceDTO implements Serializable {

    private Long id;

    @NotNull
    private Double currentAmount;

    @NotNull
    private Double reloadAmount;

    @NotNull
    private String paymentMethod;

    @NotNull
    private Double total;

    @NotNull
    private ZonedDateTime time;

    private Long smartCardIDId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getReloadAmount() {
        return reloadAmount;
    }

    public void setReloadAmount(Double reloadAmount) {
        this.reloadAmount = reloadAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Long getSmartCardIDId() {
        return smartCardIDId;
    }

    public void setSmartCardIDId(Long smartCardId) {
        this.smartCardIDId = smartCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BalanceDTO balanceDTO = (BalanceDTO) o;
        if(balanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BalanceDTO{" +
            "id=" + getId() +
            ", currentAmount='" + getCurrentAmount() + "'" +
            ", reloadAmount='" + getReloadAmount() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", total='" + getTotal() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
