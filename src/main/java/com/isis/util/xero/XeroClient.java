
package com.isis.util.xero;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.ParameterStyle;
import net.oauth.client.OAuthClient;
import net.oauth.client.OAuthResponseMessage;
import net.oauth.client.httpclient3.HttpClient3;
import net.oauth.signature.RSA_SHA1;

public class XeroClient {

    private String endpointUrl;
    private String consumerKey;
    private String consumerSecret;
    private String privateKey;

    public XeroClient(String endpointUrl, String consumerKey, String consumerSecret, String privateKey) {
        this.endpointUrl = endpointUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.privateKey = privateKey;
    }

    public XeroClient(String consumerKey, String consumerSecret, String privateKey) {
        this.endpointUrl = "https://api.xero.com/api.xro/2.0/";
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.privateKey = privateKey;
    }
    	
    public XeroClient(XeroClientProperties clientProperties) {
        this.endpointUrl = clientProperties.getEndpointUrl();
        this.consumerKey = clientProperties.getConsumerKey();
        this.consumerSecret = clientProperties.getConsumerSecret();
        this.privateKey = clientProperties.getPrivateKey();
    }

    public OAuthAccessor buildAccessor() {

        OAuthConsumer consumer = new OAuthConsumer(null, consumerKey, null, null);
        consumer.setProperty(RSA_SHA1.PRIVATE_KEY, privateKey);
        consumer.setProperty(OAuth.OAUTH_SIGNATURE_METHOD, OAuth.RSA_SHA1);

        OAuthAccessor accessor = new OAuthAccessor(consumer);
        accessor.accessToken = consumerKey;
        accessor.tokenSecret = consumerSecret;

        return accessor;
    }

