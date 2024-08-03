package com.gcash.gcash_exam.service;

import com.gcash.gcash_exam.model.dto.VoucherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class VoucherServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VoucherService voucherService;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        setField(voucherService, "voucherApiUrl", "https://mynt-exam.mocklab.io");
        setField(voucherService, "apiKey", "apikey");
    }

    @Test
    public void testGetVoucherDiscount_ValidVoucher() {
        String voucherCode = "MYNT";
        VoucherResponse mockResponse = new VoucherResponse(voucherCode, 10.0, "2024-12-31");
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(mockResponse);

        double discount = voucherService.getVoucherDiscount(voucherCode);

        assertEquals(10.0, discount);
    }

    @Test
    public void testGetVoucherDiscount_InvalidVoucher() {
        String voucherCode = "INVALID";
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(null);

        double discount = voucherService.getVoucherDiscount(voucherCode);

        assertEquals(0.0, discount);
    }

    @Test
    public void testGetVoucherDiscount_NoDiscount() {
        String voucherCode = "NODISCOUNT";
        VoucherResponse mockResponse = new VoucherResponse(voucherCode, 0.0, "2024-12-31");
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(mockResponse);

        double discount = voucherService.getVoucherDiscount(voucherCode);

        assertEquals(0.0, discount);
    }

    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
