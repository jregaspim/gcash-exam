
package com.gcash.gcash_exam.model.dto;

public record DeliveryCostRequest (
        Double weight,
        Double height,
        Double width,
        Double length,
        String voucherCode) {}
