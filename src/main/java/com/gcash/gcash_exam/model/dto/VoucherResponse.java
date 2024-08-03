
package com.gcash.gcash_exam.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VoucherResponse(
        @JsonProperty("code") String code,
        @JsonProperty("discount") Double discount,
        @JsonProperty("expiry") String expiry
) {}
