package com.coffee.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService  productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct( @RequestBody @Valid ProductRequest productRequest){

        return ResponseEntity.ok(productService.createProduct(productRequest));
    }


    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody @Valid List<ProductPurchaseRequest> ProductPurchaseRequest){

        return ResponseEntity.ok(productService.purchaseProducts(ProductPurchaseRequest));
    }
    @GetMapping("/product-id")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Integer productId){

        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){

        return ResponseEntity.ok(productService.findAll());
    }
}
