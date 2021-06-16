package com.security.im;

import com.security.im.common.dto.ResponseObject;
import com.security.im.common.dto.ResultCodeEnum;

/**
 * @author <a href="mailto:Guanyp@hiwitech.com">Guanyp</a>
 * @date 2021/3/24
 */
public class ImUserFallBack implements ImUserClient{
    @Override
    public ResponseObject search(String key) {
        return ResponseObject.failed(ResultCodeEnum.ERROR_UN_KNOW);
    }
}
