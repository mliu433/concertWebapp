package se325.assignment01.concert.service.domain;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import se325.assignment01.concert.common.jackson.LocalDateTimeDeserializer;

import javax.persistence.*;
import se325.assignment01.concert.common.jackson.LocalDateTimeSerializer;

@Entity
@Table(name ="CONCERTS")
public class Concert {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "IMAGE_NAME")
    private String imageName;
    @Column(name = "BLURB",columnDefinition = "TEXT")
    private String blurb;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CONCERT_DATES",joinColumns = @JoinColumn(name="CONCERT_ID"))
    @Column(name = "DATE")
    private Set<LocalDateTime> dates;

    @JoinTable(name = "CONCERT_PERFORMER",
            joinColumns={@JoinColumn(
                    name="CONCERT_ID")},
            inverseJoinColumns={@JoinColumn(name="PERFORMER_ID")})
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Performer> performers;

    public Concert(Long id, String title, String imageName, String blurb) {
        this.id = id;
        this.title = title;
        this.imageName = imageName;
        this.blurb = blurb;
    }

    public Concert(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Set<LocalDateTime> getDates() {
        return dates;
    }

    @JsonSerialize(contentUsing  = LocalDateTimeSerializer.class)
    @JsonDeserialize(contentUsing = LocalDateTimeDeserializer.class)
    public void setDates(Set<LocalDateTime> dates) {
        this.dates = dates;
    }

    public void setPerformers(Set<Performer> performers) {
        this.performers = performers;
    }

    public Set<Performer> getPerformers() {
       return performers;
    }

}
