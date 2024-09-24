package UberApp.UberApp.strategies.Impl;

import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.Payment;
import UberApp.UberApp.entities.Rider;
import UberApp.UberApp.entities.Wallet;
import UberApp.UberApp.entities.enums.PaymentStatus;
import UberApp.UberApp.entities.enums.TransactionMethod;
import UberApp.UberApp.repositories.PaymentRepository;
import UberApp.UberApp.services.PaymentService;
import UberApp.UberApp.services.WalletService;
import UberApp.UberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
   // private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
//        Wallet riderWallet = walletService.findByUser(payment.getRide().getRider().getUser());
//        Wallet driverWallet = walletService.findByUser(payment.getRide().getDriver().getUser());
        Rider rider = payment.getRide().getRider();
        Driver driver = payment.getRide().getDriver();
        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null,
                TransactionMethod.RIDE, payment.getRide());

        walletService.addMoneyToWallet(driver.getUser(),
                payment.getAmount()*(1-PLATEFORM_COMMISION), null,
                TransactionMethod.RIDE, payment.getRide());
        //paymentService.updatePaymentStatus(payment, PaymentStatus.CONFIRMED);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
