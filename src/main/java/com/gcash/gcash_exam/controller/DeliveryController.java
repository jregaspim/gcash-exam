
package com.gcash.gcash_exam.controller;

import com.gcash.gcash_exam.model.dto.DeliveryCostRequest;
import com.gcash.gcash_exam.model.dto.DeliveryCostResponse;
import com.gcash.gcash_exam.service.DeliveryCostService;
import com.gcash.gcash_exam.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeliveryController {

    private final DeliveryCostService deliveryCostService;
    private final VoucherService voucherService;

    @PostMapping("/calculate-delivery-cost")
    public ResponseEntity<?> calculateDeliveryCost(@RequestBody DeliveryCostRequest request) {

        if (request.weight() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Weight exceeds 50kg, delivery rejected.");
        }

        double cost = deliveryCostService.calculateDeliveryCost(request);

        if (request.voucherCode() != null && !request.voucherCode().isEmpty()) {
            double discount = voucherService.getVoucherDiscount(request.voucherCode());
            cost -= (cost * discount / 100);
        }

        return ResponseEntity.ok(new DeliveryCostResponse(cost));
    }
}
