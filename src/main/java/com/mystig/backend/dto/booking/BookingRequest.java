package com.mystig.backend.dto.booking;

import com.mystig.backend.model.enums.BookingType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingRequest {
    private CustomerDto customer;
    private BookingType bookingType;
    private UUID itemId;
    private ItemDetailsDto itemDetails;
    private Integer participants;
    private BigDecimal totalAmount;
    private String currency;      // defaults to MAD if null
    private String paymentMethod; // "stripe","paypal",...
    private String specialRequests;
    private UUID sellerId;        // owner/agent handling the booking

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public ItemDetailsDto getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetailsDto itemDetails) {
        this.itemDetails = itemDetails;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }
}
