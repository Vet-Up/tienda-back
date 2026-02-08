package es.VetUp.tienda_back.infrastructure.impl;

import es.VetUp.tienda_back.infrastructure.PaymentGateway;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class PaymentGatewayImpl implements PaymentGateway {

    private final RestTemplate restTemplate;
    private final String bankApiUrl;

    public PaymentGatewayImpl(RestTemplate restTemplate, String bankApiUrl) {
        this.restTemplate = restTemplate;
        this.bankApiUrl = bankApiUrl;
    }

    @Override
    public CardPaymentResponse payment(CardPaymentRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CardPaymentRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<CardPaymentResponse> response = restTemplate.postForEntity(
                    bankApiUrl,
                    entity,
                    CardPaymentResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println(">>> HTTP Error: " + e.getStatusCode());
            System.out.println(">>> Response body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Error al procesar el pago: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println(">>> General Error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el pago: " + e.getMessage());
        }
    }
    
}
