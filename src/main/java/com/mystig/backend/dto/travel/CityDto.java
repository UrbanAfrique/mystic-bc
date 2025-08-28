package com.mystig.backend.dto.travel;

import lombok.*;

import java.util.UUID;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CityDto {
    private UUID id;
    private String name;
}
