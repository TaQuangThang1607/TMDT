package com.example.Custom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Custom.domain.Category;
import com.example.Custom.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

   @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR :name = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:size IS NULL OR :size = '' OR LOWER(TRIM(p.size)) = LOWER(TRIM(:size))) AND " +
           "(:color IS NULL OR :color = '' OR LOWER(TRIM(p.color)) = LOWER(TRIM(:color))) AND " +
           "(:material IS NULL OR :material = '' OR LOWER(TRIM(p.material)) = LOWER(TRIM(:material))) AND " +
           "(:minStock IS NULL OR p.stock >= :minStock) AND " +
           "(:minSold IS NULL OR p.soldQuantity >= :minSold)")
    Page<Product> findByFilters(
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("category") Category category,
            @Param("size") String size,
            @Param("color") String color,
            @Param("material") String material,
            @Param("minStock") Integer minStock,
            @Param("minSold") Integer minSold,
            Pageable pageable);

    @Query("SELECT DISTINCT p.size FROM Product p WHERE p.size IS NOT NULL")
    List<String> findDistinctSizes();

    @Query("SELECT DISTINCT p.color FROM Product p WHERE p.color IS NOT NULL")
    List<String> findDistinctColors();

    @Query("SELECT DISTINCT p.material FROM Product p WHERE p.material IS NOT NULL")
    List<String> findDistinctMaterials();

    // gợi ý sản phẩm
    List<Product> findByRecommendedTrue();

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchByName(@Param("keyword") String keyword, Pageable pageable);

}
