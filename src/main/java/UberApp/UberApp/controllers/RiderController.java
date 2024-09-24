package UberApp.UberApp.controllers;

import UberApp.UberApp.dto.*;
import UberApp.UberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
@Secured("ROLE_RIDER")
public class RiderController {
    private final RiderService riderService;
    @PostMapping("/requestRide")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
        System.out.println("I am in request-Ride !");
        System.out.println("rideResponse="+riderService.requestRide(rideRequestDto));
        return ResponseEntity.ok(riderService.requestRide(rideRequestDto));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@RequestBody Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("/rateRider")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(riderService.rateDriver(ratingDto.getRideId(),
                ratingDto.getRating()));
    }
    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDto> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getAllMyRides")
    public ResponseEntity<Page<RideDto>>getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffet,
                                                      @RequestParam(defaultValue = "10",
                                                              required = false) Integer pagSize){
        Pageable pageable = PageRequest.of(pageOffet, pagSize);
        return ResponseEntity.ok(riderService.getAllMyRides(pageable));
    }
}
