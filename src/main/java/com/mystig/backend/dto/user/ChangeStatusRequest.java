package com.mystig.backend.dto.user;

import lombok.Data;

@Data
public class ChangeStatusRequest {
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
