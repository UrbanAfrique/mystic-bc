package com.mystig.backend.dto.booking;

import com.mystig.backend.model.enums.BookingStatus;
import com.mystig.backend.model.enums.PaymentStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateBookingStatusRequest {
    private BookingStatus status;
    private PaymentStatus paymentStatus; // optional

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
}
