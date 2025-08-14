package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Tax;
import com.sm.backend.repository.TaxRepository;
import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    @Autowired
    public TaxServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }


    @Override
    public List<TaxResponse> findAll() {
        List<Tax> taxList = taxRepository.findAll();
        return taxList.stream().map(TaxResponse::new).toList();
    }

    @Override
    public TaxResponse findById(Long id) {
        return new TaxResponse(taxRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Id not FOund")));
    }

    @Override
    public void deleteById(Long id) {
        Tax tax = taxRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Id not Found"));
        taxRepository.delete(tax);

    }

//    @Override
//    public Object updateById(Long id, TaxRequest taxRequest) {
//        Tax tax = taxRepository.findById(id).orElseThrow(()->
//                new ResourceNotFoundException("Invalid Id"));
//
//        if(taxRequest.getCategory() != null){
//            tax.setCategory(taxRequest.getCategory());
//        }
//        return taxRepository.save(tax);
//    }
}
