package com.magestore.app.pos.service.customer;

import com.google.gson.Gson;
import com.magestore.app.lib.connection.ConnectionException;
import com.magestore.app.lib.model.catalog.Product;
import com.magestore.app.lib.model.customer.Complain;
import com.magestore.app.lib.model.customer.CustomerAddress;
import com.magestore.app.lib.model.customer.Customer;
import com.magestore.app.lib.resourcemodel.DataAccessException;
import com.magestore.app.lib.resourcemodel.DataAccessFactory;
import com.magestore.app.pos.model.customer.PosComplain;
import com.magestore.app.pos.model.customer.PosCustomerAddress;
import com.magestore.app.pos.model.customer.PosCustomer;
import com.magestore.app.lib.resourcemodel.customer.CustomerDataAccess;
import com.magestore.app.lib.service.customer.CustomerService;
import com.magestore.app.pos.service.AbstractService;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Các thao tác nghiệp vụ liên quan đến customer
 * Created by Mike on 12/27/2016.
 * Magestore
 * mike@trueplus.vn
 */

public class POSCustomerService extends AbstractService implements CustomerService {
    Customer mCustomer;

    @Override
    public Customer createCustomer() {
        return new PosCustomer();
    }


    @Override
    public List<Customer> retrieveCustomerList(int size) throws InstantiationException, IllegalAccessException, IOException, ParseException {
        // Khởi tạo customer gateway factory
        DataAccessFactory factory = DataAccessFactory.getFactory(getContext());
        CustomerDataAccess customerDataAccess = factory.generateCustomerDataAccess();

        // lấy danh sách khách hàng
        return customerDataAccess.retrieveCustomers(size, 1);
    }

    /**
     * Cập nhật API danh sách khách hàng
     * @param customer
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public boolean updateCustomer(Customer customer) throws InstantiationException, IllegalAccessException, IOException, ParseException {
        // Khởi tạo customer gateway factory
        DataAccessFactory factory = DataAccessFactory.getFactory(getContext());
        CustomerDataAccess customerDataAccess = factory.generateCustomerDataAccess();

        // kiểm tra có complains không, không thì phải tạo mới
        if (customer.getComplain() == null) {
            List<Complain> listComplain = (List<Complain>)(List<?>) new ArrayList<PosComplain>();
            customer.setComplain(listComplain);
        }

        // cập nhật d.sách
        return  customerDataAccess.updateCustomer(customer);
    }

    /**
     * Cập nhật API thêm mới 1 customer
     * @param customer
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public boolean insertCustomer(Customer customer) throws InstantiationException, IllegalAccessException, IOException, ParseException {
        // Khởi tạo customer gateway factory
        DataAccessFactory factory = DataAccessFactory.getFactory(getContext());
        CustomerDataAccess customerDataAccess = factory.generateCustomerDataAccess();

        // lấy danh sách khách hàng
        return customerDataAccess.insertCustomer(customer);
    }

    /**
     * Khởi tạo 1 address trống
     * @return
     */
    @Override
    public CustomerAddress createAddress() {
        return new PosCustomerAddress();
    }

    /**
     * Khởi tạo 1 address cho customer
     * @param customer
     * @param customerAddress
     * @return
     */
    @Override
    public CustomerAddress createAddress(Customer customer, CustomerAddress customerAddress) {
        // cho address tham chiếu đến customer
        customerAddress.setCustomer(customer);

        // Kiểm tra danh sách địa chỉ, Nếu chưa có địa chỉ, khởi tạo danh sách
        List<CustomerAddress> customerAddressList = customer.getAddress();
        if (customerAddressList == null)
            customerAddressList = new ArrayList<CustomerAddress>();
        customer.setAddressList(customerAddressList);

        // Thêm địa chỉ mới vào
        customerAddressList.add(customerAddress);
        return customerAddress;
    }

