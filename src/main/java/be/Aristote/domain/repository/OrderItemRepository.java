package be.Aristote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.Aristote.domain.model.OrderItemEntity;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

}
