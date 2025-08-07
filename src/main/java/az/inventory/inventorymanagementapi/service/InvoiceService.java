package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.entity.Invoice;
import az.inventory.inventorymanagementapi.entity.Purchase;

public interface InvoiceService {
    Invoice generateInvoiceForPurchase(Purchase purchase);
}
