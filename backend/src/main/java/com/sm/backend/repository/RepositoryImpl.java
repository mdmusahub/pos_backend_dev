package com.sm.backend.repository;

import com.sm.backend.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl {
//  @PersistenceContext
//  private EntityManager manager;
//    public List<Product> getByName(String productName) {
//        CriteriaBuilder builder= manager.getCriteriaBuilder();
//        CriteriaQuery<Product> query= builder.createQuery(Product.class);
//        Root<Product> root = query.from(Product.class);
//        List<Predicate> predicate = new ArrayList<>();
//        if (productName!=null){
//            predicate.add(builder.equal(root.get("productName"),productName));
//        }
//        query.where(builder.and(predicate.toArray(new Predicate[0])));
//        return manager.createQuery(query).getResultList();
//    }
}
