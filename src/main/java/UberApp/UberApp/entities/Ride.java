package UberApp.UberApp.entities;

import UberApp.UberApp.entities.enums.PaymentMethod;
import UberApp.UberApp.entities.enums.RideRequestStatus;
import UberApp.UberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(indexes = {
        @Index(name = "idx_rider", columnList = "rider_id"),
        @Index(name = "idx_driver", columnList = "driver_id")
})
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickUpLocation;
    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private String otp;
    private double fare;
    @CreationTimestamp
    private LocalDateTime startedAt;
    @CreationTimestamp
    private LocalDateTime endedAt;
}
