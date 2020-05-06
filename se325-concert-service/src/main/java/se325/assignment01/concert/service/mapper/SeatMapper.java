package se325.assignment01.concert.service.mapper;

import se325.assignment01.concert.common.dto.SeatDTO;
import se325.assignment01.concert.service.domain.Seat;

public class SeatMapper {
    static Seat toDomainModel(SeatDTO dtoSeat) {
        Seat fullConcert = new Seat(
                dtoSeat.getLabel(),
                false,
                dtoSeat.getPrice());

        return fullConcert;
    }

    public static se325.assignment01.concert.common.dto.SeatDTO toDto(Seat concert) {
        se325.assignment01.concert.common.dto.SeatDTO dtoSeat =
                new SeatDTO(
                        concert.getLabel(),
                        concert.getPrice()
                       );
        return dtoSeat;
    }


}
