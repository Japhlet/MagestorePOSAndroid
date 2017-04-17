package com.magestore.app.pos.model.exception;

import com.magestore.app.lib.model.exception.MessageException;
import com.magestore.app.pos.model.PosAbstractModel;

/**
 * Quản lý các message từ server khi có lỗi
 * Created by Mike on 1/13/2017.
 * Magestore
 * mike@trueplus.vn
 */

public class PosMessageException extends PosAbstractModel implements MessageException {
    String code;
    String message;
    String trace;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getTrace() {
        return null;
    }
}
