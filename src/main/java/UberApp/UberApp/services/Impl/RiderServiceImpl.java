package UberApp.UberApp.services.Impl;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.RideDto;
import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.dto.RiderDto;
import UberApp.UberApp.entities.*;
import UberApp.UberApp.entities.enums.RideRequestStatus;
import UberApp.UberApp.entities.enums.RideStatus;
import UberApp.UberApp.repositories.RideRepository;
import UberApp.UberApp.repositories.RideRequestRepository;
import UberApp.UberApp.repositories.RiderRepository;
import UberApp.UberApp.services.DriverService;
import UberApp.UberApp.services.RatingService;
import UberApp.UberApp.services.RideService;
import UberApp.UberApp.services.RiderService;
import UberApp.UberApp.strategies.StrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final ModelMapper modelMapper;
//    private final RideFareCalculationStrategy rideFareCalculationStrategy;
//    private final DriverMatchingStrategy driverMatchingStrategy;
    private final StrategyManager strategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RideRepository rideRepository;
    private final RatingService ratingService;
    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = strategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        System.out.println("fare=="+fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
        List<Driver>findMatchingDriver  = strategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
        System.out.println("result-1="+savedRideRequest);
        System.out.println("result="+modelMapper.map(savedRideRequest, RideRequestDto.class));

        // TODO send notification to all driver about this Ride-request
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);
        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not eligible to cancel the ride !");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Rider is not eligible to cancel the ride because it is not confirmed !");
        }
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAailablity(ride.getDriver(), true);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Long rating) {
        Ride ride = rideRepository.findById(rideId).
                orElseThrow(()-> new ResourceNotFoundException("Ride is not available at id :"+ rideId));

        Rider currentRider = getCurrentRider();
        if(!ride.getDriver().equals(currentRider)){
            throw new  RuntimeException("Driver is not eligible to End the ride !");
        }
        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new  RuntimeException("Ride cannot be cancel because it is not in running !");
        }
        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(Pageable pageable) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageable).map(ride->
                modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        //TODO implement using spring security
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(() ->
                new ResourceNotFoundException("Rider not found in this user id = " + user.getId()));
    }

}
