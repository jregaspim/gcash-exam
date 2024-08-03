
package com.gcash.gcash_exam.service;

import com.gcash.gcash_exam.model.dto.VoucherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class VoucherService {

    @Value("${voucher.api.url}")
    private String voucherApiUrl;

    @Value("${voucher.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public double getVoucherDiscount(String voucherCode) {

        String url = UriComponentsBuilder.fromHttpUrl(voucherApiUrl + "/voucher/" + voucherCode)
                .queryParam("key", apiKey)
                .toUriString();

        VoucherResponse response = restTemplate.getForObject(url, VoucherResponse.class);

        return response != null ? response.discount() : 0;
    }
}
