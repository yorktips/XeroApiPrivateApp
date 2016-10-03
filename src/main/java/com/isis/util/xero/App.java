package com.isis.util.xero;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


public class App {

	public static Account getAccountByCode(ArrayOfAccount arrayOfExistingAccounts, String code) {
		if (arrayOfExistingAccounts != null && arrayOfExistingAccounts.getAccount() != null) {
			for (Account account : arrayOfExistingAccounts.getAccount()) {
				if (account.getCode().equals(code))
					return account;
			}
		}
		return null;
	}
	
	public static Contact getContactByCode(ArrayOfContact arrayOfExistingContacts, String id) {
		if (arrayOfExistingContacts != null && arrayOfExistingContacts.getContact() != null) {
			for (Contact contact : arrayOfExistingContacts.getContact()) {
				if (contact.getContactID().equalsIgnoreCase(id))
					return contact;
			}
		}
		return null;
	}
	
	public static Contact getContactByName(ArrayOfContact arrayOfExistingContacts, String name) {
		if (arrayOfExistingContacts != null && arrayOfExistingContacts.getContact() != null) {
			for (Contact contact : arrayOfExistingContacts.getContact()) {
				if (contact.getName().equalsIgnoreCase(name))
					return contact;
			}
		}
		return null;
	}
	
	public static Account getAccountByAccountId(ArrayOfAccount arrayOfExistingAccounts, String id) {
		if (arrayOfExistingAccounts != null && arrayOfExistingAccounts.getAccount() != null) {
			for (Account account : arrayOfExistingAccounts.getAccount()) {
				if (account.getAccountID().equals(id))
					return account;
			}
		}
		return null;
	}
	
	public static Account getAccountByName(ArrayOfAccount arrayOfExistingAccounts, String name) {
		if (arrayOfExistingAccounts != null && arrayOfExistingAccounts.getAccount() != null) {
			for (Account account : arrayOfExistingAccounts.getAccount()) {
				if (account.getName().equalsIgnoreCase(name))
					return account;
			}
		}
		return null;
	}
	
    public static void main(String[] args) {

        // Prepare the Xero Client
        XeroClient xeroClient = null;
        Account account=null;
        Contact contact=null;
        
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
            contact=getContactByName(arrayOfExistingContacts,"POS System");
            if (arrayOfExistingContacts != null && arrayOfExistingContacts.getContact() != null) {
                System.out.println("");
                for (Contact wk_contact : arrayOfExistingContacts.getContact()) {
                    System.out.println("Contact: " + wk_contact.getContactID() + ";name=" + wk_contact.getName());
                }
            }

            ArrayOfAccount arrayOfExistingAccounts=xeroClient.getDataBeans(new ArrayOfAccount());
            account=getAccountByCode(arrayOfExistingAccounts,"200");
            
            if (arrayOfExistingAccounts != null && arrayOfExistingAccounts.getAccount() != null) {
                System.out.println("");
                for (Account act : arrayOfExistingAccounts.getAccount()) {
                    System.out.println("Account: id=" + act.getAccountID() + ";code=" + act.getCode() + ";name=" + act.getName());
                }
            }
            
        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }

        String InvoiceNumber="";
        // Create an Invoice
        Invoice invoice = null;
        try {

            ArrayOfInvoice arrayOfInvoice = new ArrayOfInvoice();
            List<Invoice> invoices = arrayOfInvoice.getInvoice();
            invoice = new Invoice();
            InvoiceNumber="INV-API-006";
            invoice.setContact(contact);

            ArrayOfLineItem arrayOfLineItem = new ArrayOfLineItem();
            List<LineItem> lineItems = arrayOfLineItem.getLineItem();
            LineItem lineItem = new LineItem();
            lineItem.setAccountCode("200");
            BigDecimal qty = new BigDecimal("2");
            lineItem.setQuantity(qty);
            BigDecimal amnt = new BigDecimal("150.00");
            lineItem.setUnitAmount(amnt);
            lineItem.setDescription("Programming books2");
            lineItem.setLineAmount(qty.multiply(amnt));
            lineItems.add(lineItem);
            invoice.setLineItems(arrayOfLineItem);

            invoice.setDate(Calendar.getInstance());
            Calendar due = Calendar.getInstance();
            due.set(due.get(Calendar.YEAR), due.get(Calendar.MONTH) + 1, 20);
            invoice.getLineAmountTypes().add("Inclusive");
            invoice.setDueDate(due);
            invoice.setInvoiceNumber(InvoiceNumber);
            invoice.setType(InvoiceType.ACCREC);
            invoice.setStatus(InvoiceStatus.AUTHORISED);
            
            invoices.add(invoice);
            
            xeroClient.postBeans(arrayOfInvoice);
            
            
        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }

        // Add a payment to an exisiting Invoice
        try {
            Invoice invoice1 = new Invoice();
            invoice1.setInvoiceNumber("INV-API-006");

            account = new Account();
            account.setCode("880");//account.getCode());

            Payment payment = new Payment();
            payment.setAccount(account);
            payment.setInvoice(invoice1);
            payment.setAmount(new BigDecimal("300.00"));
            payment.setDate(Calendar.getInstance());

            ArrayOfPayment arrayOfPayment = new ArrayOfPayment();
            List<Payment> payments = arrayOfPayment.getPayment();
            payments.add(payment);

            xeroClient.postBeans(arrayOfPayment);
            
        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }
    }
}
