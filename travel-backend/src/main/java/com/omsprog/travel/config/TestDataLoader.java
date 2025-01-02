package com.omsprog.travel.config;

import com.omsprog.travel.entity.jpa.AppUserEntity;
import com.omsprog.travel.entity.jpa.FlightEntity;
import com.omsprog.travel.entity.jpa.HotelEntity;
import com.omsprog.travel.repository.FlightRepository;
import com.omsprog.travel.repository.HotelRepository;
import com.omsprog.travel.repository.UserRepository;
import com.omsprog.travel.util.AeroLine;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("testdata")
public class TestDataLoader {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    public TestDataLoader(HotelRepository hotelRepository, UserRepository userRepository, FlightRepository flightRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
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

    }
}
