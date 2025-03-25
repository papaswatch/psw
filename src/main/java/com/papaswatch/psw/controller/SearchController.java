package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.dto.product.Product;
import com.papaswatch.psw.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/{productId}")
    public Response<Product> getProductById(@PathVariable long productId) {
        Product response = searchService.getProductById(productId);
        return Response.ok(response);
    }

    @GetMapping("/simple/list")
    public void getProductList() {
        searchService.getProductList();
    }

    @GetMapping("/discount")
    public void getDiscountProductItemList() {
        searchService.getDiscountProductItemList();
    }

    @GetMapping("/trend")
    public void getSearchTrend() {
        searchService.getSearchTrend();
    }

    @GetMapping("/qna")
    public void getProductQNAList() {
        searchService.getProductQNAList();
    }

    @GetMapping("/qna/{productId}")
    public void getProductQNA(@PathVariable long productId) {
        searchService.getProductQNA(productId);
    }

    @GetMapping("/qna-reply/{productId}")
    public void getReplyProductQNA(@PathVariable long productId) {
        searchService.getReplyProductQNA(productId);
    }
}
