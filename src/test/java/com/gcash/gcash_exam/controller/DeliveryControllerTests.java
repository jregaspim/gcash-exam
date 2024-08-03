package com.gcash.gcash_exam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcash.gcash_exam.model.dto.DeliveryCostRequest;
import com.gcash.gcash_exam.service.DeliveryCostService;
import com.gcash.gcash_exam.service.VoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
public class DeliveryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryCostService deliveryCostService;

    @MockBean
    private VoucherService voucherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCalculateDeliveryCostWithoutVoucher() throws Exception {
        DeliveryCostRequest request = new DeliveryCostRequest(12.0, 10.0, 10.0, 10.0, null);

        double expectedCost = 325.0;
        when(deliveryCostService.calculateDeliveryCost(any(DeliveryCostRequest.class))).thenReturn(expectedCost);

        mockMvc.perform(post("/api/calculate-delivery-cost")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"cost\":325.0}"));
    }

    @Test
    public void testCalculateDeliveryCostWithVoucher() throws Exception {
        DeliveryCostRequest request = new DeliveryCostRequest(12.0, 10.0, 10.0, 10.0, "MYNT");

        double expectedCostBeforeDiscount = 325.0;
        double discount = 10.0;
        double expectedCostAfterDiscount = expectedCostBeforeDiscount - (expectedCostBeforeDiscount * discount / 100);

        when(deliveryCostService.calculateDeliveryCost(any(DeliveryCostRequest.class))).thenReturn(expectedCostBeforeDiscount);
        when(voucherService.getVoucherDiscount(anyString())).thenReturn(discount);

        mockMvc.perform(post("/api/calculate-delivery-cost")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"cost\":" + expectedCostAfterDiscount + "}"));
    }

    @Test
    public void testRejectOverweightParcel() throws Exception {
        DeliveryCostRequest request = new DeliveryCostRequest(51.0, 10.0, 10.0, 10.0, null);

        mockMvc.perform(post("/api/calculate-delivery-cost")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Weight exceeds 50kg, delivery rejected."));
    }
}
