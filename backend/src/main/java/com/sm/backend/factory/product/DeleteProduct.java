package com.sm.backend.factory.product;

import com.sm.backend.exceptionalHandling.ProductCanNotBeDeletedException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.*;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteProduct implements ProductOperation{

    private final ProductRepository repository;
    private final ProductVariantRepository variantRepository;
    private final ProductInventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public ProductResponse execute(ProductRequest request) {
        Product product =
                repository.findById(request.getProductId())
                        .orElseThrow((
                        ) -> new ResourceNotFoundException("invalid Product ID"));
        List<ProductVariant>
                variants = variantRepository.getAllVariantsByProductId(
                        product.getId());
//        deleting inventories
        if (variants.isEmpty()){
            repository.delete(product);
        }
        else {
            for (ProductVariant variant : variants) {
                if (orderItemRepository.findOrderItemByProductVariant(
                        variant).isPresent()) {
                    throw new ProductCanNotBeDeletedException(
                            "this product and its variants cannot be deleted " +
                                    "since its variant " +variant.getProduct()
                                    +" is present in an order item");
                } else {
                    inventoryRepository.delete(
                            inventoryRepository.
                                    findProductInventoryByProductVariant(
                                            variant));
                }
            }
            //         deleting variants
            variantRepository.deleteAll(variants);
//        deleting product
            repository.delete(product);
        }
        return ProductResponse.builder()
                .message("product deleted successfully")
                .build();
    }
}
