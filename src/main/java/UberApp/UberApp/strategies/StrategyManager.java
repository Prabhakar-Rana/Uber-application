package UberApp.UberApp.strategies;

import UberApp.UberApp.strategies.Impl.DriverMatchingHighestRatedDriverStrategy;
import UberApp.UberApp.strategies.Impl.DriverMatchingNearestDriverStrategy;
import UberApp.UberApp.strategies.Impl.RideFareSurgePricingFareCalculationStrategy;
import UberApp.UberApp.strategies.Impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StrategyManager {
    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RiderFareDefaultFareCalculationStrategy riderFareDefaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double rating){
        if(rating >= 4.8){
            return driverMatchingHighestRatedDriverStrategy;
        } else{
            return driverMatchingNearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        LocalTime SurgeStartTime = LocalTime.of(18,00);
        LocalTime SurgeEndTime = LocalTime.of(22,00);
        LocalTime CurrentTime = LocalTime.now();
        if(CurrentTime.isAfter(SurgeStartTime) && CurrentTime.isAfter(SurgeEndTime)){
            return rideFareSurgePricingFareCalculationStrategy;
        } else{
            return riderFareDefaultFareCalculationStrategy;
        }
    }
}
