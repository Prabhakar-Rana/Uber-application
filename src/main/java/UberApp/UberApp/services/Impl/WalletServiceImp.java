package UberApp.UberApp.services.Impl;

import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.User;
import UberApp.UberApp.entities.Wallet;
import UberApp.UberApp.entities.WalletTransaction;
import UberApp.UberApp.entities.enums.TransactionMethod;
import UberApp.UberApp.entities.enums.TransactionType;
import UberApp.UberApp.repositories.WalletRepository;
import UberApp.UberApp.services.WalletService;
import UberApp.UberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, double money, String transactionId,
                                   TransactionMethod transactionMethod, Ride ride) {
        Wallet wallet = walletRepository.findByUser(user);
        wallet.setBalance(wallet.getBalance() + money);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .amount(money)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .ride(ride)
                .wallet(wallet)
                .build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, double money, String transactionId,
                                        TransactionMethod transactionMethod, Ride ride) {
        Wallet wallet = walletRepository.findByUser(user);
        wallet.setBalance(wallet.getBalance() - money);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .amount(money)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .ride(ride)
                .wallet(wallet)
                .build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(()->
                new ResourceNotFoundException("Wallet is not available in this id"));

    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user);
    }
}
