package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.request.ProductVariantRequest;

import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository repository;
    private final ProductInventoryRepository inventoryRepository;
@Autowired
    public ProductVariantServiceImpl(ProductRepository productRepository, ProductVariantRepository repository, ProductInventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.repository = repository;
    this.inventoryRepository = inventoryRepository;
}

    @Override
    public void register(ProductVariantRequest request) {
//       creating variant
        ProductVariant variant = new ProductVariant();
        Product productId = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product ID not found"));
        variant.setProduct(productId);
        variant.setVariantName(request.getVariantName());
        variant.setVariantValue(request.getVariantValue());
        variant.setPrice(request.getPrice());
        repository.save(variant);
//        creating inventory

            ProductInventory inventory = new ProductInventory();
            inventory.setQuantity(request.getInventoryRequest().getQuantity());
            inventory.setLocation(request.getInventoryRequest().getLocation());
            inventory.setLastUpdated(request.getInventoryRequest().getLastUpdated());
//            ProductVariant variant1 = repository.findById(request.getInventoryRequest().getVariantId())
//                    .orElseThrow(() -> new ResourceNotFoundException("invalid variant ID"));
            inventory.setProduct(variant.getProduct());
            inventory.setProductVariant(variant);
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
        Page<ProductVariant> all = repository.findAll(pageable);
        return all.stream().map(ProductVariantResponse::new).toList();
    }

    @Override
    public Object findById(Long variantId) {
        return repository.findById(variantId)
                .orElseThrow(()->new ResourceNotFoundException("invalid variant Id"));    }

    @Override
    public Object updateVariant(ProductVariantRequest request, Long variantId) {
    ProductVariant variant = repository.findById(variantId).orElseThrow(()->new RuntimeException("id not found"));
    if (request.getProductId()!=null){
            Product product = productRepository.findById(request.getProductId()).orElseThrow(()->new ResourceNotFoundException("product ID not found"));
            variant.setProduct(product);
    }

if (request.getVariantName()!=null){
variant.setVariantName(request.getVariantName());
}
if (request.getVariantValue()!=null){
    variant.setVariantValue(request.getVariantValue());
}
if (request.getPrice()!=null){
    variant.setPrice(request.getPrice());
}
return repository.save(variant);
}

    @Override
    public void delete(Long variantId) {
        repository.deleteById(variantId);
    }


}
