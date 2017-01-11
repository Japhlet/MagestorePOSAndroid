package com.magestore.app.pos.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.magestore.app.lib.adapterview.AdapterViewAnnotiation;
import com.magestore.app.lib.model.Model;
import com.magestore.app.lib.parse.ParseModel;
import com.magestore.app.util.NotFoundObject;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Mike on 12/22/2016.
 * Magestore
 * mike@trueplus.vn
 * TODO: Add a class header comment!
 */

public class AbstractModel implements Model, ParseModel {
    protected String id;
    protected HashMap<String, Object> mRefer;

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setRefer(String key, Object value) {
        if (mRefer == null) mRefer = new HashMap<String, Object>();
        mRefer.put(key, value);
    }

    @Override
    public Object getRefer(String key) {
        if (mRefer == null) return null;
        return mRefer.get(key);
    }

    @Override
    public boolean setValue(String key, Object value) {
        Class clazz = this.getClass();
        if (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(this, value);
                return true;
            } catch (NoSuchFieldException e) {
                return false;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    @Override
    public Object getValue(String key) {
        Class clazz = this.getClass();
        if (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(key);
                AdapterViewAnnotiation ann3 = field.getAnnotation(AdapterViewAnnotiation.class);
                field.setAccessible(true);
                return field.get(this);
            } catch (NoSuchFieldException e) {
                return NotFoundObject.getInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

    @Override
    public Object getValue(String key, Object defaultValue) {
        Object value = getValue(key);
        if (value == null) return defaultValue;
        else return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Viết ra chuỗi json của object lên parcel
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Gson gson = new Gson();
        String str = gson.toJson(this);
        parcel.writeString(str);
    }

    /**
     * Đọc chuỗi json của object từ parcel, chuyển thành object
     * @param parcel
     */
    public void readFromParcel(Parcel parcel) {
        Gson gson = new Gson();
        String str = parcel.readString();
        gson.fromJson(str, this.getClass());
    }

    /**
     * Khởi tạo json của object
     */
    public static final Parcelable.Creator<AbstractModel> CREATOR =
            new Parcelable.Creator<AbstractModel>() {
                @Override
                public AbstractModel createFromParcel(Parcel source) {
                    AbstractModel entity = new AbstractModel();
                    entity.readFromParcel(source);
                    return entity;
                }

                @Override
                public AbstractModel[] newArray(int size) {
                    return new AbstractModel[size];
                }
            };
}
