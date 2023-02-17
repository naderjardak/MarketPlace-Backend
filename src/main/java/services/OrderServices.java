package services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.OrderRepository;

@Service
@Slf4j
public class OrderServices implements OrderInterface {
    @Autowired
    OrderRepository orderRepository;
}
