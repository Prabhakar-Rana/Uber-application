package UberApp.UberApp.services.Impl;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.RideDto;
import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.dto.RiderDto;
import UberApp.UberApp.entities.*;
import UberApp.UberApp.entities.enums.RideRequestStatus;
import UberApp.UberApp.entities.enums.RideStatus;
import UberApp.UberApp.repositories.RideRepository;
import UberApp.UberApp.services.RideService;
import UberApp.UberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ToString
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    ModelMapper modelMapper = new ModelMapper();
    private final RideRepository rideRepository;
    private final RideRequestServiceImp rideRequestServiceImp;
    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId).orElse(null);
    }

    @Override
    public void matchWithDriver(RideRequestDto rideRequestDto) {

    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = modelMapper.map(rideRequest, Ride.class);
        System.out.println("ride-value="+ride);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setOtp(getRandomOtp());
        ride.setDriver(driver);
        rideRequestServiceImp.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, Pageable pageable) {
        return rideRepository.findByRider(rider, pageable);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, Pageable pageable) {
        return rideRepository.findByDriver(driver, pageable);
    }

    public String getRandomOtp(){
        int randomNumber = (int)(Math.random() * 9000) + 1000;
        return String.valueOf(randomNumber);
    }
}
