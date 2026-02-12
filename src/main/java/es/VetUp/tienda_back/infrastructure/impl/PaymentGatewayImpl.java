package es.VetUp.tienda_back.infrastructure.impl;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.infrastructure.PaymentGateway;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class PaymentGatewayImpl implements PaymentGateway {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayImpl.class);
    private final RestTemplate restTemplate;
    private final String bankApiUrl;

    public PaymentGatewayImpl(RestTemplate restTemplate, String bankApiUrl) {
        this.restTemplate = restTemplate;
        this.bankApiUrl = bankApiUrl;
    }

    @Override
    public CardPaymentResponse payment(CardPaymentRequest request) {
        log.info("PaymentGateway - Processing payment to bank API: {}", bankApiUrl);
        log.info("PaymentGateway - Authorization: login={}, apiToken={}",
                 request.authorization() != null ? request.authorization().login() : "null",
                 request.authorization() != null ? request.authorization().apiToken() : "null");
        log.info("PaymentGateway - Origin: cardNumber={}, fullName={}",
                 request.origin() != null ? request.origin().cardNumber() : "null",
                 request.origin() != null ? request.origin().fullName() : "null");
        log.info("PaymentGateway - Destination: iban={}",
                 request.destination() != null ? request.destination().iban() : "null");
        log.info("PaymentGateway - Payment: amount={}, concept={}",
                 request.payment() != null ? request.payment().amount() : "null",
                 request.payment() != null ? request.payment().concept() : "null");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CardPaymentRequest> entity = new HttpEntity<>(request, headers);

        try {
            log.info("PaymentGateway - Sending POST request to: {}", bankApiUrl);
            ResponseEntity<CardPaymentResponse> response = restTemplate.postForEntity(
                    bankApiUrl,
                    entity,
                    CardPaymentResponse.class
            );

            CardPaymentResponse paymentResponse = response.getBody();
            log.info("PaymentGateway - Payment response status: {}", paymentResponse != null ? paymentResponse.status() : "null");

            return paymentResponse;
        } catch (HttpClientErrorException e) {
            log.error("PaymentGateway - HTTP Client Error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new BusinessException("Error al procesar el pago con el banco: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            log.error("PaymentGateway - HTTP Server Error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new BusinessException("El servidor del banco no está disponible. Por favor, intente más tarde.");
        } catch (ResourceAccessException e) {
            log.error("PaymentGateway - Connection Error: Cannot connect to bank API at {}", bankApiUrl, e);
            throw new BusinessException("No se puede conectar con el banco. Verifique que el servicio bancario esté activo en: " + bankApiUrl);
        } catch (Exception e) {
            log.error("PaymentGateway - Unexpected Error: {} - {}", e.getClass().getName(), e.getMessage(), e);
            throw new BusinessException("Error inesperado al procesar el pago: " + e.getMessage());
        }
    }
    
}
