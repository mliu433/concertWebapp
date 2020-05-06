package se325.assignment01.concert.service.mapper;

import se325.assignment01.concert.common.dto.BookingDTO;
import se325.assignment01.concert.common.dto.SeatDTO;
import se325.assignment01.concert.service.domain.Booking;
import se325.assignment01.concert.service.domain.Seat;
import se325.assignment01.concert.service.mapper.SeatMapper;
import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    public static Booking toDomainModel(BookingDTO dtoBooking) {
        List<SeatDTO> seats = dtoBooking.getSeats();
        List<Seat> seat_domain = new ArrayList<>();
        SeatMapper map = new SeatMapper();
        for(SeatDTO s : seats){
            seat_domain.add(SeatMapper.toDomainModel(s));
        }

       Booking fullConcert = new Booking(
                dtoBooking.getConcertId(),
                dtoBooking.getDate(),
                seat_domain);
        return fullConcert;
    }

    public static BookingDTO toDto(Booking booking) {
        List<Seat> seats = booking.getSeats();
        List<SeatDTO> seat_dto = new ArrayList<>();
        SeatMapper map = new SeatMapper();
        for(Seat s : seats){
            seat_dto.add(SeatMapper.toDto(s));
        }
        se325.assignment01.concert.common.dto.BookingDTO dtoParolee =
                new BookingDTO(
                       booking.getConcertId(),
                        booking.getDate(),
                        seat_dto);

        return dtoParolee;
    }
}
