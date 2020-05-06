package se325.assignment01.concert.service.mapper;

import se325.assignment01.concert.common.dto.PerformerDTO;
import se325.assignment01.concert.service.domain.Performer;
import sun.misc.Perf;

public class PerformerMapper {
    public static Performer toDomainModel(PerformerDTO dtoConcert) {
        Performer fullConcert = new Performer(
                dtoConcert.getId(),
                dtoConcert.getName(),
                dtoConcert.getImageName(),
                dtoConcert.getGenre(),
                dtoConcert.getBlurb());
        return fullConcert;
    }

   public static se325.assignment01.concert.common.dto.PerformerDTO toDto(Performer concert) {
        se325.assignment01.concert.common.dto.PerformerDTO dtoParolee =
                new PerformerDTO(
                        concert.getId(),
                        concert.getName(),
                        concert.getImageName(),
                        concert.getGenre(),
                        concert.getBlurb());
        return dtoParolee;
    }
}
