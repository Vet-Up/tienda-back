package es.VetUp.tienda_back.infrastructure.model;

public record CardPaymentResponse(
    String transactionId,
    String status,
    String message
) {
}
