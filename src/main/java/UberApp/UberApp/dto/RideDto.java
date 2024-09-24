package UberApp.UberApp.dto;

import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Rider;
import UberApp.UberApp.entities.enums.PaymentMethod;
import UberApp.UberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@ToString
public class RideDto {
    private Long id;
    private PointDto pickUpLocation;
    private PointDto dropOffLocation;
    private LocalDateTime createdTime;
    private RiderDto rider;
    private DriverDto driver;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;
    private String otp;
    private double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
