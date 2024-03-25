package webCalendarSpring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EventRequestDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    private String event;
    @NotNull
    private LocalDate date;

    public EventRequestDTO(){}

    public EventRequestDTO(String event, LocalDate date) {
        this.event = event;
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
