package UberApp.UberApp.repositories;

import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.RideRequest;
import UberApp.UberApp.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickUpLocation) as distance "+
    "FROM Driver as d "+
    "WHERE available = true AND ST_DWithin(d.current_location, :pickUpLocation, 10000) "+
    "ORDER BY DISTANCE "+
    "LIMIT 10", nativeQuery = true)
    List<Driver> findTenDriverByDistance(Point pickUpLocation);

    @Query(value = "SELECT d.* "+
    "FROM Driver as d "+
    "WHERE available = true AND ST_DWithin(d.current_location, :pickUpLocation, 15000) "+
    "ORDER BY d.rating DESC "+
    "LIMIT 10", nativeQuery = true)
    List<Driver> findTenDriverByRating(Point pickUpLocation);

    Optional<Driver> findByUser(User user);
}
