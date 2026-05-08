package com.sm.backend.factory.tax;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Tax;
import com.sm.backend.repository.TaxRepository;
import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTax implements TaxOperation{

    private final TaxRepository taxRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public TaxResponse execute(TaxRequest request) {

        Tax tax = taxRepository.findById(request.getTaxId()).orElseThrow(()->
                new ResourceNotFoundException("Invalid Id"));

        if(request.getCategory() != null){
            tax.setCategory(request.getCategory());
        }
        taxRepository.save(tax);
        return TaxResponse.builder()
                .message("Tax updated successfully with ID " + request.getTaxId())
                .build();
    }
}
