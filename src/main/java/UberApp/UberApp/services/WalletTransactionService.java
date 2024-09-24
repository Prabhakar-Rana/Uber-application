package UberApp.UberApp.services;

import UberApp.UberApp.dto.WalletTransactionDto;
import UberApp.UberApp.entities.WalletTransaction;

public interface WalletTransactionService {
     void createNewWalletTransaction(WalletTransaction walletTransaction);
}
