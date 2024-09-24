package UberApp.UberApp.services;

import UberApp.UberApp.entities.Payment;
import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.enums.PaymentMethod;
import UberApp.UberApp.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);
    Payment createNewPayment(Ride ride);
    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
