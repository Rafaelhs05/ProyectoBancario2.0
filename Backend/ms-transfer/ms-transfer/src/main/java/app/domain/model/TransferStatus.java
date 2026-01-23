package app.domain.model;

public enum TransferStatus {

    CREATED, // Creada
    PENDING, // Validaciones OK
    DEBITED, // Débito realizado
    CREDITED, // Crédito realizado
    COMPLETED, // Transferencia exitosa
    FAILED, // Falló
    REVERSED // Revertida

}
