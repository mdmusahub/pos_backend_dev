package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ProductAlreadyExistsException;
import com.sm.backend.exceptionalHandling.ProductCanNotBeDeletedException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.*;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.*;
import com.sm.backend.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductInventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository, ProductVariantRepository variantRepository, ProductInventoryRepository inventoryRepository, OrderItemRepository orderItemRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.variantRepository = variantRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void createProduct(ProductRequest request) {
//       creating products
        Optional<Product> productByProductName = repository.findProductByProductName(request.getProductName());
        if (productByProductName.isPresent()) {
            throw new ProductAlreadyExistsException("the product " + request.getProductName() + " already exists");
        } else {
            Product product = new Product();
            product.setProductName(request.getProductName());
            product.setSku(request.getSku());
            product.setDescription(request.getDescription());
//            product.setCreatedAt(request.getCreatedAt());
//            product.setUpdatedAt(request.getUpdatedAt());
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Invalid Category ID"));
            product.setCategory(category);
            repository.save(product);

//        creating variants
           if(request.getVariantRequests().isEmpty()==false) {
               for (ProductVariantRequest request1 : request.getVariantRequests()) {
                   ProductVariant variant = new ProductVariant();
                   variant.setVariantName(request1.getVariantName());
                   variant.setVariantValue(request1.getVariantValue());
                   variant.setPrice(request1.getPrice());
                   variant.setProduct(repository.findById(product.getProductId()).orElseThrow(() -> new ResourceNotFoundException("invalid product ID")));
                   variantRepository.save(variant);
//            creating inventories
                   ProductInventory inventory = new ProductInventory();
                   inventory.setQuantity(request1.getInventoryRequest().getQuantity());
                   inventory.setLocation(request1.getInventoryRequest().getLocation());
//                   inventory.setLastUpdated(request1.getInventoryRequest().getLastUpdated());
                   inventory.setProduct(variant.getProduct());
                   inventory.setProductVariant(variant);
                   inventoryRepository.save(inventory);
               }
           }
        }

    }

    @Override
    public ProductResponse getById(Long productId) {
        return new ProductResponse(repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid Product ID"))) ;
    }

    @Override
    public List<ProductResponse> getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir) {
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
    public Object update(ProductRequest request, Long productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("invalid product ID"));

        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getSku() != null) {
            product.setSku(request.getSku());
        }
        product.setUpdatedAt(LocalDateTime.now());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("invalid category ID"));
            product.setCategory(category);
        }
        return repository.save(product);
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("invalid Product ID"));
        List<ProductVariant> variants = variantRepository.getAllVariantsByProductId(product.getProductId());
//        deleting inventories
       if (variants.isEmpty()){
           repository.delete(product);
       }
           else {
           for (ProductVariant variant : variants) {
               if (orderItemRepository.findOrderItemByProductVariant(variant).isPresent()) {
                   throw new ProductCanNotBeDeletedException("this product and its variants cannot be deleted since one of its variant is present in an order item");
               } else {
                   inventoryRepository.delete(inventoryRepository.findProductInventoryByProductVariant(variant));


               }
//        deleting variants
               variantRepository.deleteAll(variants);
//        deleting product
               repository.delete(product);
           }
       }
}

    @Override
    public PVIResponse getAllProductDetails(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("invalid product id"));
        List<ProductVariant> variants = variantRepository.getAllVariantsByProductId(id);
        List<VIResponse> viResponses = new ArrayList<>();
      try {
          for (ProductVariant variant:variants){
              ProductVariantResponse variantResponse=new ProductVariantResponse(variant);
              ProductInventoryResponse inventoryResponse= new ProductInventoryResponse(inventoryRepository.findProductInventoryByProductVariant(variant));
              VIResponse viResponse = new VIResponse(variantResponse,inventoryResponse);
              viResponses.add(viResponse);
          }
      } catch (Exception e) {
          throw new ResourceNotFoundException("some variant does not have inventory ");
      }
        return new PVIResponse(product,viResponses);
    }
}
