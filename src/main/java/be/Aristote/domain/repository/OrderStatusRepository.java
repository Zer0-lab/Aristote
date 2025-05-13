package be.Aristote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.Aristote.domain.model.OrderStatusEntity;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {

}
