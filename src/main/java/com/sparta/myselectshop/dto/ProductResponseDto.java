package com.sparta.myselectshop.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sparta.myselectshop.entity.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;
    private String link;
    private String image;
    private int lprice;
    private int myprice;

    private List<FolderResponseDto> productFolderList = new ArrayList<>();
    // Entity끼리 연관관계를 맺고 있으므로, Dto로 변경해줄 때도 Dto 생성자에 전달되는 Entity에서 연관Entity 정보도 불러와야한다.

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.link = product.getLink();
        this.image = product.getImage();
        this.lprice = product.getLprice();
        this.myprice = product.getMyprice();
        productFolderList = product.getProductFolderList().stream().map( productFolder -> new FolderResponseDto(productFolder.getFolder())).collect(
            Collectors.toList());
    }
}