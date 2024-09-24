package UberApp.UberApp.dto;

import UberApp.UberApp.entities.User;
import UberApp.UberApp.entities.WalletTransaction;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Data
public class WalletDto {
    private Long id;
    private UserDto user;
    private double balance;
    private List<WalletTransaction> transactions;
}
