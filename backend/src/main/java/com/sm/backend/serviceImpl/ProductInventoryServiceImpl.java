package com.sm.backend.serviceImpl;

import com.sm.backend.model.ProductInventory;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.service.ProductInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
    private final ProductInventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;

    public ProductInventoryServiceImpl(ProductInventoryRepository inventoryRepository, ProductVariantRepository variantRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addInventory(ProductInventoryRequest request) {
        ProductInventory inventory=new ProductInventory();
        inventory.setProductId(productRepository.findById(request.getProductId()).
                orElseThrow(()->new RuntimeException("message")));
        inventory.setVariantId(variantRepository.FindAllVariantsByProductId(request.getProductId()));
        inventory.setQuantity(request.getQuantity());
        inventory.setLocation(request.getLocation());
        inventory.setLastUpdated(request.getLastUpdated());
        inventoryRepository.save(inventory);
    }

    @Override
    public Object getAll() {
        List<ProductInventory> inventory = inventoryRepository.findAll();
    List<ProductInventoryResponse> responses = inventory.stream().map(ProductInventoryResponse::new).toList();
    return responses;
    }

    @Override
    public Object getById(Long id) {
        ProductInventory inventory = inventoryRepository
                .findById(id).orElseThrow(()->new RuntimeException("error"));
        return new ProductInventoryResponse(inventory);
    }

    @Override
    public Object updateById(Long id,ProductInventoryRequest request) {
        ProductInventory inventory=inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("error"));
        if(request.getQuantity()!=null){
            inventory.setQuantity(request.getQuantity());
        }
        if(request.getLastUpdated()!=null){
            inventory.setLastUpdated(request.getLastUpdated());
        }
        if(request.getLocation()!=null){
            inventory.setLocation(request.getLocation());
        }
        if(request.getProductId()!=null){
            inventory.setProductId(productRepository.findById(request.getProductId())
                    .orElseThrow(()->new RuntimeException("error")));
        }
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteById(Long id) {
        ProductInventory inventory=inventoryRepository.findById(id).get();
        inventoryRepository.delete(inventory);
    }

}
