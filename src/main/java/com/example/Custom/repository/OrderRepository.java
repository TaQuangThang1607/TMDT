package com.example.Custom.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Custom.domain.Order;
import com.example.Custom.domain.User;

public interface OrderRepository extends JpaRepository<Order,Long>{

    List<Order> findByUser(User user);

     // Thống kê doanh thu theo thời gian (ngày, tuần, tháng, năm)
    @Query("SELECT FUNCTION('DATE_FORMAT', o.createdDate, :dateFormat) AS period, SUM(o.totalPrice) AS revenue " +
           "FROM Order o WHERE o.createdDate BETWEEN :startDate AND :endDate " +
           "GROUP BY FUNCTION('DATE_FORMAT', o.createdDate, :dateFormat) " +
           "ORDER BY period")
    List<Object[]> getRevenueByTimePeriod(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate,
                                         @Param("dateFormat") String dateFormat);

    // Thống kê doanh thu theo danh mục trong một tháng
    @Query("SELECT c.name, SUM(od.price * od.quantity) AS revenue " +
           "FROM OrderDetail od JOIN od.order o JOIN od.products p JOIN p.category c " +
           "WHERE YEAR(o.createdDate) = :year AND MONTH(o.createdDate) = :month " +
           "GROUP BY c.name")
    List<Object[]> getRevenueByCategory(@Param("year") int year, @Param("month") int month);
    @Query("SELECT o FROM Order o WHERE o.createdDate BETWEEN :startDate AND :endDate")
    List<Order> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
