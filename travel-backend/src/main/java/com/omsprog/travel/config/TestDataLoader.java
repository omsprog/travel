package com.omsprog.travel.config;

import com.omsprog.travel.entity.jpa.AppUserEntity;
import com.omsprog.travel.entity.jpa.HotelEntity;
import com.omsprog.travel.repository.HotelRepository;
import com.omsprog.travel.repository.UserRepository;
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

    public TestDataLoader(HotelRepository hotelRepository, UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
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
                        .totalFlights(1).totalLodgings(1).totalTours(0).build(),
                AppUserEntity.builder().email("khalxu@gmail.com").dni("KHXH771012BJYGR663").fullName("Khal Xu").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(2).totalLodgings(1).totalTours(1).build(),
                AppUserEntity.builder().email("marcercr@gmail.com").dni("CRMA625312UTSGE531").fullName("Marcel Craig").password("$2a$10$/R0h29fK1dAlB6Mmlq1A.e7If9Y5zKeTkNxqoO4ovZA2nRP/rcKZG")
                        .totalFlights(2).totalLodgings(1).totalTours(1).build()
            );
            userRepository.saveAll(users);
        }

        // Hotels
        if(hotelRepository.count() == 0) {
            List<HotelEntity> hotels = List.of(
                    HotelEntity.builder().name("Golden").address("Greece 21").rating(5).price(BigDecimal.valueOf(70.05)).build(),
                    HotelEntity.builder().name("El mirador").address("Greece 98").rating(4).price(BigDecimal.valueOf(100.45)).build(),
                    HotelEntity.builder().name("La Odisea").address("Greece 99").rating(5).price(BigDecimal.valueOf(220.00)).build()
            );
            hotelRepository.saveAll(hotels);
        }

    }
}