    public ArrayOfInvoice getInvoices() throws XeroClientException, XeroClientUnexpectedException {
        ArrayOfInvoice arrayOfInvoices = null;
        try {
            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();
            OAuthMessage response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Invoices", null);
            arrayOfInvoices = XeroXmlManager.xmlToInvoices(response.getBodyAsStream());
        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error getting invoices", ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        }
        return arrayOfInvoices;
    }

//    public <T> T getDataBeans() throws XeroClientException, XeroClientUnexpectedException {
//    	
//        T arrayOfT = null;
//        OAuthMessage response = null;
//        try {
//            OAuthClient client = new OAuthClient(new HttpClient3());
//            OAuthAccessor accessor = buildAccessor();
//
//            //response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Invoices", null);
//            if (arrayOfT instanceof  ArrayOfInvoice) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Invoices", null);
//            }else if (arrayOfT instanceof  ArrayOfContact) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Contacts", null);
//            }else if (arrayOfT instanceof  ArrayOfTaxRate) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "TaxRates", null);
//            }else if (arrayOfT instanceof  ArrayOfAccount) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Accounts", null);
//            }else if (arrayOfT instanceof  ArrayOfTrackingCategory) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "TrackingCategories", null);
//            }else if (arrayOfT instanceof  ArrayOfOrganisation) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Organisations", null);
//            }else if (arrayOfT instanceof  ArrayOfCreditNote) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "CreditNotes", null);
//            }else if (arrayOfT instanceof  ArrayOfCurrency) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Currencies", null);
//            }else if (arrayOfT instanceof  ArrayOfPayment) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Payments", null);
//            }else if (arrayOfT instanceof  ArrayOfBrandingTheme) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "BrandingThemes", null);
//            }else if (arrayOfT instanceof  ArrayOfItem) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Items", null);
//            }else if (arrayOfT instanceof  ArrayOfManualJournal) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "ManualJournals", null);
//            }else if (arrayOfT instanceof  ArrayOfReport) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Reports", null);
//            }else if (arrayOfT instanceof  ArrayOfEmployee) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Employees", null);
//            }else if (arrayOfT instanceof  ArrayOfAttachment) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Attachments", null);
//            }else if (arrayOfT instanceof  ArrayOfBankTransaction) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "BankTransactions", null);
//            }else if (arrayOfT instanceof  ArrayOfUser) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Users", null);
//            }else if (arrayOfT instanceof  ArrayOfReceipt) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Receipts", null);
//            }else if (arrayOfT instanceof  ArrayOfExpenseClaim) { 
//            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "ExpenseClaims", null);
//            }            
//            
//            arrayOfT = XeroXmlManager.xmlToBeans(response.getBodyAsStream());
//        } catch (OAuthProblemException ex) {
//            throw new XeroClientException("Error getting invoices", ex);
//        } catch (Exception ex) {
//            throw new XeroClientUnexpectedException("", ex);
//        }
//        return arrayOfT;
//    }

    public <T> T getDataBeans(T t) throws XeroClientException, XeroClientUnexpectedException {
    	return getDataBean(t,null);
    }
    
    public <T> T getDataBean(T t, String id) throws XeroClientException, XeroClientUnexpectedException {
        T arrayOfT ;
        OAuthMessage response = null;
        String paraId="";
        if (id !=null && !(id.trim().equals("")))
        	paraId="/" + id.trim();
        
        try {
            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();
            //o.getClass().getName()
            if (t instanceof  ArrayOfInvoice) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Invoices" + paraId, null);
            }else if (t instanceof  ArrayOfContact) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Contacts" + paraId, null);
            }else if (t instanceof  ArrayOfTaxRate) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "TaxRates" + paraId, null);
            }else if (t instanceof  ArrayOfAccount) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Accounts" + paraId, null);
            }else if (t instanceof  ArrayOfTrackingCategory) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "TrackingCategories" + paraId, null);
            }else if (t instanceof  ArrayOfOrganisation) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Organisations" + paraId, null);
            }else if (t instanceof  ArrayOfCreditNote) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "CreditNotes" + paraId, null);
            }else if (t instanceof  ArrayOfCurrency) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Currencies" + paraId, null);
            }else if (t instanceof  ArrayOfPayment) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Payments" + paraId, null);
            }else if (t instanceof  ArrayOfBrandingTheme) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "BrandingThemes" + paraId, null);
            }else if (t instanceof  ArrayOfItem) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Items" + paraId, null);
            }else if (t instanceof  ArrayOfManualJournal) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "ManualJournals" + paraId, null);
            }else if (t instanceof  ArrayOfReport) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Reports" + paraId, null);
            }else if (t instanceof  ArrayOfEmployee) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Employees" + paraId, null);
            }else if (t instanceof  ArrayOfAttachment) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Attachments" + paraId, null);
            }else if (t instanceof  ArrayOfBankTransaction) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "BankTransactions" + paraId, null);
            }else if (t instanceof  ArrayOfUser) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Users" + paraId, null);
            }else if (t instanceof  ArrayOfReceipt) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Receipts" + paraId, null);
            }else if (t instanceof  ArrayOfExpenseClaim) { 
            	response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "ExpenseClaims" + paraId, null);
            }            
            
            arrayOfT = XeroXmlManager.xmlToBeans(t,response.getBodyAsStream());
        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error getting invoices", ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        }
        return arrayOfT;
    }
    
