package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.User;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query("SELECT concat(u.FirstName,' ',u.LastName,' ',COUNT(o.status)) from User u,Order o where u.id=o.buyer.id and o.status='ACCEPTED_PAYMENT' GROUP BY u.id ORDER BY COUNT(o.status) desc ")
  List<String> RankUsersByOrdersAcceptedPayement();

  int findByBuyerId(Long id);
}
