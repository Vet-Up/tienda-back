package es.VetUp.tienda_back.infrastructure.model.smallModels;

public record Authorization(
        String login,
        String apiToken
) {
}
