package org.example.services;

import org.example.soap.MNBArfolyamServiceSoap;
import org.example.soap.MNBArfolyamServiceSoapImpl;

public class SoapClientService {

    private final MNBArfolyamServiceSoap soapClient;

    public SoapClientService() {
        soapClient = new MNBArfolyamServiceSoapImpl().getCustomBindingMNBArfolyamServiceSoap();
    }

    public String getAllExchangeRates() {
        try {
            return soapClient.getCurrentExchangeRates();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while fetching all exchange rates: " + e.getMessage();
        }
    }

    public String getFilteredExchangeRates(String startDate, String endDate, String currencies) {
        try {
            return soapClient.getExchangeRates(startDate, endDate, currencies);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while fetching filtered exchange rates: " + e.getMessage();
        }
    }
}