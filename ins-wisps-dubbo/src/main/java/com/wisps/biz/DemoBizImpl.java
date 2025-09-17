package com.wisps.biz;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wisps.dto.DemoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DemoBizImpl implements DemoBiz{

    @SentinelResource(value = "create", blockHandler = "createFallback")
    @Override
    public DemoDto create(Long id) {
        return new DemoDto(id, "name", "desc");
    }

    public DemoDto createFallback(Long id, BlockException e){
        log.info("getByIdFallback异常, 交给SentinelResourceAspect处理", e);
        return new DemoDto(id, "block_name", "block_desc");
    }
}
