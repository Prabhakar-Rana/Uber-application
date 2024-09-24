package UberApp.UberApp.services;

import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.RideRequest;
import UberApp.UberApp.entities.Rider;
import UberApp.UberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface RideService {
   Ride getRideById(Long rideId);
   void matchWithDriver(RideRequestDto rideRequestDto);
   Ride createNewRide(RideRequest rideRequest, Driver driver);
   Ride updateRideStatus(Ride ride, RideStatus rideStatus);
   Page<Ride> getAllRidesOfRider(Rider rider, Pageable pageable);
   Page<Ride> getAllRidesOfDriver(Driver driver, Pageable pageable);
}
