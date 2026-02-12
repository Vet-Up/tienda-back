package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;

public record CheckoutRequest(
        String address,
        CardPaymentRequest cardPaymentRequest
) {
}

