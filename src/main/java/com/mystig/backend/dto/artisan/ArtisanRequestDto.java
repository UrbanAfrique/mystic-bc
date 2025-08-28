package com.mystig.backend.dto.artisan;

import com.mystig.backend.model.enums.ArtisanCategory;
import com.mystig.backend.model.enums.ArtisanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ArtisanRequestDto {
    private String name;
    private ArtisanCategory category;
    private String description;
    private BigDecimal price;
    private String currency;
    private String origin;
    private String craftsman;
    private Set<String> materials;
    private String dimensions;
    private List<ArtisanImageDto> images;
    private Boolean inStock;
    private Integer quantity;
    private UUID ownerId;
    private ArtisanStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArtisanCategory getCategory() {
        return category;
    }

    public void setCategory(ArtisanCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCraftsman() {
        return craftsman;
    }

    public void setCraftsman(String craftsman) {
        this.craftsman = craftsman;
    }

    public Set<String> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<String> materials) {
        this.materials = materials;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public List<ArtisanImageDto> getImages() {
        return images;
    }

    public void setImages(List<ArtisanImageDto> images) {
        this.images = images;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public ArtisanStatus getStatus() {
        return status;
    }

    public void setStatus(ArtisanStatus status) {
        this.status = status;
    }
}
