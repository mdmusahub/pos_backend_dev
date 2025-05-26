package com.sm.backend.serviceImpl;

import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
@Autowired
    public ProductVariantServiceImpl(ProductRepository productRepository, ProductVariantRepository variantRepository) {
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public void addVariant(ProductVariantRequest request) {
        ProductVariant variant = new ProductVariant();
        variant.setProductId(productRepository.findById(request.getProductId())
                .orElseThrow(()->new RuntimeException("error")));
        variant.setVariantName(request.getVariantName());
        variant.setVariantValue(request.getVariantValue());
        variant.setPrice(request.getPrice());
        variantRepository.save(variant);
    }

    @Override
    public Object getAllVariants() {
        List<ProductVariant> variant = variantRepository.findAll();
        List<ProductVariantResponse> variantResponses = variant.stream()
                .map(ProductVariantResponse::new).toList();
        return variantResponses;
    }

    @Override
    public Object GetById(Long id) {
        ProductVariant variant=variantRepository.findById(id).
                orElseThrow(()->new RuntimeException("error"));
        return new ProductVariantResponse(variant);
    }

    @Override
    public Object updateById(Long id, ProductVariantRequest request) {
        ProductVariant variant=variantRepository.findById(id)
                .orElseThrow(()->new RuntimeException("error"));
        if(request.getProductId()!=null){
            variant.setProductId(productRepository.findById(request.getProductId())
                    .orElseThrow(()->new RuntimeException("error")));
        }
        if(request.getVariantName()!=null){
            variant.setVariantName(request.getVariantName());
        }
        if(request.getVariantValue()!=null){
            variant.setVariantValue(request.getVariantValue());
        }
        if(request.getPrice()!=null){
            variant.setPrice(request.getPrice());
        }
        return variantRepository.save(variant);
    }

    @Override
    public void delete(Long id) {
        ProductVariant variant=variantRepository.findById(id).
                orElseThrow(()->new RuntimeException("error"));
        variantRepository.delete(variant);
    }
}
