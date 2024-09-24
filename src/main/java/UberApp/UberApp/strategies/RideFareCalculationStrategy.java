package UberApp.UberApp.strategies;

import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {
    double RIDE_FARE_MULTIPLIER = 10;
    Double calculateFare(RideRequest rideRequest);
}
