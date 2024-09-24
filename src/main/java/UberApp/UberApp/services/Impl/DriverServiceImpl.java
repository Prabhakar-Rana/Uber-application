package UberApp.UberApp.services.Impl;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.RideDto;
import UberApp.UberApp.dto.RiderDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.RideRequest;
import UberApp.UberApp.entities.User;
import UberApp.UberApp.entities.enums.RideRequestStatus;
import UberApp.UberApp.entities.enums.RideStatus;
import UberApp.UberApp.repositories.DriverRepository;
import UberApp.UberApp.repositories.RideRepository;
import UberApp.UberApp.repositories.RideRequestRepository;
import UberApp.UberApp.repositories.RiderRepository;
import UberApp.UberApp.services.DriverService;
import UberApp.UberApp.services.PaymentService;
import UberApp.UberApp.services.RatingService;
import UberApp.UberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@ToString
public class DriverServiceImpl implements DriverService {
   // ModelMapper modelMapper = new ModelMapper();
    private final RiderRepository riderRepository;
    private final RideRequestRepository rideRequestRepository;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RideRepository rideRepository;
    private final RatingService ratingService;
    @Override
    public RideDto acceptRide(Long rideId) {
        RideRequest rideRequest = rideRequestRepository.findById((rideId)).orElse(null);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("driver is not available !");
        }
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("rideRequest is not in pending !");
        }
        currentDriver.setAvailable(false);
        Ride ride = rideService.createNewRide(rideRequest, currentDriver);
        System.out.println("ride in acceptRide="+ride);
        RideDto rideDto = modelMapper.map(ride, RideDto.class);
        System.out.println("ridedto="+rideDto);
        return rideDto;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!ride.getDriver().equals(currentDriver)){
            throw new  RuntimeException("Driver is not eligible to cancel the ride !");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new  RuntimeException("Ride cannot be cancel because it is not started yet !");
        }
        currentDriver.setAvailable(true);
        driverRepository.save(currentDriver);
        ride.setRideStatus(RideStatus.CANCELLED);
        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId, String rideOtp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("drivers are not matching !");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("ride status is not Confirmed !");
        }
        if(!ride.getOtp().equals(rideOtp)){
            throw new RuntimeException("Otp is not matching"+ride.getOtp()+"  "+rideOtp);
        }
        driver.setAvailable(false);
        driverRepository.save(driver);
        Ride updatedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createNewPayment(updatedRide);
        ratingService.createRating(rideRepository.getReferenceById(rideId).getRider(), driver, updatedRide);

        return modelMapper.map(updatedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!ride.getDriver().equals(currentDriver)){
            throw new  RuntimeException("Driver is not eligible to End the ride !");
        }
        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new  RuntimeException("Ride cannot be cancel because it is not in running !");
        }
        currentDriver.setAvailable(true);
        driverRepository.save(currentDriver);
        ride.setRideStatus(RideStatus.ENDED);
        ride.setEndedAt(LocalDateTime.now());
        paymentService.processPayment(ride);
        Ride updatedRide = rideRepository.save(ride);
        return modelMapper.map(updatedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Long rating) {
        Ride ride = rideRepository.findById(rideId).
                orElseThrow(()-> new ResourceNotFoundException("Ride is not available at id :"+ rideId));

        Driver currentDriver = getCurrentDriver();
        if(!ride.getDriver().equals(currentDriver)){
            throw new  RuntimeException("Driver is not eligible to End the ride !");
        }
        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new  RuntimeException("Ride cannot be cancel because it is not in running !");
        }
        return ratingService.rateRider(ride, rating);
    }
    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map(ride->
                modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user).orElseThrow(() ->
                new ResourceNotFoundException("Driver not found in this user id = " + user.getId()));
    }
    public void updateDriverAailablity(Driver driver, boolean isAvailable){
        driver.setAvailable(isAvailable);
        driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
