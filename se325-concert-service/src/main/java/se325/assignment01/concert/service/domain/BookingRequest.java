package se325.assignment01.concert.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import se325.assignment01.concert.common.jackson.LocalDateTimeDeserializer;
import se325.assignment01.concert.common.jackson.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BookingRequest {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "CONCERT_ID", nullable = false)
    private long concertId;
    private LocalDateTime date;
    @ElementCollection
    @CollectionTable(name = "SEAT")
    private List<String> seatLabels = new ArrayList<>();

    public BookingRequest(){}

    public BookingRequest(long concertId, LocalDateTime date) {
        this.concertId = concertId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingRequest(long concertId, LocalDateTime date, List<String> seatLabels) {
        this.concertId = concertId;
        this.date = date;
        this.seatLabels = seatLabels;
    }

    public long getConcertId() {
        return concertId;
    }

    public void setConcertId(long concertId) {
        this.concertId = concertId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<String> getSeatLabels() {
        return seatLabels;
    }

    public void setSeatLabels(List<String> seatLabels) {
        this.seatLabels = seatLabels;
    }
}
