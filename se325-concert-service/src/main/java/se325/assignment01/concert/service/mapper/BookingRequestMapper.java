package se325.assignment01.concert.service.mapper;

import se325.assignment01.concert.common.dto.BookingRequestDTO;
import se325.assignment01.concert.service.domain.BookingRequest;

public class BookingRequestMapper {
    static BookingRequest toDomainModel(BookingRequestDTO dtoConcert) {
        //TODO: TWO PARAMETER
        BookingRequest fullConcert = new BookingRequest(
                dtoConcert.getConcertId(),
                dtoConcert.getDate(),
                dtoConcert.getSeatLabels()
                );

        return fullConcert;
    }

    static se325.assignment01.concert.common.dto.BookingRequestDTO toDto(BookingRequest concert) {
        se325.assignment01.concert.common.dto.BookingRequestDTO dtoParolee =
                new BookingRequestDTO(
                        concert.getConcertId(),
                        concert.getDate(),
                        concert.getSeatLabels()
                       );
        return dtoParolee;
    }
}
