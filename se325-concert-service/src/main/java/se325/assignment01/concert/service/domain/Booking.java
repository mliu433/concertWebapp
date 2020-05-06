package se325.assignment01.concert.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import se325.assignment01.concert.common.dto.SeatDTO;
import se325.assignment01.concert.common.jackson.LocalDateTimeDeserializer;
import se325.assignment01.concert.common.jackson.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "BOOKINGS")
public class Booking {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "CONCERT_ID", nullable = false)
    private long concertId;
    @Column(name = "DATE")
    private LocalDateTime date; //one concert a day?
    @JoinTable(name = "BOOKING_SEAT",
            joinColumns={@JoinColumn(
                    name="BOOKING_LABEL")},
            inverseJoinColumns={@JoinColumn(name="SEAT_ID")})
    @OneToMany(fetch = FetchType.EAGER)
    private List<Seat> seats = new ArrayList<>();
    @JoinColumn(name = "USER_ID")
    private long userId;

    public Booking(long concertId, LocalDateTime date, List<Seat> seats, long userId) {
        this.concertId = concertId;
        this.date = date;
        this.seats = seats;
        this.userId= userId;
    }

    public Booking() {
    }

    public Booking(long concertId, LocalDateTime date, List<Seat> seat_domain) {
        this.concertId = concertId;
        this.date = date;
        this.seats = seats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getConcertId() {
        return concertId;
    }

    public void setConcertId(long concertId) {
        this.concertId = concertId;
    }
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public long getUser(){
        return userId;
    }
    public void setUser(long userId){
        this.userId = userId;
    }
}
