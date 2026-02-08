package es.VetUp.tienda_back.infrastructure.model.smallModels;

public record Origin(
        String cardNumber,
        String expirationDate,
        String cvc,
        String fullName
) {
}
