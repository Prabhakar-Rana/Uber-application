package UberApp.UberApp.services;

import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.User;
import UberApp.UberApp.entities.Wallet;
import UberApp.UberApp.entities.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, double money, String transactionId,
                            TransactionMethod transactionMethod, Ride ride);
    Wallet deductMoneyFromWallet(User user, double money, String transactionId,
                                 TransactionMethod transactionMethod, Ride ride);
    void withdrawAllMyMoneyFromWallet();
    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);
    Wallet findByUser(User user);
}
