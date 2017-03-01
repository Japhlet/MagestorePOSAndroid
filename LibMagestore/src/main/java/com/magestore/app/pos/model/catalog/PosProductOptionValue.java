package com.magestore.app.pos.model.catalog;

import com.magestore.app.lib.model.catalog.ProductOption;
import com.magestore.app.lib.model.catalog.ProductOptionValue;
import com.magestore.app.pos.model.PosAbstractModel;

/**
 * Created by Mike on 2/16/2017.
 * Magestore
 * mike@trueplus.vn
 */
public class PosProductOptionValue extends PosAbstractModel implements ProductOptionValue {
    String option_type_id;
    String option_id;
    String sku;
    String sort_order;
    String default_title;
    String store_title;
    String title;
    String default_price;
    String default_price_type;
    String store_price;
    String store_price_type;
    String price;
    String price_type;

    @Override
    public String getID() {
        return option_type_id;
    }

    @Override
    public String getDisplayContent() {
        return (title != null) ? title : default_title;
    }

    @Override
    public String getSubDisplayContent() {
        return (price != null) ? price : store_title;
    }

    @Override
    public String getOptionTypeID() {
        return option_type_id;
    }

    @Override
    public String getOptionID() {
        return option_id;
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public String getSortOrder() {
        return sort_order;
    }

    @Override
    public String getDefaultTitle() {
        return default_title;
    }

    @Override
    public String getStoreTitle() {
        return store_title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDefaultPrice() {
        return default_price;
    }

    @Override
    public String getDefaultPriceType() {
        return default_price_type;
    }

    @Override
    public String getStorePrice() {
        return store_price;
    }

    @Override
    public String getStorePriceType() {
        return store_price_type;
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public String getPriceType() {
        return price_type;
    }
}
