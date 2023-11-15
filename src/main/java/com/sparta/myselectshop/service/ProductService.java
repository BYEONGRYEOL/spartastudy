package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public static final int MY_PRICE_MIN = 100;
    public ProductResponseDto createProduct(ProductRequestDto req) {
        Product product = productRepository.save(new Product(req));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductMyPriceRequestDto req) {
        int myPrice = req.getMyprice();
        if(myPrice < MY_PRICE_MIN){
            throw new IllegalArgumentException("최소 " + MY_PRICE_MIN + "원 이상으로 검색");
        }
        Product product = productRepository.findById(productId).orElseThrow(()-> new NullPointerException("해당 상품 없음"));
        product.update(req); // dirty check

        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(()-> new NullPointerException("자동 검색중 상품 조회 실패"));
        product.update(itemDto);
    }
}
