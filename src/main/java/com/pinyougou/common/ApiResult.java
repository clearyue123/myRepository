package com.pinyougou.common;

public class ApiResult extends BaseResult{
	public ApiResult(int code, String message, Object data) {
        super(code, message, data);
    }

    public ApiResult(ApiResultConstant apiResultConstant, Object data) {
        super(apiResultConstant.getCode(), apiResultConstant.getMessage(), data);
    }
}
