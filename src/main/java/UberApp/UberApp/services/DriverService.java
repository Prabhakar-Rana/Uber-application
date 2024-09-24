package UberApp.UberApp.services;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.RideDto;
import UberApp.UberApp.dto.RiderDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {
    RideDto acceptRide(Long rideId);
    RideDto cancelRide(Long rideId);
    RideDto startRide(Long rideId, String rideStartDto);
    RideDto endRide(Long rideId);
    RiderDto rateRider(Long rideId, Long rating);
    DriverDto getMyProfile();
    Page<RideDto> getAllMyRides(PageRequest pageRequest);
    Driver getCurrentDriver();
    void updateDriverAailablity(Driver driver, boolean isAvailable);
    Driver createNewDriver(Driver driver);
}
