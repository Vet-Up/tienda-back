package es.VetUp.tienda_back.infrastructure;

import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentResponse;

public interface PaymentGateway {

    CardPaymentResponse payment(CardPaymentRequest request);
}