    /**
     * Khởi tạo 1 address trống cho customer
     * @param customer
     * @return
     */
    @Override
    public CustomerAddress createAddress(Customer customer) {
        CustomerAddress customerAddress = createAddress();
        return createAddress(customer, customerAddress);
    }

    /**
     * Gỡ 1 address ra khỏi customer, không cập nhật API
     * @param customer
     * @param customerAddress
     */
    @Override
    public boolean removeAddress(Customer customer, CustomerAddress customerAddress) {
        // Tìm customerAddress
        if (customer != null && customerAddress != null & customer.getAddress() != null)
            return customer.getAddress().remove(customerAddress);
        return false;
    }

    /**
     * Xóa 1 address khỏi customer, cập nhật API
     * @param customer
     * @param customerAddress
     * @throws IOException
     * @throws InstantiationException
     * @throws ParseException
     * @throws IllegalAccessException
     */
    @Override
    public boolean deleteAddress(Customer customer, CustomerAddress customerAddress) throws IOException, InstantiationException, ParseException, IllegalAccessException {
        // cập nhật customer
        updateCustomer(customer);

        // Xóa address trong danh sách
        return removeAddress(customer, customerAddress);
    }

    /**
     * Cập nhật 1 address vào customer, cập nhật API
     * @param customer
     * @param customerAddress
     */
    @Override
    public boolean updateAddress(Customer customer, CustomerAddress customerAddress) throws IOException, InstantiationException, ParseException, IllegalAccessException {
        // cập nhật customer trên API
        return updateCustomer(customer);
    }

    /**
     * Thêm 1 address cho customer, cập nhật API
     * @param customer
     * @param customerAddress
     */
    @Override
    public boolean insertAddress(Customer customer, CustomerAddress customerAddress) throws IOException, InstantiationException, ParseException, IllegalAccessException {
        createAddress(customer, customerAddress);
        return updateCustomer(customer);
    }

    /**
     * Khởi tạo 1 complain trống
     * @return
     */
    @Override
    public Complain createComplain() {
        return new PosComplain();
    }

    /**
     * Khởi tạo 1 complain cho customer
     * @param customer
     * @return
     */
    @Override
    public Complain createComplain(Customer customer) {
        Complain complain = createComplain();
        return createComplain(customer, complain);
    }

    /**
     * Khởi tạo 1 complain cho customer
     * @param customer
     * @param complain
     * @return
     */
    @Override
    public Complain createComplain(Customer customer, Complain complain) {
        // cho complain tham chiếu đến customer
        complain.setCustomerID(customer.getID());

        // kiểm tra nếu complain list của customer đang trống thì khởi tạo
        List<Complain> complainList = customer.getComplain();
        if (complainList == null) complainList = new ArrayList<Complain>();
        customer.setComplain(complainList);

        // thêm một complain vào cho customer
        complainList.add(complain);
        return complain;
    }

    /**
     * Gọi API Trả về danh sách complain của 1 customer
     * @param customerID
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public List<Complain> retrieveComplain(String customerID) throws InstantiationException, IllegalAccessException, IOException, ParseException {
        // Khởi tạo customer gateway factory
        DataAccessFactory factory = DataAccessFactory.getFactory(getContext());
        CustomerDataAccess customerDataAccess = factory.generateCustomerDataAccess();

        // lấy danh sách khách hàng
        return customerDataAccess.retrieveCustomerComplain(customerID);

    }

    /**
     * Gọi API, lưu complain vào cho khách hàng
     * @param customer
     * @param complain
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public boolean insertComplain(Customer customer, Complain complain) throws InstantiationException, IllegalAccessException, IOException, ParseException {
        // chèn complain vào customer
        createComplain(customer, complain);

        // Khởi tạo customer gateway factory
        DataAccessFactory factory = DataAccessFactory.getFactory(getContext());
        CustomerDataAccess customerDataAccess = factory.generateCustomerDataAccess();

        // lấy danh sách khách hàng
        return customerDataAccess.insertCustomerComplain(customer, complain);
    }

}
