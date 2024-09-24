package UberApp.UberApp.strategies.Impl;

import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.RideRequest;
import UberApp.UberApp.repositories.DriverRepository;
import UberApp.UberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {
    private final DriverRepository driverRepository;
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenDriverByRating(rideRequest.getPickUpLocation());
    }
}
