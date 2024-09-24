package UberApp.UberApp.controllers;
import UberApp.UberApp.dto.*;
import UberApp.UberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@Secured("ROLE_Driver")
public class DriverController {
    private final DriverService driverService;
    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable long rideRequestId){
        System.out.println("I am in post value !");
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable long rideRequestId, @RequestBody RideStartDto rideStartDto){
        return ResponseEntity.ok(driverService.startRide(rideRequestId, rideStartDto.getOtp()));
    }

    @PostMapping("/endRide/{rideRequestId}")
    public ResponseEntity<RideDto> endRide(@PathVariable long rideRequestId){
        System.out.println("I am in post value !");
        return ResponseEntity.ok(driverService.endRide(rideRequestId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@RequestBody Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateRider")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(driverService.rateRider(ratingDto.getRideId(),
                ratingDto.getRating()));
    }
    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getAllMyRides")
    public ResponseEntity<Page<RideDto>>getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffet,
                                                      @RequestParam(defaultValue = "10",
                                                              required = false) Integer pagSize){
        Pageable pageable = PageRequest.of(pageOffet, pagSize);
        return ResponseEntity.ok(driverService.getAllMyRides((PageRequest) pageable));
    }


}
