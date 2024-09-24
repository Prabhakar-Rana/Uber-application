package UberApp.UberApp.entities;

import UberApp.UberApp.entities.enums.PaymentMethod;
import UberApp.UberApp.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.swing.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToOne(fetch = FetchType.LAZY)
    public Ride ride;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @CreationTimestamp
    private LocalDateTime paymentTime;
}
