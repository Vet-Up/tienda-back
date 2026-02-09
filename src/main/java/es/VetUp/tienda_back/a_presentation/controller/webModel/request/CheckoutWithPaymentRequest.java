package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

public record CheckoutWithPaymentRequest(
        String address,
        String cardNumber,
        String cardHolderName,
        String expirationDate,
        String cvv
) {
}
