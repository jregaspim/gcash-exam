# Delivery Cost API

This Spring Boot application provides an API to calculate the delivery cost of a parcel based on weight and volume. The application supports flexible rules for cost calculation and integrates with an external voucher service to apply discounts.

## Features

- Calculate delivery cost based on weight and volume
- Flexible pricing rules
- Integration with an external voucher service for discounts

## Installation

### Prerequisites

- Java 17 or later
- Maven

### Setup

1. **Clone the repository:**
   ```sh
   git clone https://github.com/jregaspim/gcash-exam.git
   cd gcash-exam

## API Endpoints
### Calculate Delivery Cost
- URL: `/api/calculate-delivery-cost`
- Method: `POST`
- Request Body:
  ```
  {
    "weight": 12.0,
    "height": 10.0,
    "width": 10.0,
    "length": 10.0,
    "voucherCode": "MYNT"
  }

- Response:
  ```
  {
    "cost":216.0
  }


