
package com.gcash.gcash_exam.service;

import com.gcash.gcash_exam.model.dto.DeliveryCostRequest;
import org.springframework.stereotype.Service;

@Service
public class DeliveryCostService {

    public double calculateDeliveryCost(DeliveryCostRequest request) {

        double weight = request.weight();
        double height = request.height();
        double width = request.width();
        double length = request.length();

        double volume = height * width * length;

        if (weight > 10) {
            return 20 * weight;
        }

        if (volume < 1500) {
            return 0.03 * volume;
        }

        if (volume < 2500) {
            return 0.04 * volume;
        }

        return 0.05 * volume;
    }
}
