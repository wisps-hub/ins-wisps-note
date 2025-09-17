package com.wisps.filter;

import com.wisps.consts.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import java.util.function.Consumer;

@Slf4j
@Component
public class DefaultGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        long start = System.currentTimeMillis();
        log.info("请求 uri: {}, 开始时间: {}", uri, start);
        //前置逻辑
        Mono<Void> mono = chain.filter(exchange).doFinally(new Consumer<SignalType>() {
            @Override
            public void accept(SignalType signalType) {
                long end = System.currentTimeMillis();
                log.info("请求 uri: {}, 结束时间: {}， 耗时: {}", uri, end, end - start);
            }
        });

        ServerHttpResponse response = exchange.getResponse();
        //后置逻辑
        log.info("请求响应 uri: {}, 响应结果: {}", uri, response.getStatusCode());
        return mono;
    }

    @Override
    public int getOrder() {
        return Consts.DEFAULT_FILTER_ORDER;
    }
}
