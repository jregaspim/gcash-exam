package com.gcash.gcash_exam.service;

import com.gcash.gcash_exam.model.dto.DeliveryCostRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCostServiceTests {

    private final DeliveryCostService deliveryCostService = new DeliveryCostService();

    @Test
    public void testCalculateCostForHeavyParcel() {
        DeliveryCostRequest request = new DeliveryCostRequest(12.0, 10.0, 10.0, 10.0, null);

        double cost = deliveryCostService.calculateDeliveryCost(request);

        assertEquals(240.0, cost);
    }

    @Test
    public void testCalculateCostForSmallParcel() {
        DeliveryCostRequest request = new DeliveryCostRequest(8.0, 5.0, 10.0, 20.0, null);

        double cost = deliveryCostService.calculateDeliveryCost(request);

        assertEquals(30.0, cost);
    }

    @Test
    public void testCalculateCostForMediumParcel() {
        DeliveryCostRequest request = new DeliveryCostRequest(8.0, 10.0, 15.0, 15.0, null);

        double cost = deliveryCostService.calculateDeliveryCost(request);

        assertEquals(90.0, cost);
    }

    @Test
    public void testCalculateCostForLargeParcel() {
        DeliveryCostRequest request = new DeliveryCostRequest(8.0, 20.0, 20.0, 20.0, null);

        double cost = deliveryCostService.calculateDeliveryCost(request);

        assertEquals(400.0, cost);
    }
}
