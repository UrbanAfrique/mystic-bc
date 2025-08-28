package com.mystig.backend.mapper;

import com.mystig.backend.dto.booking.*;
import com.mystig.backend.model.User;
import com.mystig.backend.model.booking.Booking;
import com.mystig.backend.model.embedded.Address;
import com.mystig.backend.model.embedded.CustomerInfo;
import com.mystig.backend.model.embedded.ItemDetails;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingMapper {

    public Booking toEntity(BookingRequest r, User seller, String bookingNumber) {
        Booking b = new Booking();
        b.setBookingNumber(bookingNumber);
        b.setBookingType(r.getBookingType());
        b.setItemId(r.getItemId());
        if (r.getItemDetails() != null) {
            b.setItemDetails(ItemDetails.builder()
                    .name(r.getItemDetails().getName())
                    .description(r.getItemDetails().getDescription())
                    .startDate(r.getItemDetails().getStartDate())
                    .endDate(r.getItemDetails().getEndDate())
                    .build());
        }
        if (r.getCustomer() != null) {
            b.setCustomer(CustomerInfo.builder()
                    .name(r.getCustomer().getName())
                    .email(r.getCustomer().getEmail())
                    .phone(r.getCustomer().getPhone())
                    .address(Address.builder()
                            .street(r.getCustomer().getStreet())
                            .city(r.getCustomer().getCity())
                            .country(r.getCustomer().getCountry())
                            .zipCode(r.getCustomer().getZipCode())
                            .build())
                    .build());
        }
        b.setParticipants(r.getParticipants());
        b.setTotalAmount(r.getTotalAmount());
        b.setCurrency(r.getCurrency() == null ? "MAD" : r.getCurrency());
        b.setPaymentMethod(r.getPaymentMethod());
        b.setSpecialRequests(r.getSpecialRequests());
        b.setSeller(seller);
        // status defaults in service
        return b;
    }

    public BookingResponse toDto(Booking b) {
        return BookingResponse.builder()
                .id(b.getId())
                .bookingNumber(b.getBookingNumber())
                .bookingType(b.getBookingType())
                .itemId(b.getItemId())
                .itemDetails(b.getItemDetails() == null ? null : ItemDetailsDto.builder()
                        .name(b.getItemDetails().getName())
                        .description(b.getItemDetails().getDescription())
                        .startDate(b.getItemDetails().getStartDate())
                        .endDate(b.getItemDetails().getEndDate())
                        .build())
                .customer(b.getCustomer() == null ? null : CustomerDto.builder()
                        .name(b.getCustomer().getName())
                        .email(b.getCustomer().getEmail())
                        .phone(b.getCustomer().getPhone())
                        .street(b.getCustomer().getAddress() == null ? null : b.getCustomer().getAddress().getStreet())
                        .city(b.getCustomer().getAddress() == null ? null : b.getCustomer().getAddress().getCity())
                        .country(b.getCustomer().getAddress() == null ? null : b.getCustomer().getAddress().getCountry())
                        .zipCode(b.getCustomer().getAddress() == null ? null : b.getCustomer().getAddress().getZipCode())
                        .build())
                .participants(b.getParticipants())
                .totalAmount(b.getTotalAmount())
                .currency(b.getCurrency())
                .status(b.getStatus())
                .paymentStatus(b.getPaymentStatus())
                .paymentMethod(b.getPaymentMethod())
                .specialRequests(b.getSpecialRequests())
                .sellerId(b.getSeller() == null ? null : b.getSeller().getId())
                .createdAt(b.getCreatedAt())
                .updatedAt(b.getUpdatedAt())
                .build();
    }
}

