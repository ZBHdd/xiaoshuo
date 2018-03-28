package com.example.demo.base;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryCondition <T>{
    public Specification<T> addCondition(Map<String,Object> map) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                List<Predicate> list = new ArrayList<Predicate>();
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    if (entry.getKey().toUpperCase().contains(String.valueOf(Condition._LIKE))) {
                        String key = entry.getKey().split("-")[0];
                        list.add(builder.like(root.get(key),String.valueOf(entry.getValue())));continue;
                    }
                    if (entry.getKey().toUpperCase().contains(String.valueOf(Condition._EQ))) {
                        String key = entry.getKey().split("-")[0];
                        list.add(builder.equal(root.get(key),String.valueOf(entry.getValue())));continue;
                    }
                    if (entry.getKey().toUpperCase().contains(String.valueOf(Condition._LT))) {
                        String key = entry.getKey().split("-")[0];
                        list.add(builder.lessThanOrEqualTo(root.get(key),String.valueOf(entry.getValue())));continue;
                    }
                    if (entry.getKey().toUpperCase().contains(String.valueOf(Condition._GT))) {
                        String key = entry.getKey().split("-")[0];
                        list.add(builder.greaterThanOrEqualTo(root.get(key),String.valueOf(entry.getValue())));continue;
                    }
                    if (entry.getKey().toUpperCase().contains(String.valueOf(Condition._IN))) {
                        String key = entry.getKey().split("-")[0];
                        list.add(root.get(key).in(String.valueOf(entry.getValue())));
                    }
                }
                return query.where(list.toArray(new Predicate[list.size()])).getRestriction();
            }
        };
    }
}
