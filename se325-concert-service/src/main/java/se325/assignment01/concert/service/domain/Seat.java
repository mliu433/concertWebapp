package se325.assignment01.concert.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import se325.assignment01.concert.common.dto.SeatDTO;
import se325.assignment01.concert.common.jackson.LocalDateTimeDeserializer;
import se325.assignment01.concert.common.jackson.LocalDateTimeSerializer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Table(name = "SEATS")
public class Seat {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	@Version
	long version=0;
	@Column(name = "LABEL")
	private String label;
	@Column(name = "PRICE")
	private BigDecimal price;
	@Column(name = "STATUS")
	private boolean status;
    @Column(name = "DATE")
    private LocalDateTime date;
	public Seat() {}

	public Seat(String label, boolean status, BigDecimal price) {
		this.label = label;
		this.status = status;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Seat(String seatLabel, boolean b, LocalDateTime date, BigDecimal price) {
		this.label = seatLabel;
		this.status = b;
		this.date=date;
		this.price = price;
	}

	public LocalDateTime getDate() {
		return date;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean booked) {
		status = booked;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
