package com.magestore.app.pos.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.magestore.app.lib.model.Model;
import com.magestore.app.lib.model.config.ConfigCountry;
import com.magestore.app.lib.model.config.ConfigRegion;
import com.magestore.app.lib.model.customer.Customer;
import com.magestore.app.lib.model.customer.CustomerAddress;
import com.magestore.app.lib.model.directory.Region;
import com.magestore.app.lib.panel.AbstractDetailPanel;
import com.magestore.app.lib.view.SimpleSpinner;
import com.magestore.app.pos.R;
import com.magestore.app.pos.controller.CustomerListController;
import com.magestore.app.pos.model.directory.PosRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Johan on 2/3/17.
 * Magestore
 * dong.le@trueplus.vn
 */

public class CustomerAddNewPanel extends AbstractDetailPanel<Customer> {
    SimpleSpinner mspinGroupID;
    SimpleSpinner s_spinner_country;
    SimpleSpinner b_spinner_country;
    SimpleSpinner s_spinner_state;
    SimpleSpinner b_spinner_state;
    LinearLayout ll_add_new_customer;
    LinearLayout ll_shipping_address;
    LinearLayout ll_billing_address;
    LinearLayout ll_new_shipping_address;
    LinearLayout ll_new_billing_address;
    EditText s_state, b_state, s_first_name, b_first_name, s_last_name, b_last_name, s_street1, b_street1, s_street2, b_street2, s_vat, b_vat;
    EditText first_name, last_name, email, s_company, b_company, s_phone, b_phone, s_city, b_city, s_zipcode, b_zipcode;
    Switch subscribe;
    CheckBox cb_same_billing_and_shipping;
    ConfigRegion shippingRegion;
    ConfigRegion billingRegion;

    public CustomerAddNewPanel(Context context) {
        super(context);
    }

    public CustomerAddNewPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerAddNewPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initLayout() {
        View view = inflate(getContext(), R.layout.panel_customer_add_new, null);
        addView(view);

        mspinGroupID = (SimpleSpinner) findViewById(R.id.spinner_group_id);
        ll_add_new_customer = (LinearLayout) findViewById(R.id.ll_add_new_customer);
        ll_shipping_address = (LinearLayout) findViewById(R.id.ll_shipping_address);
        ll_billing_address = (LinearLayout) findViewById(R.id.ll_billing_address);
        ll_new_shipping_address = (LinearLayout) findViewById(R.id.ll_new_shipping_address);
        ll_new_billing_address = (LinearLayout) findViewById(R.id.ll_new_billing_address);
        s_spinner_country = (SimpleSpinner) findViewById(R.id.s_spinner_country);
        b_spinner_country = (SimpleSpinner) findViewById(R.id.b_spinner_country);
        s_spinner_state = (SimpleSpinner) findViewById(R.id.s_spinner_state);
        b_spinner_state = (SimpleSpinner) findViewById(R.id.b_spinner_state);
        s_state = (EditText) findViewById(R.id.s_state);
        b_state = (EditText) findViewById(R.id.b_state);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        subscribe = (Switch) findViewById(R.id.subscribe);
        s_first_name = (EditText) findViewById(R.id.s_first_name);
        b_first_name = (EditText) findViewById(R.id.b_first_name);
        s_last_name = (EditText) findViewById(R.id.s_last_name);
        b_last_name = (EditText) findViewById(R.id.b_last_name);
        s_company = (EditText) findViewById(R.id.s_company);
        b_company = (EditText) findViewById(R.id.b_company);
        s_phone = (EditText) findViewById(R.id.s_phone);
        b_phone = (EditText) findViewById(R.id.b_phone);
        s_street1 = (EditText) findViewById(R.id.s_street1);
        s_street2 = (EditText) findViewById(R.id.s_street2);
        b_street1 = (EditText) findViewById(R.id.b_street1);
        b_street2 = (EditText) findViewById(R.id.b_street2);
        s_city = (EditText) findViewById(R.id.s_city);
        b_city = (EditText) findViewById(R.id.b_city);
        s_zipcode = (EditText) findViewById(R.id.s_zipcode);
        b_zipcode = (EditText) findViewById(R.id.b_zipcode);
        s_vat = (EditText) findViewById(R.id.s_vat);
        b_vat = (EditText) findViewById(R.id.b_vat);
        cb_same_billing_and_shipping = (CheckBox) findViewById(R.id.cb_same_billing_and_shipping);
    }

