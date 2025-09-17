package com.wisps.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 通用出参
 */
@Setter
@Getter
@ToString
public class BaseResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean success;

    private String respCode;

    private String respMsg;

    public BaseResp() {
    }

    public BaseResp(Boolean success, String respCode, String respMsg) {
        this.success = success;
        this.respCode = respCode;
        this.respMsg = respMsg;
    }
}
