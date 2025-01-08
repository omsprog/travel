package com.omsprog.dashboard_service.config;

import com.omsprog.dashboard_service.entity.jpa.*;
import com.omsprog.dashboard_service.repository.*;
import com.omsprog.dashboard_service.util.AeroLine;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Component
@Profile("testdata")
public class TestDataLoader {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final TourRepository tourRepository;

    public TestDataLoader(HotelRepository hotelRepository,
                          UserRepository userRepository,
                          FlightRepository flightRepository,
                          ReservationRepository reservationRepository,
                          TicketRepository ticketRepository,
                          TourRepository tourRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.tourRepository = tourRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData() {

        // App Users
        if(userRepository.count() == 0) {
            List<AppUserEntity> users = List.of(
                AppUserEntity.builder().email("testautomation@gmail.com").dni("TSAU967823OYONE740").fullName("Test Automation").password("$2a$10$X88KU02yETEg7Qzt2huoHuxeXmlgJbKrXkE6XbSQze5C6QUznvbUW")
                        .totalFlights(0).totalLodgings(0).totalTours(0).phoneNumber("5567390326").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("jeffch@travel.com").dni("CHJF982241TXFEV672").fullName("Chris Jeff").password("$2a$10$ZFSPZ5w6unTbZWjC5uk9he1s3Es958oc5VV/Vz45Jwmx/fbKos8bC")
                        .totalFlights(0).totalLodgings(0).totalTours(0).phoneNumber("7724124532").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("rileyt@travel.com").dni("RLMY932893CAFEV730").fullName("Riley Thomas").password("$2a$10$ZFSPZ5w6unTbZWjC5uk9he1s3Es958oc5VV/Vz45Jwmx/fbKos8bC")
                        .totalFlights(0).totalLodgings(0).totalTours(0).phoneNumber("4423565634").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("kmichel@gmail.com").dni("KEMI771012EUMRG004").fullName("Kenan Michel").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(2).totalLodgings(1).totalTours(0).phoneNumber("3437458433").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("gunterfl@gmail.com").dni("GUFL781012IRKGR426").fullName("Gunter Fl").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(0).totalLodgings(2).totalTours(0).phoneNumber("4355336626").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("waltk@gmail.com").dni("WAKT771012POLRG472").fullName("Walt Knut").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(1).totalLodgings(1).totalTours(0).phoneNumber("5556853173").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("rxkaren@gmail.com").dni("RKKA771012RQCRR118").fullName("Karen RK").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(4).totalLodgings(1).totalTours(0).phoneNumber("5527372778").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("kathyw@gmail.com").dni("WIKA771012IIYGR980").fullName("Kathy Williamson").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(0).totalLodgings(2).totalTours(0).phoneNumber("8932324414").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("kmasada@gmail.com").dni("MASK771012OPWGR426").fullName("Masada K").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(1).totalLodgings(1).totalTours(0).phoneNumber("5567879823").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("khalxu@gmail.com").dni("KHXH771012BJYGR663").fullName("Khal Xu").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(2).totalLodgings(1).totalTours(1).phoneNumber("6677879054").profilePicturePath("profile-pictures/default-profile.jpg").build(),
                AppUserEntity.builder().email("marcercr@gmail.com").dni("CRMA625312UTSGE531").fullName("Marcel Craig").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(2).totalLodgings(1).totalTours(1).phoneNumber("5544290981").profilePicturePath("profile-pictures/default-profile.jpg").build()
            );
            userRepository.saveAll(users);
        }

        // Hotels
        if(hotelRepository.count() == 0) {
            List<HotelEntity> hotels = List.of(
                HotelEntity.builder().name("Golden").address("Greece 21").rating(5).price(BigDecimal.valueOf(70.05)).build(),
                HotelEntity.builder().name("El mirador").address("Greece 98").rating(4).price(BigDecimal.valueOf(100.45)).build(),
                HotelEntity.builder().name("La Odisea").address("Greece 99").rating(5).price(BigDecimal.valueOf(220.00)).build(),
                HotelEntity.builder().name("Olimpo").address("Greece 29").rating(5).price(BigDecimal.valueOf(221.21)).build(),
                HotelEntity.builder().name("Atenea").address("Greece 43").rating(2).price(BigDecimal.valueOf(56.43)).build(),
                HotelEntity.builder().name("Ice and fire").address("Iceland 49").rating(3).price(BigDecimal.valueOf(23.43)).build(),
                HotelEntity.builder().name("Sky lights").address("Iceland 98").rating(5).price(BigDecimal.valueOf(130.45)).build(),
                HotelEntity.builder().name("Vikings").address("Iceland 01").rating(5).price(BigDecimal.valueOf(99.99)).build(),
                HotelEntity.builder().name("Dark ocean").address("Iceland 100").rating(2).price(BigDecimal.valueOf(199.99)).build(),
                HotelEntity.builder().name("Nordic").address("Iceland 300").rating(3).price(BigDecimal.valueOf(109.99)).build(),
                HotelEntity.builder().name("LUX").address("Mexico 53").rating(4).price(BigDecimal.valueOf(10.88)).build(),
                HotelEntity.builder().name("Los chiles").address("Mexico 9").rating(3).price(BigDecimal.valueOf(21.19)).build(),
                HotelEntity.builder().name("Reforma").address("Mexico 922").rating(1).price(BigDecimal.valueOf(33.33)).build(),
                HotelEntity.builder().name("Catrina").address("Mexico 88").rating(5).price(BigDecimal.valueOf(22.87)).build(),
                HotelEntity.builder().name("Tequila").address("Mexico 348").rating(5).price(BigDecimal.valueOf(32.42)).build(),
                HotelEntity.builder().name("The lake").address("Canada 22").rating(5).price(BigDecimal.valueOf(63.54)).build(),
                HotelEntity.builder().name("Cascade").address("Canada 89").rating(4).price(BigDecimal.valueOf(22.99)).build(),
                HotelEntity.builder().name("Pino").address("Canada 38").rating(4).price(BigDecimal.valueOf(99.42)).build()

            );
            hotelRepository.saveAll(hotels);
        }

        // Flights
        if(flightRepository.count() == 0) {
            List<FlightEntity> flights = List.of(
                FlightEntity.builder().originName("Mexico").destinationName("Greece").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(43.00))
                        .originLat(65.9999).originLng(-35.8888).destinationLat(22.2222).destinationLng(11.1111).build(),
                FlightEntity.builder().originName("Greece").destinationName("Mexico").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(33.33))
                        .originLat(11.1111).originLng(22.2222).destinationLat(-14.8888).destinationLng(10.9999).build(),
                FlightEntity.builder().originName("Mexico").destinationName("Iceland").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(48.70))
                        .originLat(13.9999).originLng(46.8888).destinationLat(77.7777).destinationLng(58.8888).build(),
                FlightEntity.builder().originName("Iceland").destinationName("Mexico").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(12.99))
                        .originLat(85.9999).originLng(75.8888).destinationLat(77.7777).destinationLng(-54.8888).build(),
                FlightEntity.builder().originName("Iceland").destinationName("Greece").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(85.98))
                        .originLat(57.9645).originLng(58.8888).destinationLat(76.7777).destinationLng(-10.9866).build(),
                FlightEntity.builder().originName("Greece").destinationName("Iceland").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(829.99))
                        .originLat(76.3492).originLng(24.4411).destinationLat(10.8899).destinationLng(-20.9866).build(),
                FlightEntity.builder().originName("Mexico").destinationName("Greece").aeroLine(AeroLine.blue_sky).price(BigDecimal.valueOf(12.99))
                        .originLat(80.6668).originLng(14.7669).destinationLat(-10.2222).destinationLng(-23.5533).build(),
                FlightEntity.builder().originName("Greece").destinationName("Mexico").aeroLine(AeroLine.blue_sky).price(BigDecimal.valueOf(19.99))
                        .originLat(-14.7866).originLng(19.7339).destinationLat(-9.4444).destinationLng(-23.5533).build(),
                FlightEntity.builder().originName("Canada").destinationName("Mexico").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(19.99))
                        .originLat(-7.8978).originLng(23.8788).destinationLat(23.4444).destinationLng(23.8899).build(),
                FlightEntity.builder().originName("Mexico").destinationName("Canada").aeroLine(AeroLine.aero_gold).price(BigDecimal.valueOf(15.65))
                        .originLat(20.9899).originLng(-23.8788).destinationLat(10.7655).destinationLng(10.3322).build(),
                FlightEntity.builder().originName("Mexico").destinationName("Iceland").aeroLine(AeroLine.blue_sky).price(BigDecimal.valueOf(42.99))
                        .originLat(20.1144).originLng(-15.2234).destinationLat(10.7655).destinationLng(12.3322).build(),
                FlightEntity.builder().originName("Iceland").destinationName("Mexico").aeroLine(AeroLine.blue_sky).price(BigDecimal.valueOf(21.54))
                        .originLat(65.8765).originLng(-20.8877).destinationLat(12.6644).destinationLng(-23.4422).build(),
                FlightEntity.builder().originName("Iceland").destinationName("Greece").aeroLine(AeroLine.blue_sky).price(BigDecimal.valueOf(12.00))
                        .originLat(40.8765).originLng(-23.8700).destinationLat(20.8899).destinationLng(-21.9082).build(),
                FlightEntity.builder().originName("Canada").destinationName("Mexico").aeroLine(AeroLine.blue_sky).price(BigDecimal.valueOf(16.99))
                        .originLat(20.9877).originLng(-20.2300).destinationLat(10.7854).destinationLng(-13.8092).build(),
                FlightEntity.builder().originName("Mexico").destinationName("Canada").aeroLine(AeroLine.blue_sky).price(BigDecimal.valueOf(14.65))
                        .originLat(20.9133).originLng(-89.9083).destinationLat(13.8790).destinationLng(-10.1096).build()
            );
            flightRepository.saveAll(flights);
        }

        // Reservations
        if(reservationRepository.count() == 0) {
            HotelEntity hotel1InDb = hotelRepository.findByName("Golden").get();
            HotelEntity hotel2InDb = hotelRepository.findByName("El mirador").get();
            HotelEntity hotel3InDb = hotelRepository.findByName("La Odisea").get();
            HotelEntity hotel4InDb = hotelRepository.findByName("Olimpo").get();
            HotelEntity hotel5InDb = hotelRepository.findByName("Atenea").get();

            AppUserEntity user1InDb = userRepository.findById("WIKA771012IIYGR980").get();
            AppUserEntity user2InDb = userRepository.findById("MASK771012OPWGR426").get();
            AppUserEntity user3InDb = userRepository.findById("GUFL781012IRKGR426").get();
            AppUserEntity user4InDb = userRepository.findById("WAKT771012POLRG472").get();
            AppUserEntity user5InDb = userRepository.findById("KEMI771012EUMRG004").get();

            List<ReservationEntity> reservations = List.of(
                ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345824")).dateTimeReservation(now()).hotel(hotel1InDb)
                        .customer(user1InDb).dateStart(LocalDate.parse("2024-08-01")).dateEnd(LocalDate.parse("2024-09-07")).totalDays(7).price(BigDecimal.valueOf(77.0)).build(),
                ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345825")).dateTimeReservation(now()).hotel(hotel2InDb)
                        .customer(user1InDb).dateStart(LocalDate.parse("2024-08-01")).dateEnd(LocalDate.parse("2024-09-07")).totalDays(7).price(BigDecimal.valueOf(225.0)).build(),
                ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345826")).dateTimeReservation(now()).hotel(hotel3InDb)
                        .customer(user2InDb).dateStart(LocalDate.parse("2024-08-01")).dateEnd(LocalDate.parse("2024-10-01")).totalDays(4).price(BigDecimal.valueOf(112.00)).build(),
                ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345827")).dateTimeReservation(now()).hotel(hotel3InDb)
                        .customer(user3InDb).dateStart(LocalDate.parse("2024-08-12")).dateEnd(LocalDate.parse("2024-11-14")).totalDays(2).price(BigDecimal.valueOf(74.00)).build(),
                ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345828")).dateTimeReservation(now()).hotel(hotel4InDb)
                        .customer(user3InDb).dateStart(LocalDate.parse("2024-08-01")).dateEnd(LocalDate.parse("2024-10-01")).totalDays(4).price(BigDecimal.valueOf(98.00)).build(),
                ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345829")).dateTimeReservation(now()).hotel(hotel5InDb)
                        .customer(user4InDb).dateStart(LocalDate.parse("2024-08-12")).dateEnd(LocalDate.parse("2024-10-01")).totalDays(2).price(BigDecimal.valueOf(74.00)).build(),
                ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345830")).dateTimeReservation(now()).hotel(hotel5InDb)
                        .customer(user5InDb).dateStart(LocalDate.parse("2024-08-01")).dateEnd(LocalDate.parse("2024-10-01")).totalDays(4).price(BigDecimal.valueOf(98.00)).build()
            );
            reservationRepository.saveAll(reservations);
        }

        // Tickets
        if(ticketRepository.count() == 0){
            FlightEntity flight1InDb = flightRepository.findById(1L).get();
            FlightEntity flight2InDb = flightRepository.findById(2L).get();
            FlightEntity flight4InDb = flightRepository.findById(4L).get();
            FlightEntity flight5InDb = flightRepository.findById(5L).get();
            FlightEntity flight7InDb = flightRepository.findById(7L).get();

            AppUserEntity user1InDb = userRepository.findById("WAKT771012POLRG472").get();
            AppUserEntity user2InDb = userRepository.findById("KEMI771012EUMRG004").get();
            AppUserEntity user3InDb = userRepository.findById("MASK771012OPWGR426").get();
            AppUserEntity user4InDb = userRepository.findById("RKKA771012RQCRR118").get();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            List<TicketEntity> tickets = List.of(
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345677")).price(BigDecimal.valueOf(330.05)).flight(flight1InDb).customer(user1InDb)
                            .departureDate(LocalDateTime.parse("2024-08-01 14:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-08-01 16:00:00", formatter)).purchaseDate(LocalDate.from(now())).build(),
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345678")).price(BigDecimal.valueOf(220.33)).flight(flight1InDb).customer(user2InDb)
                            .departureDate(LocalDateTime.parse("2024-08-01 14:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-08-01 16:00:00", formatter)).purchaseDate(LocalDate.from(now())).build(),
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345679")).price(BigDecimal.valueOf(320.00)).flight(flight4InDb).customer(user2InDb)
                            .departureDate(LocalDateTime.parse("2024-08-01 12:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-08-01 15:00:00", formatter)).purchaseDate(LocalDate.from(now())).build(),
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345680")).price(BigDecimal.valueOf(560.77)).flight(flight7InDb).customer(user3InDb)
                            .departureDate(LocalDateTime.parse("2024-08-12 20:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-01-14 22:00:00", formatter)).purchaseDate(LocalDate.from(now())).build(),
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345681")).price(BigDecimal.valueOf(135.50)).flight(flight2InDb).customer(user4InDb)
                            .departureDate(LocalDateTime.parse("2024-09-13 20:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-09-14 01:00:00", formatter)).purchaseDate(LocalDate.from(now())).build(),
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345682")).price(BigDecimal.valueOf(260.24)).flight(flight1InDb).customer(user4InDb)
                            .departureDate(LocalDateTime.parse("2024-09-14 20:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-09-14 23:00:00", formatter)).purchaseDate(LocalDate.from(now())).build(),
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345683")).price(BigDecimal.valueOf(560.79)).flight(flight5InDb).customer(user4InDb)
                            .departureDate(LocalDateTime.parse("2024-09-15 20:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-09-15 22:00:00", formatter)).purchaseDate(LocalDate.from(now())).build(),
                    TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345684")).price(BigDecimal.valueOf(560.95)).flight(flight7InDb).customer(user4InDb)
                            .departureDate(LocalDateTime.parse("2024-09-16 20:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-09-17 01:00:00", formatter)).purchaseDate(LocalDate.from(now())).build()

            );
            ticketRepository.saveAll(tickets);
        }

        // Tours
        if(tourRepository.count() == 0) {
            AppUserEntity user1InDb = userRepository.findById("KHXH771012BJYGR663").get();
            AppUserEntity user2InDb = userRepository.findById("CRMA625312UTSGE531").get();

            FlightEntity flight10InDb = flightRepository.findById(10L).get();
            FlightEntity flight11InDb = flightRepository.findById(11L).get();
            FlightEntity flight12InDb = flightRepository.findById(12L).get();
            FlightEntity flight13InDb = flightRepository.findById(13L).get();

            HotelEntity hotel7InDb = hotelRepository.findByName("Sky lights").get();
            HotelEntity hotel8InDb = hotelRepository.findByName("El mirador").get();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            TourEntity tourToBeCreated1 = TourEntity.builder().customer(user1InDb).name("Honey Moon").build();
            TourEntity tourToBeCreated2 = TourEntity.builder().customer(user2InDb).name("Magical Tour").build();


            TicketEntity ticket1 = TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345685")).price(BigDecimal.valueOf(320.24)).flight(flight10InDb).customer(user1InDb)
                    .departureDate(LocalDateTime.parse("2024-06-07 14:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-06-07 16:00:00", formatter)).purchaseDate(LocalDate.from(now()))
                    .tour(tourToBeCreated1).build();
            TicketEntity ticket2 = TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345686")).price(BigDecimal.valueOf(243.64)).flight(flight11InDb).customer(user1InDb)
                    .departureDate(LocalDateTime.parse("2024-06-08 10:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-06-08 13:00:00", formatter)).purchaseDate(LocalDate.from(now()))
                    .tour(tourToBeCreated1).build();
            ReservationEntity reservation1 = ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345831")).dateTimeReservation(now()).hotel(hotel7InDb)
                    .customer(user1InDb).dateStart(LocalDate.parse("2024-08-10")).dateEnd(LocalDate.parse("2024-08-19")).totalDays(9).price(BigDecimal.valueOf(77.0))
                    .tour(tourToBeCreated1).build();

            tourToBeCreated1.addTicket(ticket1);
            tourToBeCreated1.addTicket(ticket2);
            tourToBeCreated1.addReservation(reservation1);

            tourRepository.save(tourToBeCreated1);

            TicketEntity ticket3 = TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345687")).price(BigDecimal.valueOf(230.80)).flight(flight12InDb).customer(user2InDb)
                    .departureDate(LocalDateTime.parse("2024-07-11 16:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-07-11 18:00:00", formatter)).purchaseDate(LocalDate.from(now()))
                    .tour(tourToBeCreated2).build();
            TicketEntity ticket4 = TicketEntity.builder().id(UUID.fromString("12345678-1234-5678-2236-567812345688")).price(BigDecimal.valueOf(532.35)).flight(flight13InDb).customer(user2InDb)
                    .departureDate(LocalDateTime.parse("2024-07-14 11:00:00", formatter)).arrivalDate(LocalDateTime.parse("2024-07-14 12:30:00", formatter)).purchaseDate(LocalDate.from(now()))
                    .tour(tourToBeCreated2).build();
            ReservationEntity reservation2 = ReservationEntity.builder().id(UUID.fromString("12345678-1234-5678-1234-567812345832")).dateTimeReservation(now()).hotel(hotel8InDb)
                    .customer(user2InDb).dateStart(LocalDate.parse("2024-07-15")).dateEnd(LocalDate.parse("2024-07-26")).totalDays(11).price(BigDecimal.valueOf(345.0))
                    .tour(tourToBeCreated2).build();

            tourToBeCreated2.addTicket(ticket3);
            tourToBeCreated2.addTicket(ticket4);
            tourToBeCreated2.addReservation(reservation2);

            tourRepository.save(tourToBeCreated2);
        }

    }
}
