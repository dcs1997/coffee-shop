package com.coffee.ecommerce.product;

import com.coffee.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return  productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequest) {
        List<Integer> productIdList = productPurchaseRequest
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        List<Product> productList = productRepository.findAllByIdInOrderById(productIdList);

        if(productIdList.size()!= productList.size()){
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var storedRequest = productPurchaseRequest
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for(int i=0; i<productList.size(); i++){
            var product = productList.get(i);
            var productRequest = storedRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Not enough stock for product ID: " + product.getId());
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - productRequest.quantity());
            productRepository.save(product);

            purchasedProducts.add(productMapper.toProductPurchaseResponse(product,productRequest.quantity() ));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper:: toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id:: "+ productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper:: toProductResponse)
                .collect(Collectors.toList());
    }
}
