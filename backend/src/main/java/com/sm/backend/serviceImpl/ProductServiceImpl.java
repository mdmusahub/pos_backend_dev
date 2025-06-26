package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ProductAlreadyExistsException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductInventoryRepository inventoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository, ProductVariantRepository variantRepository, ProductInventoryRepository inventoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.variantRepository = variantRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void register(ProductRequest request) {
//       creating products
        Optional<Product> productByProductName = repository.findProductByProductName(request.getProductName());
        if (productByProductName.isPresent()) {
            throw new ProductAlreadyExistsException("the product " + request.getProductName() + " already exists");
        } else {
            Product product = new Product();
            product.setProductName(request.getProductName());
            product.setSku(request.getSku());
            product.setDescription(request.getDescription());
            product.setCreatedAt(request.getCreatedAt());
            product.setUpdatedAt(request.getUpdatedAt());
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category ID not found"));
            product.setCategory(category);
            repository.save(product);

//        creating variants
            for (ProductVariantRequest request1 : request.getVariantRequests()) {
                ProductVariant variant = new ProductVariant();
                variant.setVariantName(request1.getVariantName());
                variant.setVariantValue(request1.getVariantValue());
                variant.setPrice(request1.getPrice());
                variant.setProduct(repository.findById(product.getProductId()).orElseThrow(() -> new ResourceNotFoundException("invalid id")));
                variantRepository.save(variant);
//            creating inventories
                ProductInventory inventory = new ProductInventory();
                inventory.setQuantity(request1.getInventoryRequest().getQuantity());
                inventory.setLocation(request1.getInventoryRequest().getLocation());
                inventory.setLastUpdated(request1.getInventoryRequest().getLastUpdated());
                inventory.setProduct(variant.getProduct());
                inventory.setProductVariant(variant);
                inventoryRepository.save(inventory);
            }
        }

    }

    @Override
    public Object findById(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid Product ID"));
    }

    @Override
    public Object findall(Integer pageNumber, Integer pageSize, String sortby, String sortDir) {
        Sort sort = null;

        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortby).ascending();
        } else {
            sort = Sort.by(sortby).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = repository.findAll(pageable);
        return all.stream().map(ProductResponse::new).toList();


    }

    @Override
    public Object updateDetails(ProductRequest request, Long productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product ID not found"));

        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getSku() != null) {
            product.setSku(request.getSku());
        }

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("invalid category ID"));
            product.setCategory(category);
        }

        return repository.save(product);
    }

    @Override
    public void delete(Long productId) {
        if (repository.existsById(productId)) {
            repository.deleteById(productId);
        } else {
            throw new RuntimeException("Id not Found");
        }
    }
}
