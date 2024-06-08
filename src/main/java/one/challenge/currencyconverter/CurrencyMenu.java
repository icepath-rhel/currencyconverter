package one.challenge.currencyconverter;

public class CurrencyMenu {
    // Método para obtener el menú como una cadena de texto formateada
    public String getMenuString() {
        // Retorna una cadena formateada que representa el menú
        return """
          \s
            ======================================================
                     ===      CURRENCY CONVERTER      ===       \s
                Bienvenido al Sistema de Intercambio de Moneda  \s
            ======================================================

            Por favor, seleccione una opción:
          \s
            1. USD - Dólar estadounidense -> EUR - Euro
            2. EUR - Euro  -> USD - Dólar estadounidense
            3. USD - Dólar estadounidense -> JPY - Yen japonés
            4. JPY - Yen japonés -> USD - Dólar estadounidense
            5. USD - Dólar estadounidense -> BRL - Real brasilero
            6. BRL - Real brasilero -> USD - Dólar estadounidense
            7. USD - Dólar estadounidense -> ARS - Peso argentino
            8. ARS - Peso argentino -> USD - Dólar estadounidense
            9. Salir
          \s
            Nota: Elija una opción válida.

            ======================================================
          \s""";
    }
}