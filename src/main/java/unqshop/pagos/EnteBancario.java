package unqshop.pagos;

public interface EnteBancario {

    void validarCBU(String CBU);

    void validarAlias(String alias);

    String realizarTransferencia(
            double monto,
            String origen,
            String destino
    );

    void notificarResultado(String CBU);
}

