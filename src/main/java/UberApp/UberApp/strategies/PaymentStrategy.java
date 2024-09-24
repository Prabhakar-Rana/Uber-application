package UberApp.UberApp.strategies;

import UberApp.UberApp.entities.Payment;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.repositories.RideRepository;
import lombok.RequiredArgsConstructor;

public interface PaymentStrategy {
    Double PLATEFORM_COMMISION = 0.3;
    public void processPayment(Payment payment);
}
