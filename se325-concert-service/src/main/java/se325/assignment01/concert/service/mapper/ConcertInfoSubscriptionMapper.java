package se325.assignment01.concert.service.mapper;

import se325.assignment01.concert.common.dto.ConcertInfoSubscriptionDTO;
import se325.assignment01.concert.service.domain.ConcertInfoSubscription;

public class ConcertInfoSubscriptionMapper {
    public static ConcertInfoSubscription toDomainModel(ConcertInfoSubscriptionDTO dtoConcert) {
        ConcertInfoSubscription fullConcert = new ConcertInfoSubscription(
                dtoConcert.getConcertId(),
                dtoConcert.getDate(),
                dtoConcert.getPercentageBooked());

        return fullConcert;
    }

    public static ConcertInfoSubscriptionDTO toDto(ConcertInfoSubscription concert) {
        se325.assignment01.concert.common.dto.ConcertInfoSubscriptionDTO dtoParolee =
                new ConcertInfoSubscriptionDTO(
                        concert.getConcertId(),
                        concert.getDate(),
                        concert.getPercentageBooked()
                        );
        return dtoParolee;
    }
}
