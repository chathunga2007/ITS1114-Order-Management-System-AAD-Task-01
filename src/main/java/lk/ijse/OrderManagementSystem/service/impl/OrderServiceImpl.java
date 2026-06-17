package lk.ijse.OrderManagementSystem.service.impl;

import lk.ijse.OrderManagementSystem.dto.FilterOrderDTO;
import lk.ijse.OrderManagementSystem.dto.ItemDTO;
import lk.ijse.OrderManagementSystem.dto.PlaceOrderDTO;
import lk.ijse.OrderManagementSystem.entity.Customer;
import lk.ijse.OrderManagementSystem.entity.Item;
import lk.ijse.OrderManagementSystem.entity.Order;
import lk.ijse.OrderManagementSystem.entity.OrderItem;
import lk.ijse.OrderManagementSystem.repository.CustomerRepository;
import lk.ijse.OrderManagementSystem.repository.ItemRepository;
import lk.ijse.OrderManagementSystem.repository.OrderItemRepository;
import lk.ijse.OrderManagementSystem.repository.OrderRepository;
import lk.ijse.OrderManagementSystem.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ItemRepository itemRepository;
    private OrderItemRepository  orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ItemRepository itemRepository,
                            OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void placeOrder(PlaceOrderDTO placeOrderDTO) {
        log.info("Placing order");

        try {
            Order order = new Order();
            order.setTotal(placeOrderDTO.getTotal());
            order.setOrderDate(new java.util.Date());

            Optional<Customer> optionalCustomer = customerRepository.findById(placeOrderDTO.getCustomerId());
            if (optionalCustomer.isEmpty())
                throw new Exception("Customer not found");

            Customer customer = optionalCustomer.get();
            order.setCustomer(customer);

            Order savedOrder = orderRepository.save(order);

            for (Long itemId : placeOrderDTO.getItemIdList()) {
                OrderItem orderItem = new OrderItem();

                Optional<Item> optionalItem = itemRepository.findById(itemId);
                if (optionalItem.isEmpty())
                    throw new Exception("Item not found");

                Item item = optionalItem.get();

                orderItem.setOrders(savedOrder);
                orderItem.setItem(item);
                orderItem.setOrderItemQTY(1);
                
                int price = 0;
                if (item.getItemPrice() != null) {
                    try {
                        price = (int) Double.parseDouble(item.getItemPrice());
                    } catch (NumberFormatException e) {
                        log.warn("Failed to parse item price: {}", item.getItemPrice());
                    }
                }
                orderItem.setOrderItemPrice(price);

                orderItemRepository.save(orderItem);
            }

            log.info("Order placed successfully");

        } catch (Exception e) {
            log.error("Error placing order: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FilterOrderDTO> filterOrders(String customerName) {
        log.info("Filtering orders");
        try {

            List<FilterOrderDTO> filterOrderDTOS = new ArrayList<>();
            List<Order> orderList = orderRepository.filterOrders(customerName);

            for(Order order : orderList){
                FilterOrderDTO  filterOrderDTO = new FilterOrderDTO();

                filterOrderDTO.setOrderId(order.getOrderId());
                filterOrderDTO.setCustomerName(order.getCustomer().getCustomerName());

                List<ItemDTO> itemDTOList = new ArrayList<>();

                List<OrderItem> orderItemList = order.getOrderItems();

                for(OrderItem orderItem : orderItemList){
                    Item item = orderItem.getItem();
                    ItemDTO itemDTO = new ItemDTO();
                    itemDTO.setItemId(item.getItemId());
                    itemDTO.setItemName(item.getItemName());
                    itemDTO.setItemQTY(item.getItemQTY());
                    itemDTO.setItemPrice(item.getItemPrice());

                    itemDTOList.add(itemDTO);
                }

                filterOrderDTO.setItemList(itemDTOList);

                filterOrderDTOS.add(filterOrderDTO);
            }
            return filterOrderDTOS;
        } catch (Exception e) {
            log.error("Error filtering orders");
            throw e;
        }
    }

    @Override
    public List<FilterOrderDTO> getOrdersByCustomerId(Long customerId) {
        log.info("Retrieving orders for customer: {}", customerId);
        try {
            List<FilterOrderDTO> filterOrderDTOS = new ArrayList<>();
            List<Order> orderList = orderRepository.getOrdersByCustomerId(customerId);

            for(Order order : orderList){
                FilterOrderDTO filterOrderDTO = new FilterOrderDTO();

                filterOrderDTO.setOrderId(order.getOrderId());
                filterOrderDTO.setCustomerName(order.getCustomer().getCustomerName());

                List<ItemDTO> itemDTOList = new ArrayList<>();
                List<OrderItem> orderItemList = order.getOrderItems();

                for(OrderItem orderItem : orderItemList){
                    Item item = orderItem.getItem();
                    ItemDTO itemDTO = new ItemDTO();
                    itemDTO.setItemId(item.getItemId());
                    itemDTO.setItemName(item.getItemName());
                    itemDTO.setItemQTY(item.getItemQTY());
                    itemDTO.setItemPrice(item.getItemPrice());

                    itemDTOList.add(itemDTO);
                }

                filterOrderDTO.setItemList(itemDTOList);
                filterOrderDTOS.add(filterOrderDTO);
            }
            return filterOrderDTOS;
        } catch (Exception e) {
            log.error("Error retrieving customer orders: {}", e.getMessage());
            throw e;
        }
    }
}