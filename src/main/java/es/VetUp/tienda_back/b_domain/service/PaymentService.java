package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentResponse;

public interface PaymentService {

    CardPaymentResponse payment(CardPaymentRequest request);
    
}
