package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaListener {
    @Autowired
    ProductRepository productRepository;

    @StreamListener(Processor.INPUT)
    public void onEventByString(@Payload OrderPlaced orderPlaced) {
        if (orderPlaced.getEventType().equals("OrderPlaced")) {
            System.out.println("======================");
            System.out.println("재고량 수정 - 기존데이터가 없으니 현재는 그냥 저장로직만 수행");
            Product p = new Product();
            p.setName(orderPlaced.getProductName());
            p.setStock(orderPlaced.getQty());
            productRepository.save(p);
            System.out.println("======================");
        }
    }

//    @StreamListener(Processor.INPUT)
//    public void onEvent(@Payload OrderPlaced orderPlaced){
//        try {
//            /**
//             * 주문 발생시, 상품 재고량 조정 있음.
//             */
//            if (orderPlaced.getEventType().equals("OrderPlaced")) {
//                System.out.println("##### listener1 : " + orderPlaced.toString());
//
//                productRepository.findById(orderPlaced.getProductId())
//                        .ifPresent(
//                                product -> {
//                                    product.setStock(product.getStock() - orderPlaced.getQty());
//                                    productRepository.save(product);
//                                }
//                        )
//                ;
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}