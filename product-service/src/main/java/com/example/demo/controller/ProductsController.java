package com.example.demo.controller;


import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productsService.getProducts());
    }

    @GetMapping("/get-categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }


}
