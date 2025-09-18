package com.adnan.ecommerce.product;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return productRepo.save(product).getId();
    }

//    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
//        var productsIds = request
//                .stream()
//                .map(ProductPurchaseRequest::productId)
//                .toList();
//        var storedProducts = productRepo.findAllByIdInOrderById(productsIds);
//        return null;
//    }

    public ProductResponse findById(Integer productId) {
        return productRepo.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return productRepo.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
