package UberApp.UberApp.strategies;

import UberApp.UberApp.entities.enums.PaymentMethod;
import UberApp.UberApp.strategies.Impl.CashPaymentStrategy;
import UberApp.UberApp.strategies.Impl.WalletPaymentStrategy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class PaymentStrategyManger {
    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;
    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        if(paymentMethod.equals(PaymentMethod.CASH)){
            return cashPaymentStrategy;
        } else{
            return walletPaymentStrategy;
        }
    }
}
