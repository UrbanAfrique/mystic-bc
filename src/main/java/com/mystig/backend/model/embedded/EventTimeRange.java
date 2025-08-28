package com.mystig.backend.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventTimeRange {

    private LocalTime start;

    private LocalTime end;

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
