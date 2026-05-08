package com.sm.backend.factory.tax;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.repository.TaxRepository;
import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadTaxById implements TaxOperation{

    private final TaxRepository taxRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public TaxResponse execute(TaxRequest request) {
        return new TaxResponse(taxRepository.findById(
                request.getTaxId()).orElseThrow(() ->
                new ResourceNotFoundException("Id not found")));
    }
}
