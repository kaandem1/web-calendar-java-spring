package webCalendarSpring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EventResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @NotNull
    @NotEmpty
    @NotBlank
    private String event;
    @NotNull
    @NotEmpty
    @NotBlank
    private LocalDate date;

    public EventResponseDTO(){}

    public EventResponseDTO(String event, LocalDate date) {
        this.event = event;
        this.date = date;
    }

    public EventResponseDTO(String message, String event, LocalDate date) {
        this.message = message;
        this.event = event;
        this.date = date;
    }

    public EventResponseDTO(Long id, String event, LocalDate date) {
        this.id = id;
        this.event = event;
        this.date = date;
    }

    public EventResponseDTO(String message, Long id, String event, LocalDate date) {
        this.message = message;
        this.id = id;
        this.event = event;
        this.date = date;
    }

    public EventResponseDTO(Long id, String message, String event, LocalDate date) {
        this.id = id;
        this.message = message;
        this.event = event;
        this.date = date;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
