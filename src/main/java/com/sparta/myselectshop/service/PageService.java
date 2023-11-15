package com.sparta.myselectshop.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageService {

    public static Pageable getPageable(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC; // 오름차순 Sort.Direction
        Sort sort = Sort.by(direction, sortBy); //
        return PageRequest.of(page, size, sort);
    }
}
