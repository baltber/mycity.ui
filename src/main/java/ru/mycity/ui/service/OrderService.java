package ru.mycity.ui.service;

import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.order.FullOrderDto;
import ru.mycity.ui.service.rest.dto.order.OrderRequestDto;

import java.util.HashMap;
import java.util.List;

public class OrderService {

    private CoreService coreService;
    private HashMap<String, Integer> cash = new HashMap<>();

    public OrderService() {
        coreService = new CoreService();
    }



    public List<OrderRequestDto> getListOrder(String state, int limit, int offset){
        FullOrderDto fullOrderDto = coreService.getOrderList(state, limit, offset);

        cash.put(state, fullOrderDto.getTotal());

        return fullOrderDto.getOrders();

    }

    public int getCount(String state){
        if (cash.containsKey(state)){
            return cash.get(state);
        }
        return 0;
    }
}
