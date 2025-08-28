package com.mystig.backend.model.artisan;



import com.mystig.backend.model.User;
import com.mystig.backend.model.enums.ArtisanCategory;
import com.mystig.backend.model.enums.ArtisanStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "artisan_products", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "category"),
        @Index(columnList = "owner_id"),
        @Index(columnList = "status")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ArtisanProduct {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ArtisanCategory category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String currency = "MAD";

    private String origin;
    private String craftsman;

    @ElementCollection
    @CollectionTable(name = "artisan_materials", joinColumns = @JoinColumn(name = "artisan_id"))
    @Column(name = "material")
    private Set<String> materials = new HashSet<>();

    private String dimensions;

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtisanImage> images = new ArrayList<>();

    private boolean inStock;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Enumerated(EnumType.STRING)
    private ArtisanStatus status;

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

    public List<ArtisanImage> getImages() {
        return images;
    }

    public void setImages(List<ArtisanImage> images) {
        this.images = images;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArtisanStatus getStatus() {
        return status;
    }

    public void setStatus(ArtisanStatus status) {
        this.status = status;
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
