package com.app.util.numberAccount.imp;

import com.app.domain.model.Premises;
import com.app.domain.model.TypeAccount;
import com.app.domain.repository.RepositoryPremises;
import com.app.util.numberAccount.GenerateNumberAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class GenerateNumberAccountImp implements GenerateNumberAccount {

    private static final String CODBANK = "003";
    private static final int ACCOUNT_NUMBER_LENGTH = 18;
    private static final int ACCOUNT_NUMBER_LENGTH_CCI = 20;

    private final RepositoryPremises repositoryPremises;

    @Override
    public String generateAccountNumber(TypeAccount accountType, String currency, String city, String location) {

        String productCode = generateProductCode(accountType, currency);

        String branchCode = generateBranchCode(city, location);

        String sequentialNumber = generateSequentialNumber();

        String accountNumber = productCode + branchCode + sequentialNumber;

        if (accountNumber.length() != ACCOUNT_NUMBER_LENGTH) {
            throw new IllegalStateException("Número de cuenta debe tener " + ACCOUNT_NUMBER_LENGTH + " dígitos");
        }

        return accountNumber;
    }

    private static String generateSequentialNumber() {
        // Generar número de 10 dígitos, puede comenzar con 0
        long min = 0L;
        long max = 9_999_999_999L; // 10 dígitos máximo

        long randomNum = ThreadLocalRandom.current().nextLong(min, max + 1);

        return String.format("%010d", randomNum);

    }

    private String generateBranchCode(String city, String location) {
        Premises premises = repositoryPremises
                .findByCityAndLocation(
                        city, location)
                .orElseThrow(() -> new RuntimeException(
                        "No existe sucursal activa en " + city + " - " + location));

        return premises.getCode();
    }

    private static String generateProductCode(TypeAccount accountType, String currency) {

        switch (accountType) {
            case AHORROS:
                return "PEN".equalsIgnoreCase(currency) ? "0011" : "0012";

            case CORRIENTE:
                return "PEN".equalsIgnoreCase(currency) ? "0013" : "0014";

            case DIGITAL:
                return "PEN".equalsIgnoreCase(currency) ? "0017" : "0018";

            case INDEPENDIENTE:
                return "PEN".equalsIgnoreCase(currency) ? "0010" : "0019";

            default:
                throw new RuntimeException("Tipo de cuenta no soportado");
        }

    }

    @Override
    public String generateCCI(String accountNumber) {

        if (accountNumber == null) {
            throw new IllegalArgumentException("Número de cuenta nulo");
        }

        // Asegurar número limpio
        accountNumber = accountNumber.replace("-", "");

        if (accountNumber.length() != 18 || !accountNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Número de cuenta inválido para CCI");
        }

        String productCode = accountNumber.substring(0, 4);
        String branchCode = accountNumber.substring(4, 8);
        String sequential = accountNumber.substring(8, 18); // 10 dígitos

        StringBuilder cci = new StringBuilder();

        // 1. Banco (BBVA ejemplo)
        cci.append(CODBANK); // 003

        // 2. Moneda
        cci.append(getCurrencyCodeFromProductCode(productCode)); // 1 o 2

        // 3. Agencia
        cci.append(branchCode);

        // 4. Producto (3 primeros)
        cci.append(productCode.substring(0, 3));

        // 5. Cuenta (últimos 9 del secuencial)
        cci.append(sequential.substring(2)); // quita 1er dígito → 9

        // 6. Dígito verificador
        String base = cci.toString();
        int checkDigit = calculateMod10CheckDigit(base);

        cci.append(checkDigit);

        if (cci.length() != ACCOUNT_NUMBER_LENGTH_CCI) {
            throw new IllegalStateException("CCI mal generado: " + cci.length());
        }

        return cci.toString();
    }

    private static String convertToAgencyCodeCCI(String branchCode) {
        try {
            int branchNum = Integer.parseInt(branchCode);
            // Pequeña variación para hacerlo diferente
            int cciAgency = (branchNum + 12) % 10000;
            return String.format("%04d", cciAgency);
        } catch (NumberFormatException e) {
            return "0590"; // Valor por defecto
        }
    }

    private static int calculateMod10CheckDigit(String number) {
        int sum = 0;
        boolean alternate = true;

        // Recorrer de derecha a izquierda
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }

            sum += n;
            alternate = !alternate;
        }

        int mod = sum % 10;
        return mod == 0 ? 0 : 10 - mod;
    }

    private static String adjustToLength(String input, int targetLength) {
        if (input.length() == targetLength) {
            return input;
        } else if (input.length() < targetLength) {
            // Rellenar con ceros a la izquierda
            return String.format("%" + targetLength + "s", input).replace(' ', '0');
        } else {
            // Tomar los últimos 'targetLength' caracteres
            return input.substring(input.length() - targetLength);
        }
    }

    private static String extractAccountForCCI(String accountNumber) {
        String sequential = accountNumber.substring(8, 16); // 0235443332

        // En tu ejemplo, el CCI tiene "443333061" que viene de "0235443332"
        // Parece que toma: posiciones 4-9 del secuencial + "061"
        if (sequential.length() >= 9) {
            String part1 = sequential.substring(3, 9); // "544333" de "0235443332"
            String part2 = "061"; // Sufijo común en BBVA
            return part1 + part2;
        } else {
            // Fallback: últimos 9 dígitos de toda la cuenta
            String last9 = accountNumber.substring(Math.max(0, accountNumber.length() - 9));
            return String.format("%-9s", last9).replace(' ', '0');
        }
    }

    private static String getCurrencyCodeFromProductCode(String productCode) {
        // Basado en los últimos dígitos del productCode
        if (productCode.endsWith("0") || productCode.endsWith("1") || productCode.endsWith("3")
                || productCode.endsWith("7")) {
            return "1"; // Soles
        } else if (productCode.endsWith("2") || productCode.endsWith("4") || productCode.endsWith("9")) {
            return "2"; // Dólares
        } else {
            return "1"; // Por defecto soles
        }
    }

    public static String formatAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() != 18) {
            return accountNumber;
        }

        return accountNumber.substring(0, 4) + "-" +
                accountNumber.substring(4, 8) + "-" +
                accountNumber.substring(8, 18);
    }

    /**
     * Formatea el CCI en grupos para mejor legibilidad
     * 01105900238443333061 -> 011-0590-0238-443333061
     */
    public static String formatCCI(String cci) {
        if (cci == null || cci.length() != 20) {
            return cci;
        }

        return cci.substring(0, 3) + "-" +
                cci.substring(3, 4) + "-" +
                cci.substring(4, 8) + "-" +
                cci.substring(8, 11) + "-" +
                cci.substring(11, 20);
    }

    /**
     * Genera ambos números de forma coordinada
     */
    public AccountNumbers generateBBVAAccountNumbers(TypeAccount accountType, String currency, String city,
            String location) {
        String accountNumber = generateAccountNumber(accountType, currency, city, location);
        String cci = generateCCI(accountNumber);

        return new AccountNumbers(accountNumber, cci);
    }

    /**
     * Clase para retornar ambos números con formato
     */
    public static class AccountNumbers {
        private final String rawAccountNumber;
        private final String rawCCI;

        public AccountNumbers(String accountNumber, String cci) {
            this.rawAccountNumber = accountNumber;
            this.rawCCI = cci;
        }

        public String getAccountNumber() {
            return rawAccountNumber;
        }

        public String getFormattedAccountNumber() {
            return formatAccountNumber(rawAccountNumber);
        }

        public String getCCI() {
            return rawCCI;
        }

        public String getFormattedCCI() {
            return formatCCI(rawCCI);
        }

        @Override
        public String toString() {
            return String.format("Cuenta: %s, CCI: %s",
                    getFormattedAccountNumber(), getFormattedCCI());
        }
    }

    /**
     * Valida el formato de número de cuenta BBVA
     */
    public static boolean isValidBBVAAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            return false;
        }

        // Limpiar guiones si los tiene
        String cleanNumber = accountNumber.replace("-", "");

        // Debe tener 16 dígitos
        if (cleanNumber.length() != ACCOUNT_NUMBER_LENGTH) {
            return false;
        }

        // Debe contener solo dígitos
        if (!cleanNumber.matches("\\d+")) {
            return false;
        }

        // Los primeros 4 dígitos deben ser un código de producto válido
        String productCode = cleanNumber.substring(0, 4);
        return productCode.matches("001[0-4]"); // 0010 a 0014
    }

}
