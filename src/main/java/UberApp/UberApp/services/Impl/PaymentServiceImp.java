package UberApp.UberApp.services.Impl;

import UberApp.UberApp.entities.Payment;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.enums.PaymentStatus;
import UberApp.UberApp.repositories.PaymentRepository;
import UberApp.UberApp.services.PaymentService;
import UberApp.UberApp.strategies.PaymentStrategyManger;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {
    private final PaymentStrategyManger paymentStrategyManger;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride).orElseThrow(() ->
                new ResourceNotFoundException("payment is not found in this ride id="+ ride.getId()));
        paymentStrategyManger.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .paymentMethod(ride.getPaymentMethod())
                .ride(ride)
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }
}
