package com.magestore.app.lib.controller;

import android.view.View;

import com.magestore.app.lib.model.Model;
import com.magestore.app.lib.observ.GenericState;
import com.magestore.app.lib.panel.AbstractDetailPanel;
import com.magestore.app.lib.panel.AbstractListPanel;
import com.magestore.app.lib.service.ListService;
import com.magestore.app.lib.task.DeleteListTask;
import com.magestore.app.lib.task.InsertListTask;
import com.magestore.app.lib.task.RetrieveListTask;
import com.magestore.app.lib.task.UpdateListTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Task quản lý việc hiển thị một List và một View
 * Created by Mike on 1/9/2017.
 * Magestore
 * mike@trueplus.vn
 */
public class AbstractListController<TModel extends Model>
        extends AbstractController<TModel, AbstractListPanel<TModel>>
        implements ListController<TModel> {

    // tự động chọn item đầu tiên trong danh sách
    boolean mblnAutoChooseFirstItem = true;

    // view chi tiết
    protected AbstractDetailPanel<TModel> mDetailView;

    /**
     * Danh sách dữ liệu chứa Model
     */
    protected List<TModel> mList;

    /**
     * Item được chọn trên danh sách
     */
    protected TModel mSelectedItem;

    /**
     * Service xử lý
     */
    protected ListService<TModel> mListService;

    /**
     * Chỉ định có tự động chọn item đầu tiên trong danh sách sau khi load không
     * @param auto
     */
    public void setAutoSelectFirstItem(boolean auto) {
        mblnAutoChooseFirstItem = auto;
    }

    /**
     * Clear danh sách
     */
    public void clearList() {
        mList = null;
        mView.clearList();
//        mView.notifyDataSetChanged();
    }

    /**
     * Thiết lập danh sách, cho hiển thị lên view
     * @param list
     */
    public void bindList(List<TModel> list) {
        mList = list;
        mView.bindList(mList);
//        mView.notifyDataSetChanged();
    }

    /**
     * Sự kiện lúc chọn được data, cập nhật cả view và dữ liệu
     * @param item
     */
    @Override
    public void bindItem(TModel item) {
        super.bindItem(item);
        setSelectedItem(item);
        if (mDetailView != null)
            mDetailView.bindItem(item);

        // báo cho các observ khác về việc bind item
        GenericState<ListController<TModel>> state = new GenericState<ListController<TModel>>(this, GenericState.DEFAULT_STATE_CODE_ON_SELECT_ITEM);
        if (getSubject() != null) getSubject().setState(state);
    }

    /**
     * Xác định controller xử lý detail
     */
    public void setDetailPanel(AbstractDetailPanel<TModel> detailPanel) {
        mDetailView = detailPanel;
        detailPanel.setController(this);
    }

    /**
     * Đặt list panel để quản lý hiển thị cho panel
     * @param view
     */
    public void setListPanel(AbstractListPanel<TModel> view) {
        setView(view);
    }

    //////////////////////////////////////////////////////////

    /**
     * Thực hiện tải dữ liệu, xác định xem có tự động chọn item đầu tiên không
     *
     * @param blnAutoChooseFirstItem
     */
    public void doLoadData(boolean blnAutoChooseFirstItem) {
        mblnAutoChooseFirstItem = blnAutoChooseFirstItem;
        doRetrieve();
    }

    /**
     * Thực hiện load dữ liệu lúc đầu mở view
     */
    @Override
    public void doRetrieve() {
        // chuẩn bị task load data
        doRetrieve(1, mView.getPageSize());

    }

    /**
     * Thực hiện load dữ liệu lúc đầu mở view
     */
    @Override
    public void doRetrieve(int page, int pageSize) {
        // hiển thị progress
        if (page == 1) mView.showProgress(true);
        else mView.showProgressBottom(true);

        // chuẩn bị task load data
        RetrieveListTask<TModel> task = new RetrieveListTask<TModel>(this);
        task.doExecute(page, pageSize);

        // cho phép layzy loading
        mView.lockLazyLoading(true);
    }

    /**
     * Load data từ background ngầm, các lớp con sẽ nạp chồng hàm này
     * @return
     * @throws Exception
     */
    @Override
    public List<TModel> onRetrieveBackground(int page, int pageSize) throws Exception {
        List<TModel> returnList;
        if (mListService != null) {
            if (pageSize > 0)
                returnList = mListService.retrieve(page, pageSize);
            else returnList = mListService.retrieve();
        } else
            returnList = loadDataBackground();
        return returnList;
    }

    /**
     * Sự kiện sau khi load dữ liệu
     *
     * @param list
     */
    @Override
    public synchronized void onRetrievePostExecute(List<TModel> list) {
        // tắt progress
        mView.hideAllProgressBar();

        // xem chế độ là lazyloading hay k0
        if (mView.haveLazyLoading()) {
            if (mList == null || mList.size() == 0) {
                bindList(list);

                // disable lazyloading nếu đã là cuối danh sách
                mView.enableLazyLoading(!(list == null || list.size() < mView.getPageSize()));
                mView.lockLazyLoading(false);

                // Chọn item đầu tiên
                if (mblnAutoChooseFirstItem && mList != null && mDetailView != null && mList.size() > 0)
                    bindItem(mList.get(0));
            } else {
                // disable lazyloading nếu đã là cuối danh sách
                if (list == null || list.size() < mView.getPageSize())
                    mView.enableLazyLoading(false);

                // bổ sung thêm vào list theo lazyloading
                mList.addAll(list);
                mView.insertListAtLast(list);
                mView.lockLazyLoading(false);
            }
        } else {
            bindList(list);

            // Chọn item đầu tiên
            if (mblnAutoChooseFirstItem && mList != null && mDetailView != null && mList.size() > 0)
                bindItem(mList.get(0));
        }

        // báo cho các observ khác về việc bind item
        GenericState<ListController<TModel>> state = new GenericState<ListController<TModel>>(this, GenericState.DEFAULT_STATE_CODE_ON_RETRIEVE);
        if (getSubject() != null) getSubject().setState(state);
    }

    /**
     * Load data từ background ngầm, các lớp con sẽ nạp chồng hàm này
     * Bản cũ không dùng nữa
     *
     * @return
     * @throws Exception
     */
    protected List<TModel> loadDataBackground(Void... params) throws Exception {
        return null;
    }

    /**
     * Kích hoạt update model trong list
     */
    @Override
    public void doUpdate(TModel oldModel, TModel newModels) {
        // chuẩn bị task load data
        UpdateListTask<TModel> task = new UpdateListTask<TModel>(this);
        task.execute(oldModel, newModels);
    }

    /**
     * Overider hàm này để xử lý nghiệp vụ update trên một thread khác
     *
     * @throws Exception
     */
    @Override
    public boolean onUpdateBackGround(TModel oldModel, TModel newModels) throws Exception {
        if (mListService != null) {
            return mListService.update(oldModel, newModels);
        }
        return false;
    }

    /**
     * Cập nhật thành công
     *
     * @param success
     */
    @Override
    public void onUpdatePostExecute(Boolean success, TModel oldModel, TModel newModels) {
        if (success) {
            mView.updateModel(oldModel, newModels);

            // báo cho các observ khác về việc bind item
            GenericState<ListController<TModel>> state = new GenericState<ListController<TModel>>(this, GenericState.DEFAULT_STATE_CODE_ON_UPDATE);
            if (getSubject() != null) getSubject().setState(state);
        }
    }

    /**
     * Kích hoạt việc xóa 1 item
     */
    @Override
    public void doDelete(TModel... models) {
        // chuẩn bị task load data
        DeleteListTask<TModel> task = new DeleteListTask<TModel>(this);
        task.doExecute(models);
    }

    /**
     * Overider hàm này để xử lý nghiệp vụ insert
     *
     * @throws Exception
     */
    @Override
    public boolean onDeleteBackGround(TModel... models) throws Exception {
        if (mListService != null)
            return mListService.delete(models);
        return false;
    }

    /**
     * Xóa thành công
     *
     * @param success
     */
    @Override
    public void onDeletePostExecute(Boolean success, TModel... models) {
        if (success) {
            mView.deleteList(models);

            // báo cho các observ khác về việc bind item
            GenericState<ListController<TModel>> state = new GenericState<ListController<TModel>>(this, GenericState.DEFAULT_STATE_CODE_ON_DELETE);
            if (getSubject() != null) getSubject().setState(state);
        }
    }

    /**
     * Kích hoạt Thực hiện chèn, tạo mới 1 item
     */
    @Override
    public void doInsert(TModel... models) {
        // chuẩn bị task load data
        InsertListTask<TModel> task = new InsertListTask<TModel>(this);
        task.doExecute(models);
    }

    /**
     * Overider hàm này để xử lý nghiệp vụ insert
     * @param params
     * @throws Exception
     */
    @Override
    public boolean onInsertBackground(TModel... params)
            throws Exception {
        if (mListService != null)
            return mListService.insert(params);
        return false;
    }

    /**
     * Insert thành công
     * @param success
     */
    @Override
    public void onInsertPostExecute(Boolean success, TModel... models) {
        if (success) {
//            mView.notifyDataSetChanged();
            mView.insertListAtLast(models);

            // báo cho các observ khác về việc bind item
            GenericState<ListController<TModel>> state = new GenericState<ListController<TModel>>(this, GenericState.DEFAULT_STATE_CODE_ON_INSERT);
            if (getSubject() != null) getSubject().setState(state);
        }
    }


    /**
     * Sự kiện lúc canceled load data
     */
    public void onCancelledLoadData(Exception exp) {
        onCancelledBackground(exp);
    }


    /**
     * Chỉ định 1 item được chọn về mặt dataset, k0 có update view
     * @param model
     */
    public void setSelectedItem(TModel model) {
        mSelectedItem = model;
    }

    /**
     * Trả về item đã được chọn trên danh sách
     * @return
     */
    public TModel getSelectedItem() {
        return mSelectedItem;
    }

    /**
     * Trả lại danh sách nhiều item được chọn
     * @return
     */
    public List<TModel> getSelectedItems() {
        return mList;
    }

    /**
     * Chỉ định tập hợp các item được chọn, k0 thay đổi view, chỉ về mặt dữ liệu
     * @param models
     */
    public void setSelectedItem(List<TModel> models) {
        setSelectedItem(models.get(0));
    }

    /**
     * Chỉ định list service
     * @param service
     */
    @Override
    public void setListService(ListService<TModel> service) {
        mListService = service;
    }

    /**
     * Trả về list service xử lý thao tác
     * @return
     */
    @Override
    public ListService<TModel> getListService() {
        return mListService;
    }

    /**
     * Khởi tạo mới 1 model
     * @return
     */
    @Override
    public TModel createItem() {
        return getListService().create();
    }

    /**
     * Load thêm dữ liệu vào list theo lazy loading
     * @param page
     */
    @Override
    public void doRetrieveMore(int page) {
        doRetrieve(page, mView.getPageSize());
    }

    /**
     * Bật tắt, hiển thị detail
     * @param show
     */
    @Override
    public void doShowDetailPanel(boolean show) {
        if (mDetailView != null) mDetailView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Hiển thị panel list
     * @param show
     */
    @Override
    public void doShowListPanel(boolean show) {
        if (mView != null) mView.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    /**
     * Cho phép lazy loading
     * @param enable
     */
    @Override
    public void enableLazyLoading(boolean enable) {
        if (mView != null) mView.enableLazyLoading(enable);
    }

    /**
     * Hiển thị kết quả tìm kiếm trên list
     * @param model
     */
    @Override
    public void displaySearch(TModel model) {
        List<TModel> listModel = new ArrayList<TModel>();
        listModel.add(model);
        mView.publishSearchList(listModel);
    }

    /**
     * Hiển thị kết quả tìm kiếm trên list
     * @param listModel
     */
    @Override
    public void displaySearch(List<TModel> listModel) {
        mView.publishSearchList(listModel);
    }

    /**
     * Giấu hết quả tìm kiếm trên list
     */
    @Override
    public void hideSearch() {
        mView.closeSearchList();
    }
}