package com.example.demo.service;

import com.example.demo.model.dto.request.ProductList;
import com.example.demo.model.dto.request.ProductQuantity;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private ProductsRepository productsRepository;

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getProducts() {
        return productsRepository.findAll();
    }

    public void updateProduct(ProductList productList) {
        for(ProductQuantity productQuantity : productList.getProducts()){
            Product product = productsRepository.findById(productQuantity.getProductId())
                    .orElseThrow(() -> new RuntimeException("product not found"));
            product.setQuantity(product.getQuantity() - productQuantity.getQuantity());
            productsRepository.save(product);
        }
    }
}
