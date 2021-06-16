package com.security.im;

import com.security.im.common.dto.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:Guanyp@hiwitech.com">Guanyp</a>
 * @date 2021/3/24
 */
@FeignClient(value = "imcenter", fallback = ImUserFallBack.class)
public interface ImUserClient {

    @GetMapping("user/search/{key}")
    ResponseObject search(@PathVariable String key);
}
