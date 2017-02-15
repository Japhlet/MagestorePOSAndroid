package com.magestore.app.lib.service.checkout;

import com.magestore.app.lib.model.checkout.Checkout;
import com.magestore.app.lib.model.checkout.CheckoutShipping;
import com.magestore.app.lib.model.checkout.CheckoutPayment;
import com.magestore.app.lib.model.checkout.Quote;
import com.magestore.app.lib.model.checkout.QuoteCustomer;
import com.magestore.app.lib.model.checkout.QuoteCustomerAddress;
import com.magestore.app.lib.model.checkout.QuoteItemExtension;
import com.magestore.app.lib.model.checkout.QuoteItems;
import com.magestore.app.lib.service.ListService;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Mike on 2/7/2017.
 * Magestore
 * mike@trueplus.vn
 */

public interface CheckoutService extends ListService<Checkout> {
    boolean insert(Checkout... checkouts) throws IOException, InstantiationException, ParseException, IllegalAccessException;

    Checkout saveCart(Checkout checkout, String quoteId) throws IOException, InstantiationException, ParseException, IllegalAccessException;

    Checkout saveShipping(String quoteId, String shippingCode) throws IOException, InstantiationException, ParseException, IllegalAccessException;

    Checkout savePayment(String quoteId, String paymentCode) throws IOException, InstantiationException, ParseException, IllegalAccessException;

    Checkout create();

    CheckoutPayment createPaymentMethod();

    CheckoutShipping createShipping();

    Quote createQuote();

    QuoteItems createQuoteItems();

    QuoteCustomer createQuoteCustomer();

    QuoteCustomerAddress createCustomerAddress();

    QuoteItemExtension createQuoteItemExtension();
}
