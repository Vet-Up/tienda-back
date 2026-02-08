package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.service.PaymentService;
import es.VetUp.tienda_back.infrastructure.PaymentGateway;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentResponse;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentGateway paymentGateway;

    public PaymentServiceImpl(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public CardPaymentResponse payment(CardPaymentRequest request) {
        return paymentGateway.payment(request);
    }
    
}
