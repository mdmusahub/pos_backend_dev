package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;

import com.sm.backend.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
   private final ProductRepository productRepository;
   private final ProductVariantRepository variantRepository;
   private final ProductInventoryRepository inventoryRepository;
@Autowired
    public ProductInventoryServiceImpl(ProductRepository productRepository, ProductVariantRepository variantRepository, ProductInventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
    this.variantRepository = variantRepository;
    this.inventoryRepository = inventoryRepository;
}

    @Override
    public void register(ProductInventoryRequest request) {
        ProductInventory inventory = new ProductInventory();
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("id not found"));
        inventory.setProduct(product);
        inventory.setLocation(request.getLocation());
        inventory.setQuantity(request.getQuantity());
        inventory.setLastUpdated(request.getLastUpdated());
        inventory.setProductVariant(variantRepository.findById(request.getVariantId())
                .orElseThrow(()->new ResourceNotFoundException("invalid variant id")));
    inventoryRepository.save(inventory);
    }

    @Override
    public Object getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir) {
        Sort sort = null;

        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortby).ascending();
        } else {
            sort = Sort.by(sortby).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<ProductInventory> all = inventoryRepository.findAll(pageable);
        return all.stream().map(ProductInventoryResponse::new).toList();
    }

    @Override
    public Object findById(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(()->new RuntimeException("invald inventory ID"));
    }

    @Override
    public void delete(Long inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }


    @Override
    public Object update(ProductInventoryRequest request, Long inventoryId) {
        ProductInventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> new RuntimeException("id not found"));

        if (request.getQuantity()!=null) {
            inventory.setQuantity(request.getQuantity());
        }
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("id not found"));
        if (request.getProductId()!=null) {
            inventory.setProduct(product);
        }
        if (request.getLocation()!=null) {
            inventory.setLocation(request.getLocation());
        }
        if (request.getLastUpdated()!=null ){
            inventory.setLastUpdated(request.getLastUpdated());
        }

  return inventoryRepository.save(inventory);
}


}
