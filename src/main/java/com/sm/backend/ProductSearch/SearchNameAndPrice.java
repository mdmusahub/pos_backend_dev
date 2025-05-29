package com.sm.backend.ProductSearch;

import com.sm.backend.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class SearchNameAndPrice {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> searchByNameAndPrice(String name,Double price){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> root = cq.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if(name != null){
            predicates.add(cb.equal(root.get("name"),name));
        }

        if (price != null){
            predicates.add(cb.equal(root.get("root"),root));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(cq).getResultList();

    }
}
