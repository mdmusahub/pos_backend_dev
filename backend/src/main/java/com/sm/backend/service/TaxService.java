package com.sm.backend.service;

import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.TaxResponse;

import java.util.List;

public interface TaxService {
    List<TaxResponse> findAll ();
    TaxResponse findById (Long id);
    void deleteById (Long id);
//    Object updateById (Long id, TaxRequest taxRequest);

}
