package com.papaswatch.psw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    @PostMapping("/add")
    public void addProduct() {}

    @PutMapping("/edit")
    public void editProduct() {}

    @DeleteMapping("/delete")
    public void deleteProduct() {}
}
