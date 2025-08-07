package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.entity.*;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import az.inventory.inventorymanagementapi.enums.PaymentType;
import az.inventory.inventorymanagementapi.repository.PaymentRepository;
import az.inventory.inventorymanagementapi.service.InvoiceService;
import az.inventory.inventorymanagementapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceService invoiceService;

    @Override
    public void recordPayment(Purchase purchase) {
        BigDecimal paidAmount = purchase.getPaidAmount();
        BigDecimal totalAmount = purchase.getTotalAmount();

        if (paidAmount == null || paidAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Paid amount must be positive");
        }

        if (paidAmount.compareTo(totalAmount) > 0) {
            throw new IllegalArgumentException("Paid amount cannot be greater than total amount");
        }

        Invoice invoice = invoiceService.generateInvoiceForPurchase(purchase);

        PaymentStatus status = determineStatus(paidAmount, totalAmount);

        Payment payment = Payment.builder()
                .purchase(purchase)
                .invoice(invoice)
                .amount(paidAmount)
                .paymentType(PaymentType.CASH)
                .paymentStatus(status)
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }

    private PaymentStatus determineStatus(BigDecimal paidAmount, BigDecimal totalAmount) {
        if (paidAmount.compareTo(BigDecimal.ZERO) == 0) {
            return PaymentStatus.UNPAID;
        } else if (paidAmount.compareTo(totalAmount) < 0) {
            return PaymentStatus.PARTIAL;
        } else {
            return PaymentStatus.PAID;
        }
    }
}