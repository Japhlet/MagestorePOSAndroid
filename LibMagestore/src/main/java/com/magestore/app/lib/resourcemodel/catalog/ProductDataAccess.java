package com.magestore.app.lib.resourcemodel.catalog;

import android.graphics.Bitmap;

import com.magestore.app.lib.connection.ConnectionException;
import com.magestore.app.lib.model.catalog.Product;
import com.magestore.app.lib.model.catalog.ProductOption;
import com.magestore.app.lib.parse.ParseException;
import com.magestore.app.lib.resourcemodel.DataAccess;
import com.magestore.app.lib.resourcemodel.DataAccessException;
import com.magestore.app.lib.resourcemodel.ListDataAccess;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mike on 12/14/2016.
 * Magestore
 * mike@trueplus.vn
 */

public interface ProductDataAccess extends DataAccess, ListDataAccess<Product> {
    Bitmap retrieveBitmap(Product product, int sizeWidth, int sizeHeight) throws IOException;

    List<ProductOption> loadProductOption(Product product) throws IOException;

    void loadProductOption(List<Product> listProduct);

    //    List<Product> getProducts(int pageSize, int currentPage) throws DataAccessException, ConnectionException, ParseException, IOException, java.text.ParseException;
//    void getProducts(Product product);
//    void updateProduct(Product product);
    List<Product> retrieve(String categoryId, String searchString,int pageSize, int currentPage) throws DataAccessException, ConnectionException, ParseException, IOException, java.text.ParseException;
}
