package ru.mycity;

import org.junit.Test;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.ComplaintDto;
import ru.mycity.ui.service.rest.dto.order.FullOrderDto;

import java.util.List;

public class RestTest {
    @Test
    public void test() throws Exception {
        CoreService coreService = new CoreService();
        FullOrderDto dto = coreService.getOrderList("process", 10, 0);
        System.out.println(dto);
    }
}
