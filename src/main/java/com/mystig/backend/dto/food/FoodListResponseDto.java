package com.mystig.backend.dto.food;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FoodListResponseDto {
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private List<FoodResponseDto> items;

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

    public List<FoodResponseDto> getItems() {
        return items;
    }

    public void setItems(List<FoodResponseDto> items) {
        this.items = items;
    }
}
