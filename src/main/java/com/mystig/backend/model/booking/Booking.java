package com.mystig.backend.model.booking;

import com.mystig.backend.model.User;
import com.mystig.backend.model.embedded.CustomerInfo;
import com.mystig.backend.model.embedded.ItemDetails;
import com.mystig.backend.model.enums.BookingStatus;
import com.mystig.backend.model.enums.BookingType;
import com.mystig.backend.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bookings", indexes = {
        @Index(columnList = "bookingNumber", unique = true),
        @Index(columnList = "bookingType"),
        @Index(columnList = "itemId"),
        @Index(columnList = "status"),
        @Index(columnList = "paymentStatus")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String bookingNumber; // e.g., BK-2025-000001

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "customer_name")),
            @AttributeOverride(name = "email", column = @Column(name = "customer_email")),
            @AttributeOverride(name = "phone", column = @Column(name = "customer_phone")),
            @AttributeOverride(name = "address.street", column = @Column(name = "customer_street")),
            @AttributeOverride(name = "address.city", column = @Column(name = "customer_city")),
            @AttributeOverride(name = "address.country", column = @Column(name = "customer_country")),
            @AttributeOverride(name = "address.zipCode", column = @Column(name = "customer_zip_code"))
    })
    private CustomerInfo customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingType bookingType; // HOTEL, EVENT, PACKAGE

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID itemId; // FK id of hotel/event/package (stored as UUID)

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "item_name")),
            @AttributeOverride(name = "description", column = @Column(name = "item_description")),
            @AttributeOverride(name = "startDate", column = @Column(name = "item_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "item_end_date"))
    })
    private ItemDetails itemDetails;

    private Integer participants;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Builder.Default
    private String currency = "MAD";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String paymentMethod; // "stripe", "paypal", etc.

    @Column(columnDefinition = "TEXT")
    private String specialRequests;

    @ManyToOne(fetch = FetchType.LAZY)
    private User seller; // who owns the product/listing

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfo customer) {
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

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetails itemDetails) {
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
