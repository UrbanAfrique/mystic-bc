package com.mystig.backend.dto.hotel;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HotelListResponse {
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private List<HotelResponse> items;

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

    public List<HotelResponse> getItems() {
        return items;
    }

    public void setItems(List<HotelResponse> items) {
        this.items = items;
    }
}
