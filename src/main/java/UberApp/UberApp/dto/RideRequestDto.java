package UberApp.UberApp.dto;

import UberApp.UberApp.entities.Rider;
import UberApp.UberApp.entities.enums.PaymentMethod;
import UberApp.UberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
public class RideRequestDto {
    private Long id;
    private PointDto pickUpLocation;
    private PointDto dropOffLocation;
    private PaymentMethod paymentMethod;
    private LocalDateTime requestedTime;
    private Rider rider;
    private double fare;
    private RideRequestStatus rideRequestStatus;
}
