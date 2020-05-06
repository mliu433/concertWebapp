package se325.assignment01.concert.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ConcertInfoNotification {
    @Column(name = "NUMSEATSREMAINING")
    private int numSeatsRemaining;
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    public ConcertInfoNotification() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ConcertInfoNotification(int numSeatsRemaining) {
        this.numSeatsRemaining = numSeatsRemaining;
    }

    public int getNumSeatsRemaining() {
        return numSeatsRemaining;
    }

    public void setNumSeatsRemaining(int numSeatsRemaining) {
        this.numSeatsRemaining = numSeatsRemaining;
    }
}
