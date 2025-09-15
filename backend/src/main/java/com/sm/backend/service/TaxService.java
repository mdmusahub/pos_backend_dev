package com.sm.backend.service;

import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.GstDiscount;
import com.sm.backend.response.TaxResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaxService {
    List<TaxResponse> findAll ();
    TaxResponse findById (Long id);
    void deleteById (Long id);
    ResponseEntity<GstDiscount> createTax (Long id);
    Object updateById (Long id, TaxRequest taxRequest);

}
