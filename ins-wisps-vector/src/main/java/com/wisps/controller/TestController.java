package com.wisps.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private VectorStore vectorStore;

    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(@RequestParam(name = "msg") String msg) {
        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg),
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));

        System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));

        return embeddingResponse;
    }

    @GetMapping("/save")
    public void save(){
        List<Document> documents = List.of(
                new Document("i study LLM"),
                new Document("i love java"),
                new Document("i love c++")
        );
        vectorStore.add(documents);
    }

    @GetMapping("/search")
    public List search(@RequestParam(name = "msg") String msg){
        SearchRequest searchRequest = SearchRequest.builder()
                .query(msg)
                .topK(2)
                .build();

        List<Document> list = vectorStore.similaritySearch(searchRequest);

        System.out.println(list);

        return list;
    }


}