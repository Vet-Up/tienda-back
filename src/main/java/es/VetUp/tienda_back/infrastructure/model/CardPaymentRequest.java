package es.VetUp.tienda_back.infrastructure.model;

import es.VetUp.tienda_back.infrastructure.model.smallModels.Authorization;
import es.VetUp.tienda_back.infrastructure.model.smallModels.Destination;
import es.VetUp.tienda_back.infrastructure.model.smallModels.Origin;
import es.VetUp.tienda_back.infrastructure.model.smallModels.Pay;

public record CardPaymentRequest(
                Authorization authorization,
                Origin origin,
                Destination destination,
                Pay payment) {

}
