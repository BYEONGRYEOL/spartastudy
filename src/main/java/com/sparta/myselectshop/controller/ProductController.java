package com.sparta.myselectshop.controller;


import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto req){
        return productService.createProduct(req);
    }

    @PutMapping("/products/{productId}")
    public ProductResponseDto updateProduct(@PathVariable Long productId, @RequestBody ProductMyPriceRequestDto req){
        return productService.updateProduct(productId, req);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getProducts(){
        return productService.getProducts();
    }


}
