package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.MarketItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jdbc.repository.query.Query;
import java.util.List;


@Repository
public interface MarketItemRepository extends CrudRepository<MarketItem, Integer> {
    // Basic CRUD methods are inherited:
    // save(), findById(), findAll(), deleteById()
    @Query("SELECT * FROM MarketItem WHERE category_name = :categoryName")
    List<MarketItem> findByCategoryName(String categoryName);

    @Query("SELECT * FROM MarketItem WHERE title LIKE CONCAT('%', :keyword, '%') OR description LIKE CONCAT('%', :keyword, '%')")
    List<MarketItem> searchByKeyword(String keyword);
}
