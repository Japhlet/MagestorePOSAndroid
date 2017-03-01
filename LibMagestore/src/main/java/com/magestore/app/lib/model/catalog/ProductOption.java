package com.magestore.app.lib.model.catalog;

import com.magestore.app.lib.model.Model;
import com.magestore.app.pos.model.catalog.PosProductOptionValue;

import java.util.List;

/**
 * Created by Mike on 12/14/2016.
 * Magestore
 * mike@trueplus.vn
 */

public interface ProductOption extends Model {
    String getOptionID();

    String getProductID();

    String getType();

    boolean isRequired();

    String getSku();

    int getMaxCharacters();

    String getFileExtension();

    String getImageSizeX();

    String getImageSizeY();

    String getSortOrder();

    String getDefaultTitle();

    String getStoreTitle();

    String getTitle();

    String getDefaultPrice();

    String getDefaultPriceType();

    String getStorePrice();

    String getStorePriceType();

    String getPrice();

    String getPriceType();

    List<PosProductOptionValue> getOptionValueList();
}
