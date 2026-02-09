package es.VetUp.tienda_back.b_domain.exception;

public class ProductNotPurchasedException extends BusinessException {
    public ProductNotPurchasedException(String message) {
        super(message);
    }

    public ProductNotPurchasedException(Long userId, Long productId) {
        super("El usuario con ID " + userId + " no ha comprado el producto con ID " + productId + 
              ". Solo se pueden escribir reseñas de productos que has comprado.");
    }
}

