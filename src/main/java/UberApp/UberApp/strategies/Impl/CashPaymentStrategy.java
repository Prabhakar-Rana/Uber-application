package UberApp.UberApp.strategies.Impl;

import UberApp.UberApp.entities.Payment;
import UberApp.UberApp.entities.Wallet;
import UberApp.UberApp.entities.enums.PaymentStatus;
import UberApp.UberApp.entities.enums.TransactionMethod;
import UberApp.UberApp.repositories.PaymentRepository;
import UberApp.UberApp.repositories.WalletRepository;
import UberApp.UberApp.services.PaymentService;
import UberApp.UberApp.services.WalletService;
import UberApp.UberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    //private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
       // Wallet driverWallet = walletRepository.findByUser(payment.getRide().getDriver().getUser());
        Wallet wallet = walletService.deductMoneyFromWallet(
                payment.getRide().getDriver().getUser(), payment.getAmount()*(PLATEFORM_COMMISION),
                null, TransactionMethod.RIDE, payment.getRide());
       // paymentService.updatePaymentStatus(payment, PaymentStatus.CONFIRMED);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
