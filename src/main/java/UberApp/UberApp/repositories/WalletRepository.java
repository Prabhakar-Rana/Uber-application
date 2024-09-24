package UberApp.UberApp.repositories;

import UberApp.UberApp.entities.User;
import UberApp.UberApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUser(User user);
}
