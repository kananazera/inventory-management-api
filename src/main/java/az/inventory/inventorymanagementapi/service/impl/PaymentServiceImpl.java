package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.entity.Payment;
import az.inventory.inventorymanagementapi.entity.Purchase;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import az.inventory.inventorymanagementapi.repository.PaymentRepository;
import az.inventory.inventorymanagementapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public void savePayment(Purchase purchase) {
        BigDecimal paidAmount = purchase.getPaidAmount();
        if (paidAmount == null || paidAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Paid amount must be positive or zero");
        }

        BigDecimal totalAmount = purchase.getTotalAmount();
        if (totalAmount == null) {
            throw new IllegalArgumentException("Total amount must not be null");
        }

        if (paidAmount.compareTo(totalAmount) > 0) {
            throw new IllegalArgumentException("Paid amount cannot be greater than total amount");
        }

        PaymentStatus status;
        if (paidAmount.compareTo(BigDecimal.ZERO) == 0) {
            status = PaymentStatus.UNPAID;
        } else if (paidAmount.compareTo(totalAmount) < 0) {
            status = PaymentStatus.PARTIAL;
        } else {
            status = PaymentStatus.PAID;
        }

        if (status != PaymentStatus.UNPAID) {
            Payment payment = new Payment();
            payment.setPurchase(purchase);
            payment.setAmount(paidAmount);
            payment.setPaymentStatus(status);
            payment.setPaymentDate(LocalDateTime.now());

            paymentRepository.save(payment);
        }
    }
}