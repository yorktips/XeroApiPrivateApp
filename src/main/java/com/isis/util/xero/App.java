package com.isis.util.xero;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


public class App {

    public static void main(String[] args) {

        // Prepare the Xero Client
        XeroClient xeroClient = null;
        try {
            XeroClientProperties clientProperties = new XeroClientProperties();
            clientProperties.load(new FileInputStream("./xeroApi.properties"));
            xeroClient = new XeroClient(clientProperties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Retrieve a list of Invoices
        try {

            //ArrayOfInvoice arrayOfExistingInvoices = xeroClient.getInvoices();
            ArrayOfInvoice arrayOfExistingInvoices=xeroClient.getDataBeans(new ArrayOfInvoice());
            if (arrayOfExistingInvoices != null && arrayOfExistingInvoices.getInvoice() != null) {

                System.out.println("");
                for (Invoice invoice : arrayOfExistingInvoices.getInvoice()) {
                    System.out.println("Invoice: " + invoice.getInvoiceID());
                }

                // Retrieve an invoice as a PDF 
                // (can be used to retrieve json too, just change application/pdf to application/json)
                if (!arrayOfExistingInvoices.getInvoice().isEmpty()) {
                    xeroClient.getInvoiceAsPdf(arrayOfExistingInvoices.getInvoice().get(0).getInvoiceID());
                }
            }

            ArrayOfContact arrayOfExistingContacts=xeroClient.getDataBeans(new ArrayOfContact());
            if (arrayOfExistingContacts != null && arrayOfExistingContacts.getContact() != null) {

                System.out.println("");
                for (Contact contact : arrayOfExistingContacts.getContact()) {
                    System.out.println("Contact: " + contact.getContactID() + ";name=" + contact.getName());
                }
            }

            ArrayOfAccount arrayOfExistingAccounts=xeroClient.getDataBeans(new ArrayOfAccount());
            if (arrayOfExistingAccounts != null && arrayOfExistingAccounts.getAccount() != null) {

                System.out.println("");
                for (Account account : arrayOfExistingAccounts.getAccount()) {
                    System.out.println("Account: " + account.getAccountID() + ";name=" + account.getName());
                }
            }
            
        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }

        // Create an Invoice
        Invoice invoice = null;
        try {

            ArrayOfInvoice arrayOfInvoice = new ArrayOfInvoice();
            List<Invoice> invoices = arrayOfInvoice.getInvoice();
            invoice = new Invoice();

            
            Contact contact = new Contact();
            contact.setName("Jane Smith");
            contact.setEmailAddress("jane@smith.com");
            invoice.setContact(contact);

            ArrayOfLineItem arrayOfLineItem = new ArrayOfLineItem();
            List<LineItem> lineItems = arrayOfLineItem.getLineItem();
            LineItem lineItem = new LineItem();
            lineItem.setAccountCode("200");
            BigDecimal qty = new BigDecimal("2");
            lineItem.setQuantity(qty);
            BigDecimal amnt = new BigDecimal("50.00");
            lineItem.setUnitAmount(amnt);
            lineItem.setDescription("Programming books");
            lineItem.setLineAmount(qty.multiply(amnt));
            lineItems.add(lineItem);
            invoice.setLineItems(arrayOfLineItem);

            invoice.setDate(Calendar.getInstance());
            Calendar due = Calendar.getInstance();
            due.set(due.get(Calendar.YEAR), due.get(Calendar.MONTH) + 1, 20);
            invoice.getLineAmountTypes().add("Inclusive");
            invoice.setDueDate(due);
            invoice.setInvoiceNumber("INV-API-001");
            invoice.setType(InvoiceType.ACCREC);
            invoice.setStatus(InvoiceStatus.AUTHORISED);
            invoices.add(invoice);

            xeroClient.postBeans(arrayOfInvoice);
        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }

        // Create a new Contact
        try {

            ArrayOfContact arrayOfContact = new ArrayOfContact();
            List<Contact> contacts = arrayOfContact.getContact();

            Contact contact1 = new Contact();
            contact1.setName("John Smith");
            contact1.setEmailAddress("john@smith.com");
            contacts.add(contact1);
            xeroClient.postContacts(arrayOfContact);

        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }

        // Add a payment to an exisiting Invoice
        try {

            Invoice invoice1 = new Invoice();
            invoice1.setInvoiceNumber("INV-0038");

            Account account = new Account();
            account.setCode("090");

            Payment payment = new Payment();
            payment.setAccount(account);
            payment.setInvoice(invoice);
            payment.setAmount(new BigDecimal("20.00"));
            payment.setDate(Calendar.getInstance());

            ArrayOfPayment arrayOfPayment = new ArrayOfPayment();
            List<Payment> payments = arrayOfPayment.getPayment();
            payments.add(payment);

            xeroClient.postPayments(arrayOfPayment);

        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }
    }
}
