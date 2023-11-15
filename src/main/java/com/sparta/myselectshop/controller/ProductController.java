package com.sparta.myselectshop.controller;


import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto req, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.createProduct(req, userDetails.getUser());
    }

    @PutMapping("/products/{productId}")
    public ProductResponseDto updateProduct(@PathVariable Long productId, @RequestBody ProductMyPriceRequestDto req){
        return productService.updateProduct(productId, req);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.getProducts(userDetails.getUser());
    }

    @GetMapping("/admin/products")
    public List<ProductResponseDto> getAllProducts(){
        return productService.getAllProducts();
    }

}
