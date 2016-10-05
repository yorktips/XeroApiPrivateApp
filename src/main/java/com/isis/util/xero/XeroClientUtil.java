package com.isis.util.xero;

import java.util.List;

import org.apache.log4j.Logger;

public class XeroClientUtil {

	static Logger logger = Logger.getLogger(XeroClientUtil.class.getName());

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

	public static int getMaxAccountCode(ArrayOfAccount arrayOfExistingAccounts) {
		int nMax=1;
		if (arrayOfExistingAccounts != null && arrayOfExistingAccounts.getAccount() != null) {
			for (Account account : arrayOfExistingAccounts.getAccount()) {
				if (Integer.parseInt(account.getCode()) > nMax)
					nMax=Integer.parseInt(account.getCode());
			}
		}
		return nMax;
	}
	
	public static Account getPayableAccount(XeroClient xeroClient, ArrayOfAccount arrayOfExistingAccounts, String name) {
		if (arrayOfExistingAccounts != null && arrayOfExistingAccounts.getAccount() != null) {
			for (Account account : arrayOfExistingAccounts.getAccount()) {
				if (account.getName().equalsIgnoreCase(name))
					return account;
			}
		}
		int nMax=getMaxAccountCode(arrayOfExistingAccounts);
		nMax++;
		logger.debug("Creating a new Account. code=" + String.valueOf(nMax));
		ArrayOfAccount aOA= new ArrayOfAccount();
        List<Account> accounts = aOA.getAccount();

		Account account = new Account();
		account.setName(name);
		account.setType(AccountType.REVENUE);
		account.setEnablePaymentsToAccount(true);
		account.setCode(String.valueOf(nMax));
		
		accounts.add(account);
		try{
			xeroClient.postBeans(aOA);
			accounts = arrayOfExistingAccounts.getAccount();
			accounts.add(account);
			logger.debug("Account(code=" + String.valueOf(nMax) + ") has been created succesfully.");
			return account;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
