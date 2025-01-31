package com.omsprog.dashboard_service.entity.jpa;

import com.omsprog.dashboard_service.util.AeroLine;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "flight")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinationLat;
    private Double destinationLng;
    @Column(length = 20)
    private String originName;
    @Column(length = 20)
    private String destinationName;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

    // RELATIONSHIPS
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "flight"  // Is mapped by the "flight" property of TICKET entity
    )
    private Set<TicketEntity> tickets;
}