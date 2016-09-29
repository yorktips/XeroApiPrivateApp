
package com.aireus.util.xero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;


public class XeroClientProperties extends Properties {

    private static final String ENDPOINT_URL = "endpointUrl";
    private static final String CONSUMER_KEY = "consumerKey";
    private static final String CONSUMER_SECRET = "consumerSecret";
    private static final String PRIVATE_KEY_FILE = "privateKeyFile";
    private String endpointUrl;
    private String consumerKey;
    private String consumerSecret;
    private String privateKey;

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        super.load(inStream);
        loadMembers();
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        super.load(reader);
        loadMembers();
    }

    @Override
    public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
        super.loadFromXML(in);
        loadMembers();
    }

    private void loadMembers() throws FileNotFoundException, IOException {

        endpointUrl = getProperty(ENDPOINT_URL);
        consumerKey = getProperty(CONSUMER_KEY);
        consumerSecret = getProperty(CONSUMER_SECRET);

        File file = new File(getProperty(PRIVATE_KEY_FILE));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        privateKey = stringBuilder.toString();

    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}