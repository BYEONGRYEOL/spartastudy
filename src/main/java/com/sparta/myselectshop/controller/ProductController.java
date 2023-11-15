package com.sparta.myselectshop.controller;


import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<ProductResponseDto> getProducts(
            // page, size,  sortBy, isAsc 4개의 요청이 필요하다
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.getProducts(userDetails.getUser(),
                page-1, size, sortBy, isAsc); // page는 서버단에선 0부터 시작
    }

    @GetMapping("/admin/products")
    public List<ProductResponseDto> getAllProducts(){
        return productService.getAllProducts();
    }

}
