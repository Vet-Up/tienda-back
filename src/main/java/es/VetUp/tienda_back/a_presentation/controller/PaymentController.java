package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.b_domain.service.PaymentService;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/card-payment")
    public ResponseEntity<CardPaymentResponse> processPayment(@RequestBody CardPaymentRequest request) {
        CardPaymentResponse response = paymentService.payment(request);
        return ResponseEntity.ok(response);
    }

}
