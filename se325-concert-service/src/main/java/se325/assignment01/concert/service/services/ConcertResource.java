package se325.assignment01.concert.service.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se325.assignment01.concert.common.dto.*;
import se325.assignment01.concert.service.domain.*;
import se325.assignment01.concert.service.jaxrs.LocalDateTimeParam;
import se325.assignment01.concert.service.mapper.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Path("/concert-service")
public class ConcertResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcertResource.class);
    private Map<Long, Concert> concertDB;
    private AtomicLong idCounter;
    private final List<AsyncResponse> concertInfoSubscriptions = new Vector<>();
    /**
     * get a single concert
     * @param id unique identifier for each concert
     **/
    @GET
    @Path("/concerts/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleConcert(@PathParam("id") long id) {
        Concert concert;
        EntityManager em = PersistenceManager.instance().createEntityManager();
        try {
            concert = em.find(Concert.class, new Long(id));
        } finally {
            em.close();
        }
        Response.ResponseBuilder builder = null;
        if (concert != null) {
            ConcertDTO concertDTO = ConcertMapper.toDto(concert);
            GenericEntity<ConcertDTO> entity =
                    new GenericEntity<ConcertDTO>(concertDTO) {
                    };
            builder = Response.ok(entity);
            Response response = builder.build();

            return response;
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    /**
     * get all concerts method
     **/
    @GET
    @Path("/concerts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConcerts() {
        List<Concert> concerts;
        EntityManager em = PersistenceManager.instance().createEntityManager();
        //retrieve all the concerts from concert entity table
        TypedQuery<Concert> pQuery = em.createQuery("select p from Concert p", Concert.class);

        concerts = pQuery.getResultList();

        List<ConcertDTO> concertDTOs = new ArrayList<>();
        Response.ResponseBuilder builder = null;

        for (Concert p : concerts) {
            ConcertDTO dto = ConcertMapper.toDto(p);
            concertDTOs.add(dto);
        }

        GenericEntity<List<ConcertDTO>> entity =
                new GenericEntity<List<ConcertDTO>>(concertDTOs) {
                };
        builder = Response.ok(entity);
        Response response = builder.build();
        return response;
    }

    /**
     * get all performers method
     **/
    @GET
    @Path("/performers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPerformers() {
        List<Performer> performers;
        EntityManager em = PersistenceManager.instance().createEntityManager();
        TypedQuery<Performer> pQuery = em.createQuery("select p from Performer p",
                Performer.class);
        performers = pQuery.getResultList();
        List<PerformerDTO> performerDTOs = new ArrayList<>();
        Response.ResponseBuilder builder = null;

        if (performers.isEmpty() == false) {
            for (Performer p : performers) {
                PerformerDTO dto = PerformerMapper.toDto(p);
                performerDTOs.add(dto);
            }
            GenericEntity<List<PerformerDTO>> entity =
                    new GenericEntity<List<PerformerDTO>>(performerDTOs) {
                    };
            builder = Response.ok(entity);
            Response response = builder.build();
            return response;
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * get a single concerts method
     * @Param: id unique identifier for each performer
     **/
    @GET
    @Path("/performers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSinglePerformer(@PathParam("id") long id) {
        Performer performer;
        EntityManager em = PersistenceManager.instance().createEntityManager();
        try {
            performer = em.find(Performer.class, new Long(id));
        } finally {
            em.close();
        }
        Response.ResponseBuilder builder = null;
        if (performer != null) {
            PerformerDTO concertDTO = PerformerMapper.toDto(performer);
            GenericEntity<PerformerDTO> entity =
                    new GenericEntity<PerformerDTO>(concertDTO) {
                    };
            builder = Response.ok(entity);
            Response response = builder.build();

            return response;
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    /**
     * get all concert summaries method
     **/
    @GET
    @Path("/concerts/summaries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConcertSummaries() {
        List<Concert> concerts;
        EntityManager em = PersistenceManager.instance().createEntityManager();
        TypedQuery<Concert> pQuery = em.createQuery("select p from Concert p", Concert.class);

        concerts = pQuery.getResultList();

        List<ConcertSummaryDTO> concertDTOs = new ArrayList<>();
        Response.ResponseBuilder builder = null;

        for (Concert p : concerts) {
            ConcertSummaryDTO dto = ConcertMapper.toSummaryDto(p);
            concertDTOs.add(dto);
        }

        GenericEntity<List<ConcertSummaryDTO>> entity =
                new GenericEntity<List<ConcertSummaryDTO>>(concertDTOs) {
                };
        builder = Response.ok(entity);
        Response response = builder.build();
        return response;
    }

    /**
     * get a single booking based on the booking id,
     * check if the user is authorised
     * @Param: @PathParam("id") id unique identifier for each booking
     * @Param: @CookieParam("auth") token, to authorise the client
     * String token
     **/
    @GET
    @Path("/bookings/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleBooking(@PathParam("id") long id, @CookieParam("auth") String token) {
        EntityManager entityManager = PersistenceManager.instance().createEntityManager();
        User user = null;
        //check if the client is authorised
        try {
            TypedQuery<User> query = entityManager.createQuery
                    ("select user from User user where user.token=:token", User.class)
                    .setParameter("token", token);
            user = query.getSingleResult();
            Booking booking;
            TypedQuery<Booking> pQuery = entityManager.createQuery
                    ("select b from Booking b where b.userId =:userId", Booking.class)
                    .setParameter("userId", user.getId());
            booking = pQuery.getSingleResult();
            Response.ResponseBuilder builder = null;
            if (booking != null) {
                BookingDTO concertDTO = BookingMapper.toDto(booking);
                GenericEntity<BookingDTO> entity =
                        new GenericEntity<BookingDTO>(concertDTO) {
                        };
                builder = Response.ok(entity);
                Response response = builder.build();

                return response;
            }
        } catch (NoResultException n) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } finally {
            entityManager.close();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * get all bookings
     * check if the user is authorised
     * @Param: @CookieParam("auth") token, to authorise the client
     **/
    @GET
    @Path("/bookings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings(@CookieParam("auth") String token) {
            EntityManager entityManager = PersistenceManager.instance().createEntityManager();
            User user = null;
            try {
                //using token-authorisation to check if the user has been authorised
                TypedQuery<User> query = entityManager.
                        createQuery("select user from User user where user.token=:token",
                                User.class)
                        .setParameter("token", token);
                user = query.getSingleResult();
                //if the user has been authorised, query bookings
                EntityManager em = PersistenceManager.instance().createEntityManager();
                List<Booking> bookings = null;
                try {
                    TypedQuery<Booking> pQuery = em.createQuery
                            ("select b from Booking b where b.userId =:userId", Booking.class)
                            .setParameter("userId", user.getId());
                    bookings = pQuery.getResultList();
                    List<BookingDTO> bookingDTOs = new ArrayList<>();
                    Response.ResponseBuilder builder = null;
                    for (Booking booking : bookings) {
                        BookingDTO dto = BookingMapper.toDto(booking);
                        bookingDTOs.add(dto);
                    }
                    GenericEntity<List<BookingDTO>> entity =
                            new GenericEntity<List<BookingDTO>>(bookingDTOs) {
                            };
                    builder = Response.ok(entity);
                    Response response = builder.build();
                    return response;
                } catch (NoResultException n) {
                    //if the booking is empty
                    return Response.status(Response.Status.BAD_REQUEST).build();
                } finally {
                    em.close();
                }
            } catch (NoResultException n) {
                //if the client is unauthorised
                return Response.status(Response.Status.UNAUTHORIZED).build();
            } finally {
                entityManager.close();
            }

    }
    /**
     * get all the seats booking based on the status of seat,
     * @Param: @PathParam("date")
     * @Param: @QueryParam("auth") status
     **/
    @GET
    @Path("/seats/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSeatsForDate(@PathParam("date") String date,
                                       @QueryParam("status") String status) {
        //if it's querying all the seats regardless of booked or unbooked
        if (status.toLowerCase().equals("any")) {
            LocalDateTimeParam format = new LocalDateTimeParam(date);
            LocalDateTime newDate = format.getLocalDateTime();
            List<Seat> seats;
            EntityManager em = PersistenceManager.instance().createEntityManager();
            try {
                TypedQuery<Seat> pQuery = em.createQuery
                        ("select s from Seat s where s.date=:newDate", Seat.class).
                        setParameter("newDate", newDate);
                seats = pQuery.getResultList();
            } finally {
                em.close();
            }
            //map each seat to seatDTO
            Response.ResponseBuilder builder = null;
            List<SeatDTO> seatDTOs = new ArrayList<>();
            for (Seat p : seats) {
                SeatDTO dto = SeatMapper.toDto(p);
                seatDTOs.add(dto);
            }
            GenericEntity<List<SeatDTO>> entity =
                    new GenericEntity<List<SeatDTO>>(seatDTOs) {
                    };
            builder = Response.ok(entity);
            Response response = builder.build();
            return response;
        } else if (status.toLowerCase().equals("booked") || status.toLowerCase().equals("unbooked")) {
            boolean isBooked = status.toLowerCase().equals("booked");
            LocalDateTimeParam format = new LocalDateTimeParam(date);
            LocalDateTime newDate = format.getLocalDateTime();
            List<Seat> seats;
            EntityManager em = PersistenceManager.instance().createEntityManager();
            //query seats based on date and seat status
            try {
                TypedQuery<Seat> pQuery = em.createQuery
                        ("select s from Seat s where s.date=:newDate and s.status =:status",
                                Seat.class).
                        setParameter("newDate", newDate).setParameter("status", isBooked);
                seats = pQuery.getResultList();
            } finally {
                em.close();
            }
            //map each seat to seatDTO
            Response.ResponseBuilder builder = null;
            List<SeatDTO> seatDTOs = new ArrayList<>();
            for (Seat p : seats) {
                SeatDTO dto = SeatMapper.toDto(p);
                seatDTOs.add(dto);
            }
            GenericEntity<List<SeatDTO>> entity =
                    new GenericEntity<List<SeatDTO>>(seatDTOs) {
                    };
            builder = Response.ok(entity);
            Response response = builder.build();
            return response;
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    /**
     * create a booking request
     * check if the user is authorised
     * @Param: BookingRequestDTO
     * @Param: @CookieParam("auth") token, to authorise the client
     * String token
     **/
    @POST
    @Path("/bookings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBookingRequest(BookingRequestDTO requestDTO,
                                         @CookieParam("auth") String token) {
        //check the existence of token
        if(token ==null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        //check if the token identify a user
        User user = null;
        EntityManager entityManager = PersistenceManager.instance().createEntityManager();
        try {
            TypedQuery<User> query = entityManager.createQuery
                    ("select user from User user where user.token=:token", User.class)
                    .setParameter("token", token);
            user = query.getSingleResult();
        } catch (NoResultException n) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } finally {
            entityManager.close();
        }

        Concert concert = null;
        //check if the booking is with the correct concert id
        EntityManager em = PersistenceManager.instance().createEntityManager();
        try {
            Long id = requestDTO.getConcertId();
            //ensure to book the concert with correct date
            concert = em.find(Concert.class, id);
            if (concert == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else if (!concert.getDates().contains(requestDTO.getDate())) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                //book the unbooked seats
                List<String> labels = requestDTO.getSeatLabels();
                Response response = null;
                //get the seats from date and concert id
                EntityManager em2 = PersistenceManager.instance().createEntityManager();
                List<Seat> seats = new ArrayList<>();
                try {
                    for (int i = 0; i < labels.size(); i++) {
                        TypedQuery<Seat> sQuery = em2.createQuery
                                ("select s from Seat s where s.label=:labels and s.date =:date",
                                        Seat.class).
                                setParameter("labels", labels.get(i))
                                .setParameter("date", requestDTO.getDate());
                        Seat seat = sQuery.getSingleResult(); //maybe return null
                        seats.add(seat);
                    }

                } catch (NoResultException n) {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                } finally {
                    em2.close();
                }
                Booking booking = new Booking(concert.getId(), requestDTO.getDate(),
                        seats, user.getId());
                //check if the seats have been booked
                for (Seat each : seats) {
                    if (each.getStatus()) {
                        //if any seat has been booked in the booking request
                        //the booking request will fail
                        return Response.status(Response.Status.FORBIDDEN).build();
                    }
                }
                //create the actual booking
                response = createBooking(booking);
                return response;
            }
        } catch (NoResultException nre) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } finally {
            em.close();
        }
    }
    /**
     * create a booking
     * check if the user is authorised
     * @Param: Booking
     **/
    public Response createBooking(Booking booking) {
        List<Seat> seats = booking.getSeats();

        EntityManager em = PersistenceManager.instance().createEntityManager();
        em.getTransaction().begin();
        for (Seat seat : seats) {
            seat.setStatus(true);
        }
        for (Seat seat : seats) {
            em.merge(seat);
        }
        em.persist(booking);
        //get all the subscribes based on the concert id
        TypedQuery<ConcertInfoSubscription> subs = em.createQuery
                ("select s from ConcertInfoSubscription s where s.concertId =:id"
                        ,ConcertInfoSubscription.class)
                .setParameter("id", booking.getConcertId());
        List<ConcertInfoSubscription> subList = subs.getResultList();
        em.getTransaction().commit();
        em.close();
        //map ConcertInfoSubscription to ConcertInfoSubscriptionDTO
        List<ConcertInfoSubscriptionDTO> subscriptionDTOs = new ArrayList<>();
        for (ConcertInfoSubscription s : subList) {
            ConcertInfoSubscriptionDTO dto = ConcertInfoSubscriptionMapper.toDto(s);
            subscriptionDTOs.add(dto);
        }
        //To get the remaining seats number
        //first to get the total number of seats
        EntityManager entityManager = PersistenceManager.instance().createEntityManager();
        for (ConcertInfoSubscriptionDTO subscriptionDTO : subscriptionDTOs) {
            TypedQuery<Seat> numberOfSeats = entityManager.createQuery
                    ("select s from Seat s where s.date =:date", Seat.class)
                    .setParameter("date", subscriptionDTO.getDate());
            int size = numberOfSeats.getResultList().size();
            TypedQuery<Booking> bookings = entityManager.createQuery
                    ("select s from Booking s where s.date =:date " +
                            "and s.concertId =:concertId", Booking.class)
                    .setParameter("date", subscriptionDTO.getDate()).setParameter("concertId",
                            subscriptionDTO.getConcertId());
            List<Booking> bookingList = bookings.getResultList();
            int bookingSize = bookingList.size();
            int seatRemain = size - bookingSize;
            Long booking_concertId = (Long) booking.getConcertId();
            Long sub_concertId = (Long) subscriptionDTO.getConcertId();
            //find the right subscriptions to process
            //match concert id and date
            if(booking_concertId.equals(sub_concertId) &&
                    booking.getDate().toString().equals(subscriptionDTO.getDate().toString())){
            processConcertSubscription(subscriptionDTO, new ConcertInfoNotificationDTO(seatRemain));}
        }
        entityManager.close();
        //return response with correct uri
        return Response.created(URI.create("/concert-service/bookings/" + booking.getId())).build();
    }

    /**
     * check if the user has logged in
     * @Param: userDTO
     * @Param: clientId
     **/
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(UserDTO userDTO, Cookie clientId) {
        //find username,if exist check if password matched
        User user = null;
        EntityManager em = PersistenceManager.instance().createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery
                    ("select u from User u where u.username= :username", User.class)
                    .setParameter("username", userDTO.getUsername());
            user = query.getSingleResult();
            if (user != null && user.getPassword().equals(userDTO.getPassword())) {
                GenericEntity<UserDTO> entity = new GenericEntity<UserDTO>(userDTO) {
                };
                Response.ResponseBuilder builder = Response.ok().cookie(makeCookie(user, clientId));
                Response response = builder.build();
                Response.Status.OK.getStatusCode();
                return response;
            } else {
                Response.Status.UNAUTHORIZED.getStatusCode();
                return Response.status(401).build();
            }
        } catch (NoResultException nre) {
            Response.Status.UNAUTHORIZED.getStatusCode();
            return Response.status(401).build();
        } finally {
            em.close();
        }
    }

    /**
     * generate cookie and token and store them in database
     * @Param: user
     * @Param: username
     **/
    private NewCookie makeCookie(User user, Cookie username) {
        NewCookie newCookie = null;

        if (username == null) {
            String token = UUID.randomUUID().toString();
            newCookie = new NewCookie("auth", token);
            user.setToken(token);
            EntityManager em = PersistenceManager.instance().createEntityManager();
            try {
                em.getTransaction().begin();
                //update the row with token defined
                em.merge(user);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            LOGGER.info("Generated cookie: " + newCookie.getValue());
        }
        return newCookie;
    }

    /**
     * create a new subscription and store it in the database
     * check if the user is authorised
     * @Param: subscriptionDTO
     * @Param: sub
     * @Param: token
     **/
    @POST
    @Path("/subscribe/concertInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createSubscription(ConcertInfoSubscriptionDTO subscriptionDTO, @Suspended AsyncResponse sub,
                                   @CookieParam("auth") String token) {
        //check if the client is authorised
        User user = null;
        if(token !=null) {
            EntityManager entityManager = PersistenceManager.instance().createEntityManager();
            try {
                TypedQuery<User> query = entityManager.createQuery
                        ("select user from User user where user.token=:token", User.class)
                        .setParameter("token", token);
                user = query.getSingleResult();
                //check the existence of concert
                Concert concert = entityManager.find(Concert.class,
                        new Long(subscriptionDTO.getConcertId()));
                if (concert == null) {
                    sub.resume(Response.status(Response.Status.BAD_REQUEST).build());
                }
                //check the existence of date
                if (!concert.getDates().contains(subscriptionDTO.getDate())) {
                    sub.resume(Response.status(Response.Status.BAD_REQUEST).build());
                }

                concertInfoSubscriptions.add(sub);
                ConcertInfoSubscription subscription = ConcertInfoSubscriptionMapper
                        .toDomainModel(subscriptionDTO);

                entityManager.getTransaction().begin();
                //add subscription to the database
                entityManager.persist(subscription);
                entityManager.getTransaction().commit();

            } catch (NoResultException n) {
                sub.resume(Response.status(Response.Status.UNAUTHORIZED).build());
            } finally {
                entityManager.close();
            }
        }else{
            sub.resume(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    /**
     * process concert subscription
     * check if the user is authorised
     * @Param: subscriptionDTO
     * @Param: sub
     * @Param: token
     **/
    private void processConcertSubscription(ConcertInfoSubscriptionDTO subscription,
                                            ConcertInfoNotificationDTO notificationDTO) {
        if (isNumberReached(subscription, notificationDTO)) {
            notifyConcertSeatNum(
                    new ConcertInfoNotificationDTO(notificationDTO.getNumSeatsRemaining()));
        }
    }


    /**
     * synchronise the concert notifications
     * @Param: notificationDTO
     **/
    private void notifyConcertSeatNum(ConcertInfoNotificationDTO notificationDTO) {
        synchronized (concertInfoSubscriptions) {
            for (AsyncResponse sub : concertInfoSubscriptions) {
                sub.resume(notificationDTO);
            }
            concertInfoSubscriptions.clear();
        }
    }

    /**
     * Compare the number of seats remaining
     * and the number of percentage that has been booked
     * @Param: subscriptionDTO
     * @Param: notificationDTO
     **/
    private boolean isNumberReached(ConcertInfoSubscriptionDTO subscriptionDTO,
                                    ConcertInfoNotificationDTO notificationDTO) {
        if (((float) subscriptionDTO.getPercentageBooked() / 100) * 120 >=
                notificationDTO.getNumSeatsRemaining()) {
            return true;
        } else {
            return false;
        }
    }

}
