package com.isis.util.xero;

public class XeroClientUtil {

	public XeroClientUtil() {}

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
	
}
