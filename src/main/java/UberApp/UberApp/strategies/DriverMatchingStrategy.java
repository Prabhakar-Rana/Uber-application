package UberApp.UberApp.strategies;

import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
    List<Driver>findMatchingDriver(RideRequest rideRequest);
}
