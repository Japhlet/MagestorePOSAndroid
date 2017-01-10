package com.magestore.app.pos.controller;

import android.location.Address;
import android.view.View;

import com.magestore.app.lib.model.customer.Customer;
import com.magestore.app.lib.model.customer.CustomerAddress;
import com.magestore.app.lib.service.customer.CustomerService;

import java.util.List;

/**
 * Controller cho danh sách địa chỉ khách hàng
 * Magestore
 * mike@trueplus.vn
 */

public class CustomerAddressListController
        extends AbstractPosListController<CustomerAddress>
        implements ListController<CustomerAddress> {
    /**
     * Controller xử lý các vấn đề liên quan đến customer
     */
    CustomerListController mCustomerListController;
    CustomerService mCustomerService;

    /**
     * Thiết lập service
     *
     * @param controller
     */
    public void setCustomerListController(CustomerListController controller) {
        mCustomerListController = controller;
    }

    /**
     * Thiết lập service
     *
     * @param service
     */
    public void setCustomerService(CustomerService service) {
        mCustomerService = service;
    }

    @Override
    protected List<CustomerAddress> loadDataBackground(Void... params) throws Exception {
        return null;
    }

    /**
     * Thiết lập 1 customer cho androidess
     *
     * @param customer
     */
    public void doSelectCustomer(Customer customer) {
        mList = customer.getAddress();
        mView.bindList(mList);
    }

    @Override
    public void doDeleteItem(CustomerAddress item) {
        super.doDeleteItem(item);
    }

    @Override
    public void doUpdateItem(CustomerAddress item) {
        super.doUpdateItem(item);
    }
}
