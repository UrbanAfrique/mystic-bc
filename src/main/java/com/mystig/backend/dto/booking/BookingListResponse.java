package com.mystig.backend.dto.booking;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingListResponse {
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private List<BookingResponse> items;

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<BookingResponse> getItems() {
        return items;
    }

    public void setItems(List<BookingResponse> items) {
        this.items = items;
    }
}
