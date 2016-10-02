
package com.isis.util.xero;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.transform.stream.StreamSource;
import net.oauth.OAuthProblemException;


public class XeroXmlManager {

    public static ArrayOfInvoice xmlToInvoices(InputStream invoiceStream) {

        ArrayOfInvoice arrayOfInvoices = null;

        try {
            JAXBContext context = JAXBContext.newInstance(ResponseType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<ResponseType> element = unmarshaller.unmarshal(new StreamSource(invoiceStream), ResponseType.class);
            ResponseType response = element.getValue();
            arrayOfInvoices = response.getInvoices();

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return arrayOfInvoices;
    }

    
    public static <T> T xmlToBeans(T t, InputStream stream) {

        T arrayOfT = null;

        try {
            JAXBContext context = JAXBContext.newInstance(ResponseType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<ResponseType> element = unmarshaller.unmarshal(new StreamSource(stream), ResponseType.class);
            ResponseType response = element.getValue();

            if (t instanceof  ArrayOfInvoice) { 
            	arrayOfT = (T)response.getInvoices();
            }else if (t instanceof  ArrayOfContact) { 
            	arrayOfT = (T)response.getContacts();
            }else if (t instanceof  ArrayOfTaxRate) { 
            	arrayOfT = (T)response.getTaxRates();            	
            }else if (t instanceof  ArrayOfAccount) { 
            	arrayOfT = (T)response.getAccounts();
            }else if (t instanceof  ArrayOfTrackingCategory) { 
            	arrayOfT = (T)response.getTrackingCategories();
            }else if (t instanceof  ArrayOfOrganisation) { 
            	arrayOfT = (T)response.getOrganisations();
            }else if (t instanceof  ArrayOfCreditNote) { 
            	arrayOfT = (T)response.getCreditNotes();
            }else if (t instanceof  ArrayOfCurrency) { 
            	arrayOfT = (T)response.getCurrencies();
            }else if (t instanceof  ArrayOfPayment) { 
            	arrayOfT = (T)response.getPayments();
            }else if (t instanceof  ArrayOfBrandingTheme) { 
            	arrayOfT = (T)response.getBrandingThemes();
            }else if (t instanceof  ArrayOfItem) { 
            	arrayOfT = (T)response.getItems();
            }else if (t instanceof  ArrayOfManualJournal) { 
            	arrayOfT = (T)response.getManualJournals();
            }else if (t instanceof  ArrayOfReport) { 
            	arrayOfT = (T)response.getReports();
            }else if (t instanceof  ArrayOfEmployee) { 
            	arrayOfT = (T)response.getEmployees();
            }else if (t instanceof  ArrayOfAttachment) { 
            	arrayOfT = (T)response.getAttachments();
            }else if (t instanceof  ArrayOfBankTransaction) { 
            	arrayOfT = (T)response.getBankTransactions();
            }else if (t instanceof  ArrayOfUser) { 
            	arrayOfT = (T)response.getUsers();
            }else if (t instanceof  ArrayOfReceipt) { 
            	arrayOfT = (T)response.getReceipts();
            }else if (t instanceof  ArrayOfExpenseClaim) { 
            	arrayOfT = (T)response.getExpenseClaims();
            }            
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return arrayOfT;
    }
    
    
    public static ResponseType xmlToResponse(InputStream responseStream) {

        ResponseType response = null;

        try {
            JAXBContext context = JAXBContext.newInstance(ResponseType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<ResponseType> element = unmarshaller.unmarshal(new StreamSource(responseStream), ResponseType.class);
            response = element.getValue();

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return response;
    }

    public static ApiExceptionExtended xmlToException(String exceptionString) {

        ApiExceptionExtended apiException = null;

        try {
            JAXBContext context = JAXBContext.newInstance(ApiExceptionExtended.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<ApiExceptionExtended> element = unmarshaller.unmarshal(new StreamSource(new StringReader(exceptionString)), ApiExceptionExtended.class);
            apiException = element.getValue();

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return apiException;
    }

    public static String oAuthProblemExceptionToXml(OAuthProblemException authProblemException) {

        String oAuthProblemExceptionString = null;

        Map<String, Object> params = authProblemException.getParameters();
        for (String key : params.keySet()) {
            Object o = params.get(key);
            if (key.contains("ApiException")) {
                oAuthProblemExceptionString = key + "=" + o.toString();
            }
        }

        return oAuthProblemExceptionString;
    }

    public static <T> String beansToXml(T arrayOfT) {

        String contactsString = null;

        try {

            JAXBContext context = JAXBContext.newInstance(ResponseType.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            ObjectFactory factory = new ObjectFactory();
            StringWriter stringWriter = new StringWriter();
            
            if (arrayOfT instanceof  ArrayOfInvoice) { 
            	JAXBElement<ArrayOfInvoice> element = factory.createInvoices((ArrayOfInvoice)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfContact) { 
            	JAXBElement<ArrayOfContact> element = factory.createContacts((ArrayOfContact)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfTaxRate) { 
            	JAXBElement<ArrayOfTaxRate> element = factory.createTaxRates((ArrayOfTaxRate)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfAccount) { 
            	JAXBElement<ArrayOfAccount> element = factory.createAccounts((ArrayOfAccount)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfTrackingCategory) { 
            	JAXBElement<ArrayOfTrackingCategory> element = factory.createTrackingCategories((ArrayOfTrackingCategory)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfOrganisation) { 
            	JAXBElement<ArrayOfOrganisation> element = factory.createOrganisations((ArrayOfOrganisation)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfCreditNote) { 
            	JAXBElement<ArrayOfCreditNote> element = factory.createCreditNotes((ArrayOfCreditNote)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfCurrency) { 
            	JAXBElement<ArrayOfCurrency> element = factory.createCurrencies((ArrayOfCurrency)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfPayment) { 
            	JAXBElement<ArrayOfPayment> element = factory.createPayments((ArrayOfPayment)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfBrandingTheme) { 
            	JAXBElement<ArrayOfBrandingTheme> element = factory.createBrandingThemes((ArrayOfBrandingTheme)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfManualJournal) { 
            	JAXBElement<ArrayOfManualJournal> element = factory.createManualjournals((ArrayOfManualJournal)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfReport) { 
            	//JAXBElement<ArrayOfReport> element = factory.createArrayOfReport();
            	//marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfEmployee) { 
            	JAXBElement<ArrayOfEmployee> element = factory.createEmployees((ArrayOfEmployee)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfAttachment) { 
            	JAXBElement<ArrayOfAttachment> element = factory.createAttachments((ArrayOfAttachment)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfBankTransaction) { 
            	JAXBElement<ArrayOfBankTransaction> element = factory.createBankTransactions((ArrayOfBankTransaction)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfUser) { 
            	JAXBElement<ArrayOfUser> element = factory.createUsers((ArrayOfUser)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfReceipt) { 
            	JAXBElement<ArrayOfReceipt> element = factory.createReceipts((ArrayOfReceipt)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }else if (arrayOfT instanceof  ArrayOfExpenseClaim) { 
            	JAXBElement<ArrayOfExpenseClaim> element = factory.createExpenseClaims((ArrayOfExpenseClaim)arrayOfT);
            	marshaller.marshal(element, stringWriter);
            }            

            contactsString = stringWriter.toString();

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return contactsString;
    }
    
    public static String contactsToXml(ArrayOfContact arrayOfContacts) {

        String contactsString = null;

        try {

            JAXBContext context = JAXBContext.newInstance(ResponseType.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            ObjectFactory factory = new ObjectFactory();
            JAXBElement<ArrayOfContact> element = factory.createContacts(arrayOfContacts);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(element, stringWriter);
            contactsString = stringWriter.toString();

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return contactsString;
    }

    public static String invoicesToXml(ArrayOfInvoice arrayOfInvoices) {

        String invoicesString = null;

        try {

            JAXBContext context = JAXBContext.newInstance(ResponseType.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            ObjectFactory factory = new ObjectFactory();
            JAXBElement<ArrayOfInvoice> element = factory.createInvoices(arrayOfInvoices);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(element, stringWriter);
            invoicesString = stringWriter.toString();

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return invoicesString;
    }

    public static String paymentsToXml(ArrayOfPayment arrayOfPayment) {

        String paymentsString = null;

        try {

            JAXBContext context = JAXBContext.newInstance(ResponseType.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            ObjectFactory factory = new ObjectFactory();
            JAXBElement<ArrayOfPayment> element = factory.createPayments(arrayOfPayment);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(element, stringWriter);
            paymentsString = stringWriter.toString();

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }

        return paymentsString;
    }
}
