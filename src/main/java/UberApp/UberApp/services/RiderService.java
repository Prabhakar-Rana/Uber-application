package UberApp.UberApp.services;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.RideDto;
import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.dto.RiderDto;
import UberApp.UberApp.entities.Rider;
import UberApp.UberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RiderService {
    RideRequestDto requestRide(RideRequestDto rideRequestDto);
    RideDto cancelRide(Long rideId);
    DriverDto rateDriver(Long rideId, Long rating);
    RiderDto getMyProfile();
    Page<RideDto> getAllMyRides(Pageable pageable);
    Rider getCurrentRider();

    Rider createNewRider(User user);
}
