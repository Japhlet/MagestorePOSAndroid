package com.magestore.app.pos.panel;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.magestore.app.lib.model.checkout.cart.CartItem;
import com.magestore.app.lib.panel.AbstractListPanel;
import com.magestore.app.pos.R;
import com.magestore.app.pos.controller.OrderInvoiceItemsListController;
import com.magestore.app.pos.databinding.CardOrderInvoiceItemContentBinding;

import java.util.List;

/**
 * Created by Johan on 2/3/17.
 * Magestore
 * dong.le@trueplus.vn
 */

public class OrderInvoiceItemsListPanel extends AbstractListPanel<CartItem> {

    public OrderInvoiceItemsListPanel(Context context) {
        super(context);
    }

    public OrderInvoiceItemsListPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderInvoiceItemsListPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initLayout() {
        // Load layout view danh sách items của khách hàng
        View v = inflate(getContext(), R.layout.panel_order_invoice_item_list, null);
        addView(v);

        // Chuẩn bị layout từng item trong danh sách items
        setLayoutItem(R.layout.card_order_invoice_item_content);

        // Chuẩn bị list danh sách item
//        mRecycleView = (RecyclerView) findViewById(R.id.order_invoice_items_list);
//        mRecycleView.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        mRecycleView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void bindItem(View view, CartItem item, int position) {
        CardOrderInvoiceItemContentBinding mBinding = DataBindingUtil.bind(view);
        mBinding.setOrderItem(item);

        EditText edt_qty_to_invoice = (EditText) view.findViewById(R.id.qty_to_invoice);

        actionQtyToInvoice(item, edt_qty_to_invoice);
    }

    private void actionQtyToInvoice(final CartItem item, final EditText qty_to_invoice) {
        qty_to_invoice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int qty_invoiced;
                try {
                    qty_invoiced = Integer.parseInt(qty_to_invoice.getText().toString());
                } catch (Exception e) {
                    qty_invoiced = 0;
                }

                int qty = item.QtyInvoice();
                if (qty_invoiced < 0 || qty_invoiced > qty) {
                    qty_to_invoice.setText(String.valueOf(qty));
                    item.setQuantity(qty);
                } else {
                    item.setQuantity(qty_invoiced);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public List<CartItem> bind2List() {
        return ((OrderInvoiceItemsListController) mController).getSelectedItems();
    }
}