//    public <T> T getDataBean(String reportUrl) throws XeroClientException, XeroClientUnexpectedException {
//        T t = null;
//        try {
//            OAuthClient client = new OAuthClient(new HttpClient3());
//            OAuthAccessor accessor = buildAccessor();
//            OAuthMessage response = null;
//            
//            response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Reports" + reportUrl, null);
//            ResponseType responseType = XeroXmlManager.xmlToResponse(response.getBodyAsStream());
//            if (responseType != null && responseType.getReports() != null
//                    && responseType.getReports().getReport() != null && responseType.getReports().getReport().size() > 0) {
//                t = (T)(responseType.getReports().getReport().get(0));
//            }
//            
//        } catch (OAuthProblemException ex) {
//            throw new XeroClientException("Error getting invoices", ex);
//        } catch (Exception ex) {
//            throw new XeroClientUnexpectedException("", ex);
//        }
//        return t;
//    }
//    
    public Report getReport(String reportUrl) throws XeroClientException, XeroClientUnexpectedException {
        Report report = null;
        try {
            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();
            OAuthMessage response = client.invoke(accessor, OAuthMessage.GET, endpointUrl + "Reports" + reportUrl, null);
            ResponseType responseType = XeroXmlManager.xmlToResponse(response.getBodyAsStream());
            if (responseType != null && responseType.getReports() != null
                    && responseType.getReports().getReport() != null && responseType.getReports().getReport().size() > 0) {
                report = responseType.getReports().getReport().get(0);
            }
        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error getting invoices", ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        }
        return report;
    }

    public <T> void postBeans(T arrayOfT) throws XeroClientException, XeroClientUnexpectedException {
        try {
            String beansString = XeroXmlManager.beansToXml(arrayOfT);
            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();
            OAuthMessage response = null;
            
            if (arrayOfT instanceof  ArrayOfInvoice) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Invoices", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfContact) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Contacts", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfTaxRate) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "TaxRates", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfAccount) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Accounts", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfTrackingCategory) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "TrackingCategories", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfOrganisation) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Organisations",OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfCreditNote) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "CreditNotes", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfCurrency) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Currencies", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfPayment) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Payments", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfBrandingTheme) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "BrandingThemes", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfItem) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Items", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfManualJournal) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "ManualJournals", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfReport) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Reports", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfEmployee) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Employees", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfAttachment) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Attachments", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfBankTransaction) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "BankTransactions", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfUser) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Users", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfReceipt) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Receipts", OAuth.newList("xml", beansString));
            }else if (arrayOfT instanceof  ArrayOfExpenseClaim) { 
            	response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "ExpenseClaims", OAuth.newList("xml", beansString));
            }            
        
        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error posting contancts", ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        }
    }  
    
    public void postContacts(ArrayOfContact arrayOfContact) throws XeroClientException, XeroClientUnexpectedException {
        try {
            String contactsString = XeroXmlManager.contactsToXml(arrayOfContact);
            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();
            OAuthMessage response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Contacts", OAuth.newList("xml", contactsString));
        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error posting contancts", ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        }
    }

    public void postInvoices(ArrayOfInvoice arrayOfInvoices) throws XeroClientException, XeroClientUnexpectedException {
        try {
            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();
            String contactsString = XeroXmlManager.invoicesToXml(arrayOfInvoices);
            OAuthMessage response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Invoices", OAuth.newList("xml", contactsString));
        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error posting invoices", ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        }
    }

    public void postPayments(ArrayOfPayment arrayOfPayment) throws XeroClientException, XeroClientUnexpectedException {
        try {
            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();
            String paymentsString = XeroXmlManager.paymentsToXml(arrayOfPayment);
            OAuthMessage response = client.invoke(accessor, OAuthMessage.POST, endpointUrl + "Payments", OAuth.newList("xml", paymentsString));
        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error posting payments", ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        }
    }

    public File getInvoiceAsPdf(String invoiceId) throws XeroClientException, XeroClientUnexpectedException {

        File file = null;
        InputStream in = null;
        FileOutputStream out = null;

        try {

            OAuthClient client = new OAuthClient(new HttpClient3());
            OAuthAccessor accessor = buildAccessor();

            OAuthMessage request = accessor.newRequestMessage(OAuthMessage.GET, endpointUrl + "Invoices" + "/" + invoiceId, null);
            request.getHeaders().add(new OAuth.Parameter("Accept", "application/pdf"));
            OAuthResponseMessage response = client.access(request, ParameterStyle.BODY);


            file = new File("Invoice-" + invoiceId + ".pdf");

            if (response != null && response.getHttpResponse() != null && (response.getHttpResponse().getStatusCode() / 2) != 2) {
                in = response.getBodyAsStream();
                out = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } else {
                throw response.toOAuthProblemException();
            }

        } catch (OAuthProblemException ex) {
            throw new XeroClientException("Error getting PDF of invoice " + invoiceId, ex);
        } catch (Exception ex) {
            throw new XeroClientUnexpectedException("", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException ex) {
            }
        }
        return file;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
