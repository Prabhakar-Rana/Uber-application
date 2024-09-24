package UberApp.UberApp.services;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.RiderDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.Rider;

public interface RatingService {
    RiderDto rateRider(Ride ride, Long rating);
    DriverDto rateDriver(Ride ride, Long rating);
    void createRating(Rider rider, Driver driver, Ride ride);
}
