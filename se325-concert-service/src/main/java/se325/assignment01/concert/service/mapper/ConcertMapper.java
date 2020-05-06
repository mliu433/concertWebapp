package se325.assignment01.concert.service.mapper;

import org.hibernate.Hibernate;
import se325.assignment01.concert.common.dto.ConcertDTO;
import se325.assignment01.concert.common.dto.ConcertSummaryDTO;
import se325.assignment01.concert.common.dto.PerformerDTO;
import se325.assignment01.concert.service.domain.Concert;
import se325.assignment01.concert.service.domain.Performer;

import javax.sound.midi.SysexMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConcertMapper {

    public static Concert toDomainModel(ConcertDTO dtoConcert) {
        List<PerformerDTO> performers = dtoConcert.getPerformers();
        Set<Performer> performerDTOS = null;
        for (PerformerDTO p : performers){
            performerDTOS.add(PerformerMapper.toDomainModel(p));
        }

        List<LocalDateTime> date = dtoConcert.getDates();
        Concert fullConcert = new Concert(
                dtoConcert.getId(),
                dtoConcert.getTitle(),
                dtoConcert.getImageName(),
                dtoConcert.getBlurb());
        fullConcert.setPerformers(performerDTOS);
        fullConcert.setDates((Set<LocalDateTime>) date);
        return fullConcert;
    }

    public static se325.assignment01.concert.common.dto.ConcertDTO toDto(Concert concert) {
        Set<Performer> performers = concert.getPerformers();
        List<PerformerDTO> performerDTOS = new ArrayList<>();

        Set<LocalDateTime> time = concert.getDates();
        List<LocalDateTime> list = new ArrayList<>();

        for (Performer p : performers){
            performerDTOS.add(PerformerMapper.toDto(p));
        }
        for(LocalDateTime t: time){
            list.add(t);
        }
        se325.assignment01.concert.common.dto.ConcertDTO fullConcert =
                new ConcertDTO(
                        concert.getId(),
                        concert.getTitle(),
                        concert.getImageName(),
                        concert.getBlurb());
        fullConcert.setPerformers(performerDTOS);
        fullConcert.setDates(list);
        return fullConcert;
    }


    public static se325.assignment01.concert.common.dto.ConcertSummaryDTO toSummaryDto(Concert concert) {

        se325.assignment01.concert.common.dto.ConcertSummaryDTO fullConcert =
                new ConcertSummaryDTO(
                        concert.getId(),
                        concert.getTitle(),
                        concert.getImageName());

        return fullConcert;

    }
}
