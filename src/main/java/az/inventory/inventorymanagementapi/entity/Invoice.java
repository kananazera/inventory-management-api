package az.inventory.inventorymanagementapi.entity;

import az.inventory.inventorymanagementapi.enums.InvoiceType;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Column(nullable = false)
    private LocalDateTime invoiceDate;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @OneToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @Column(nullable = false)
    private PaymentStatus status;
}
