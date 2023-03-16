package com.x14n.common.core.domain;

import com.x14n.common.constant.HttpStatus;
import com.x14n.common.utils.StringUtils;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    public AjaxResult() {
    }

    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }


    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
