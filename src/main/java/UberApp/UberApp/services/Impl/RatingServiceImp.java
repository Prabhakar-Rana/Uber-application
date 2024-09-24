package UberApp.UberApp.services.Impl;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.RiderDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Rating;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.Rider;
import UberApp.UberApp.repositories.RatingRepository;
import UberApp.UberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImp implements RatingService {
    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;
    @Override
    public RiderDto rateRider(Ride ride, Long rating) {
        Rider rider = ride.getRider();
        Rating rating1 = (Rating) ratingRepository.findByRide(ride);
        rating1.setRiderRating(rating);
        ratingRepository.save(rating1);
        Double ratting2 = ratingRepository.findByRider(rider).stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);
        rider.setRating(ratting2);
        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public DriverDto rateDriver(Ride ride, Long rating) {
        Driver driver = ride.getDriver();
        Rating rating1 = (Rating) ratingRepository.findByRide(ride);
        rating1.setDriverRating(rating);
        ratingRepository.save(rating1);
        Double ratting2 = ratingRepository.findByDriver(driver).stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);
        driver.setRating(ratting2);
        return modelMapper.map(driver, DriverDto.class);
    }

    @Override
    public void createRating(Rider rider, Driver driver, Ride ride) {
        Rating rating = Rating.builder()
                .riderRating(0L)
                .driverRating(0L)
                .ride(ride)
                .driver(driver)
                .rider(rider)
                .build();
        ratingRepository.save(rating);
    }
}
