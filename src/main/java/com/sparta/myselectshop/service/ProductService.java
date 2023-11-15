package com.sparta.myselectshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.ProductFolder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.FolderRepository;
import com.sparta.myselectshop.repository.ProductFolderRepository;
import com.sparta.myselectshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	public static final int MY_PRICE_MIN = 100;
	private final ProductRepository productRepository;
	private final FolderRepository folderRepository;
	private final ProductFolderRepository productFolderRepository;
	public ProductResponseDto createProduct(ProductRequestDto req, User user) {
		// Product 엔티티와 User가 연관관계를 맺고 있다. user도 추가해주자.
		Product product = productRepository.save(new Product(req, user));
		return new ProductResponseDto(product);
	}

	@Transactional
	public ProductResponseDto updateProduct(Long productId, ProductMyPriceRequestDto req) {
		int myPrice = req.getMyprice();
		if (myPrice < MY_PRICE_MIN) {
			throw new IllegalArgumentException("최소 " + MY_PRICE_MIN + "원 이상으로 검색");
		}
		Product product = productRepository.findById(productId).orElseThrow(() -> new NullPointerException("해당 상품 없음"));
		product.update(req); // dirty check

		return new ProductResponseDto(product);
	}

	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getProducts(User user, int page, int size, String sortBy, boolean isAsc) {
		UserRoleEnum userRoleEnum = user.getRole();
		Pageable pageable = PageService.getPageable(page, size, sortBy, isAsc);
		Page<Product> productList = null;
		if (userRoleEnum == UserRoleEnum.USER) {
			productList = productRepository.findAllByUser(user, pageable);
		}
		if (userRoleEnum == UserRoleEnum.ADMIN) {
			productList = productRepository.findAll(pageable);
		}
		return productList.map(ProductResponseDto::new); // Page<> 에서 제공하는 map
	}

	@Transactional
	public void updateBySearch(Long id, ItemDto itemDto) {
		Product product = productRepository.findById(id).orElseThrow(() -> new NullPointerException("자동 검색중 상품 조회 실패"));
		product.update(itemDto);
	}

	public List<ProductResponseDto> getAllProducts() {
		return productRepository.findAll().stream().map(ProductResponseDto::new).collect(Collectors.toList());
	}

	public void addFolder(Long productId, Long folderId, User user) {
		Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);

		Folder folder = folderRepository.findById(folderId).orElseThrow(RuntimeException::new);

		if(!user.getId().equals(product.getUser().getId())
			|| !user.getId().equals(folder.getUser().getId())){
			throw new IllegalArgumentException("관심상품, 폴더 가 회원께어ㅏ님");
		}

		// 해당상품이 이미 해당 폴더에 추가되어잇는가?
		Optional<ProductFolder> alreadyExistProductFolder = productFolderRepository.findByProductAndFolder(product, folder);
		
		if(alreadyExistProductFolder.isPresent()){
			throw new IllegalArgumentException("중복된 폴더");
		}

		productFolderRepository.save(new ProductFolder(product, folder));
	}
}
