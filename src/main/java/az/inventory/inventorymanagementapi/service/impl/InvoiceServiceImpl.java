package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.entity.*;
import az.inventory.inventorymanagementapi.enums.InvoiceType;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import az.inventory.inventorymanagementapi.repository.InvoiceItemRepository;
import az.inventory.inventorymanagementapi.repository.InvoiceRepository;
import az.inventory.inventorymanagementapi.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    @Override
    public Invoice generateInvoiceForPurchase(Purchase purchase) {
        Invoice invoice = Invoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .invoiceDate(LocalDateTime.now())
                .totalAmount(purchase.getTotalAmount())
                .purchase(purchase)
                .invoiceType(InvoiceType.PURCHASE)
                .status(PaymentStatus.UNPAID)
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        for (PurchaseItem item : purchase.getItems()) {
            InvoiceItem invoiceItem = InvoiceItem.builder()
                    .invoice(savedInvoice)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .totalPrice(item.getTotalPrice())
                    .build();
            invoiceItems.add(invoiceItem);
        }

        invoiceItemRepository.saveAll(invoiceItems);

        return savedInvoice;
    }

    private String generateInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
