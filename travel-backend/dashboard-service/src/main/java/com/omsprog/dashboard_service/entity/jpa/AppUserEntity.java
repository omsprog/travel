package com.omsprog.dashboard_service.entity.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AppUserEntity {

    @Id
    private String dni;
    @Column(length = 50)
    private String fullName;
    @Column(length = 12)
    private String phoneNumber;
    private Integer totalFlights;
    private Integer totalLodgings;
    private Integer totalTours;
    private String email;
    private String password;
    private String profilePicturePath;

    // RELATIONSHIPS
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private Set<TicketEntity> tickets;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private Set<ReservationEntity> reservations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private Set<TourEntity> tours;
}