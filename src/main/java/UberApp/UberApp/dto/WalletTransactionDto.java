package UberApp.UberApp.dto;

import UberApp.UberApp.entities.Ride;
import UberApp.UberApp.entities.enums.TransactionMethod;
import UberApp.UberApp.entities.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletTransactionDto {
    private Long id;
    private Double amount;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;
    private RideDto ride;
    private String transactionId;
    private WalletDto wallet;
    private LocalDateTime timeStamp;
}
