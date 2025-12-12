package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

public record CategoryUpdateRequest (
    Long categoryId,
    String name,
    String description

){}