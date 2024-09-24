package UberApp.UberApp.repositories;

import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Rating;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.SequencedCollection;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByRide(Ride ride);

    List<Rating> findByDriver(Driver driver);

    List<Rating> findByRider(Rider rider);
}
