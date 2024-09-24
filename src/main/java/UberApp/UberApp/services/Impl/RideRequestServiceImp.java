package UberApp.UberApp.services.Impl;

import UberApp.UberApp.entities.RideRequest;
import UberApp.UberApp.repositories.RideRepository;
import UberApp.UberApp.repositories.RideRequestRepository;
import UberApp.UberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImp implements RideRequestService {
    private final RideRequestRepository rideRequestRepository;
    @Override
    public void update(RideRequest rideRequest){
        rideRequestRepository.save(rideRequest);
    }
}
