package UberApp.UberApp.services.Impl;

import UberApp.UberApp.dto.WalletTransactionDto;
import UberApp.UberApp.entities.WalletTransaction;
import UberApp.UberApp.repositories.WalletTransactionRepository;
import UberApp.UberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImp implements WalletTransactionService {
    private final ModelMapper modelMapper;
    private final WalletTransactionRepository walletTransactionRepository;
    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
//        WalletTransaction walletTransaction =
//                modelMapper.map(walletTransactionDto, WalletTransaction.class);
        walletTransactionRepository.save(walletTransaction);
    }
}
