package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ProductCanNotBeDeletedException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.OrderItemRepository;
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

import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository repository;
    private final ProductInventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;
@Autowired
    public ProductVariantServiceImpl(ProductRepository productRepository, ProductVariantRepository repository, ProductInventoryRepository inventoryRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.repository = repository;
    this.inventoryRepository = inventoryRepository;
    this.orderItemRepository = orderItemRepository;
}

    @Override
    public void createVariant(ProductVariantRequest request) {
//       creating variant
        ProductVariant variant = new ProductVariant();
        Product productId = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product ID not found"));
        variant.setProduct(productId);
        variant.setVariantName(request.getVariantName());
        variant.setVariantValue(request.getVariantValue());
        variant.setPrice(request.getPrice());
        repository.save(variant);
//        creating inventory with variant

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
    public List<ProductVariantResponse> getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir) {
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
    public Object getById(Long variantId) {
        return repository.findById(variantId)
                .orElseThrow(()->new ResourceNotFoundException("invalid variant Id"));    }

    @Override
    public Object update(ProductVariantRequest request, Long variantId) {
    ProductVariant variant = repository.findById(variantId).orElseThrow(()->new ResourceNotFoundException("invalid variant ID"));
    if (request.getProductId()!=null){
            Product product = productRepository.findById(request.getProductId()).orElseThrow(()->new ResourceNotFoundException("invalid product ID"));
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
        ProductVariant variant = repository.findById(variantId).orElseThrow(() -> new ResourceNotFoundException("invalid variant id"));
        ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(variant);
        if (orderItemRepository.findOrderItemByProductVariant(variant).isPresent()){
            throw new ProductCanNotBeDeletedException("this variant cannot be deleted since it already exists in an order item");
        }
        else {
            inventoryRepository.delete(inventory);
            repository.delete(variant);
        }
    }

    @Override
    public List<ProductVariantResponse> findVariantByProductId(Long id) {
            Product product=productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("product id not found"));
            List<ProductVariant> productVariants=repository.getAllVariantsByProductId(id);
            return productVariants.stream().map(ProductVariantResponse::new).toList();
    }


}
