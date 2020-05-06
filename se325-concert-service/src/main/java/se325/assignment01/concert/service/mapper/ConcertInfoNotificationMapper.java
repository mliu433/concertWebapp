package se325.assignment01.concert.service.mapper;

import se325.assignment01.concert.common.dto.ConcertInfoNotificationDTO;
import se325.assignment01.concert.service.domain.ConcertInfoNotification;

public class ConcertInfoNotificationMapper {
    static ConcertInfoNotification toDomainModel(ConcertInfoNotificationDTO dtoConcert) {
        ConcertInfoNotification fullConcert = new ConcertInfoNotification(
                dtoConcert.getNumSeatsRemaining());

        return fullConcert;
    }

    static se325.assignment01.concert.common.dto.ConcertInfoNotificationDTO toDto(ConcertInfoNotification concert) {
        se325.assignment01.concert.common.dto.ConcertInfoNotificationDTO dtoParolee =
                new ConcertInfoNotificationDTO(
                        concert.getNumSeatsRemaining()
                       );
        return dtoParolee;
    }
}
