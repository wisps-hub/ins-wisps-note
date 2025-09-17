package com.wisps.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson2.JSON;
import com.wisps.resp.BaseResp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * web接口限流异常处理器
 */
@Component
public class WispsBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        BaseResp baseResp = new BaseResp(false, "500", "请求过于频繁，请稍后再试");
        writer.write(JSON.toJSONString(baseResp));
        writer.flush();
        writer.close();
    }
}
