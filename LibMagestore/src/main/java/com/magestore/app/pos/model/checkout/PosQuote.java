package com.magestore.app.pos.model.checkout;

import com.magestore.app.lib.model.checkout.Quote;
import com.magestore.app.lib.model.checkout.QuoteCustomer;
import com.magestore.app.lib.model.checkout.QuoteItems;
import com.magestore.app.pos.model.PosAbstractModel;

import java.util.List;

/**
 * Created by Johan on 2/14/17.
 * Magestore
 * dong.le@trueplus.vn
 */

public class PosQuote extends PosAbstractModel implements Quote {
    String quote_id;
    String store_id;
    String customer_id;
    String currency_id;
    String till_id;
    List<PosQuoteItems> items;
    PosQuoteCustomer customer;

    @Override
    public String getID() {
        return quote_id;
    }

    @Override
    public void setQuoteId(String strId) {
        quote_id = strId;
    }

    @Override
    public String getStoreId() {
        return store_id;
    }

    @Override
    public void setStoreId(String strStoreId) {
        store_id = strStoreId;
    }

    @Override
    public String getCustomerId() {
        return customer_id;
    }

    @Override
    public void setCustomerId(String strCustomerId) {
        customer_id = strCustomerId;
    }

    @Override
    public String getCurrencyId() {
        return currency_id;
    }

    @Override
    public void setCurrencyId(String strCurrencyId) {
        currency_id = strCurrencyId;
    }

    @Override
    public String getTillId() {
        return till_id;
    }

    @Override
    public void setTillId(String strTillId) {
        till_id = strTillId;
    }

    @Override
    public List<QuoteItems> getItems() {
        return (List<QuoteItems>) (List<?>) items;
    }

    @Override
    public QuoteCustomer getCustomer() {
        return customer;
    }
}
