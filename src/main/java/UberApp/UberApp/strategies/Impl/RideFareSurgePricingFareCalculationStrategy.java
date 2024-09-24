package UberApp.UberApp.strategies.Impl;

import UberApp.UberApp.dto.RideRequestDto;
import UberApp.UberApp.entities.RideRequest;
import UberApp.UberApp.services.Impl.DistanceServiceOSRMImpl;
import UberApp.UberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    private final DistanceServiceOSRMImpl distanceServiceOSRM;

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        double distance = distanceServiceOSRM.calculateDistance(rideRequest.getPickUpLocation(), rideRequest.getDropOffLocation());

        double SURGE_PRICING = 5;
        return distance* SURGE_PRICING;
    }
}
