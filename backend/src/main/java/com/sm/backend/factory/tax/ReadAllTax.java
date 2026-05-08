package com.sm.backend.factory.tax;

import com.sm.backend.model.Tax;
import com.sm.backend.repository.TaxRepository;
import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllTax implements TaxOperation{

    private final TaxRepository taxRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public TaxResponse execute(TaxRequest request) {
        List<Tax> taxList = taxRepository.findAll();
        return TaxResponse.builder()
                .taxResponses(taxList.stream().map(TaxResponse::new).toList())
                .build();
    }
}
