package com.tomasky.doms.support.util;

import com.tomasky.doms.common.DomsConstants;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2015/5/13.
 */
public class JsonModel extends ExtendedModelMap {

    //结果json字符串
    private Object data;
    private String status;
    private String message;

    public static Model getModel(Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute(DomsConstants.STATUS, DomsConstants.ERROR);
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError err : errors) {
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
        } else {

            model.addAttribute(DomsConstants.STATUS, DomsConstants.SUCCESS);
        }
        return model;

    }

    public String getMessage() {
        return (String) this.get("message");
    }

    public void setMessage(String message) {
        this.addAttribute("message", message);
    }


    public boolean isSuccess() {
        return (Boolean) this.get("status");
    }

    public void setSuccess(boolean success) {
        this.addAttribute("status", success);
    }



    public JsonModel(boolean success, String message) {
        this.addAttribute("status", success);
        this.addAttribute("message", message);
    }

    public JsonModel(String status, String message) {
        this.addAttribute("status", status);
        this.addAttribute("message", message);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.addAttribute("data", data);
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