    @Override
    public void bindItem(Customer item) {
        super.bindItem(item);
        setCustomerGroupDataSet(((CustomerListController) mController).getCustomerGroupList());
        final Map<String, ConfigCountry> countryDataSet = ((CustomerListController) mController).getCountry();
        setCountryDataSet(countryDataSet);

        ll_shipping_address.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_new_shipping_address.setVisibility(VISIBLE);
                ll_add_new_customer.setVisibility(GONE);
                ll_new_billing_address.setVisibility(GONE);
            }
        });

        ll_billing_address.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_new_billing_address.setVisibility(VISIBLE);
                ll_add_new_customer.setVisibility(GONE);
                ll_new_shipping_address.setVisibility(GONE);
            }
        });

        s_spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<ConfigRegion> listRegion = countryDataSet.get(s_spinner_country.getSelection()).getRegions();
                if (listRegion != null && listRegion.size() > 0) {
                    s_state.setVisibility(GONE);
                    s_spinner_state.setVisibility(VISIBLE);
                    s_spinner_state.bind(listRegion.toArray(new ConfigRegion[0]));
                } else {
                    s_state.setVisibility(VISIBLE);
                    s_spinner_state.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<ConfigRegion> listRegion = countryDataSet.get(b_spinner_country.getSelection()).getRegions();
                if (listRegion != null && listRegion.size() > 0) {
                    b_state.setVisibility(GONE);
                    b_spinner_state.setVisibility(VISIBLE);
                    b_spinner_state.bind(listRegion.toArray(new ConfigRegion[0]));
                } else {
                    b_state.setVisibility(VISIBLE);
                    b_spinner_state.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<ConfigRegion> listRegion = countryDataSet.get(s_spinner_country.getSelection()).getRegions();
                shippingRegion = listRegion.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<ConfigRegion> listRegion = countryDataSet.get(b_spinner_country.getSelection()).getRegions();
                billingRegion = listRegion.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public Customer returnCustomer() {
        boolean check_subscribe = subscribe.isChecked();
        Customer customer = ((CustomerListController) mController).createNewCustomer();
        List<CustomerAddress> listCustomerAddress = new ArrayList<>();
        CustomerAddress shippingAddress = ((CustomerListController) mController).createCustomerAddress();
        shippingAddress.setFirstName(s_first_name.getText().toString());
        shippingAddress.setLastName(s_last_name.getText().toString());
        shippingAddress.setCompany(s_company.getText().toString());
        shippingAddress.setTelephone(s_phone.getText().toString());
        shippingAddress.setStreet1(s_street1.getText().toString());
        shippingAddress.setStreet2(s_street2.getText().toString());
        shippingAddress.setCity(s_city.getText().toString());
        shippingAddress.setPostCode(s_zipcode.getText().toString());
        shippingAddress.setCountry(s_spinner_country.getSelection());
        Region s_region = ((CustomerListController) mController).createRegion();
        if (s_state.getVisibility() == VISIBLE) {
            s_region.setRegionCode(s_state.getText().toString());
            s_region.setRegionName(s_state.getText().toString());
            s_region.setID("0");
            shippingAddress.setRegionID("0");
            shippingAddress.setRegion(s_region);
        } else {
            s_region.setID(shippingRegion.getID());
            s_region.setRegionName(shippingRegion.getName());
            s_region.setRegionCode(shippingRegion.getCode());
            shippingAddress.setState(s_spinner_state.getSelection());
            shippingAddress.setRegionID(s_spinner_state.getSelection());
            shippingAddress.setRegion(s_region);
        }
        shippingAddress.setVAT(s_vat.getText().toString());
        if (cb_same_billing_and_shipping.isChecked()) {
            shippingAddress.setDefaultShipping("1");
            shippingAddress.setDefaultBilling("1");
            listCustomerAddress.add(shippingAddress);
            customer.setAddressList(listCustomerAddress);
        } else {
            shippingAddress.setDefaultShipping("1");
            shippingAddress.setDefaultBilling(String.valueOf(false));

            CustomerAddress billingAddress = ((CustomerListController) mController).createCustomerAddress();
            billingAddress.setDefaultShipping("1");
            billingAddress.setDefaultBilling(String.valueOf(false));
            billingAddress.setFirstName(b_first_name.getText().toString());
            billingAddress.setLastName(b_last_name.getText().toString());
            billingAddress.setCompany(b_company.getText().toString());
            billingAddress.setTelephone(b_phone.getText().toString());
            billingAddress.setStreet1(b_street1.getText().toString());
            billingAddress.setStreet2(b_street2.getText().toString());
            billingAddress.setCity(b_city.getText().toString());
            billingAddress.setPostCode(b_zipcode.getText().toString());
            billingAddress.setCountry(b_spinner_country.getSelection());
            Region b_region = ((CustomerListController) mController).createRegion();
            if (b_state.getVisibility() == VISIBLE) {
                b_region.setRegionCode(b_state.getText().toString());
                b_region.setRegionName(b_state.getText().toString());
                b_region.setID("0");
                billingAddress.setRegionID("0");
                billingAddress.setRegion(s_region);
            } else {
                b_region.setID(billingRegion.getID());
                b_region.setRegionName(billingRegion.getName());
                b_region.setRegionCode(billingRegion.getCode());
                billingAddress.setState(b_spinner_state.getSelection());
                billingAddress.setRegionID(b_spinner_state.getSelection());
                billingAddress.setRegion(s_region);
            }
            billingAddress.setVAT(b_vat.getText().toString());

            listCustomerAddress.add(shippingAddress);
            listCustomerAddress.add(billingAddress);
            customer.setAddressList(listCustomerAddress);
        }
        customer.setID(email.getText().toString());
        customer.setGroupID(mspinGroupID.getSelection());
        customer.setEmail(email.getText().toString());
        customer.setFirstName(first_name.getText().toString());
        customer.setLastName(last_name.getText().toString());
        customer.setSubscriber(String.valueOf(check_subscribe));
        return customer;
    }

    /**
     * Gán giá trị customer group cho spinner
     *
     * @param customerGroupDataSet
     */
    public void setCustomerGroupDataSet(Map<String, String> customerGroupDataSet) {
        if (customerGroupDataSet != null)
            mspinGroupID.bind(customerGroupDataSet);
    }

    public void setCountryDataSet(Map<String, ConfigCountry> countryDataSet) {
        if (countryDataSet != null) {
            s_spinner_country.bindModelMap((Map<String, Model>) (Map<?, ?>) countryDataSet);
            b_spinner_country.bindModelMap((Map<String, Model>) (Map<?, ?>) countryDataSet);
        }
    }
}