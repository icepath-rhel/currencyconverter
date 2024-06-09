package one.challenge.currencyconverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CurrencyConverterApplication {

	// Método para obtener las tasas de cambio de la API
	private static JsonObject getRates(String baseCurrency) {
		URI uri = URI.create("https://v6.exchangerate-api.com/v6/e20478ec913d3043148c09b2/latest/" + baseCurrency);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return new Gson().fromJson(response.body(), JsonObject.class).getAsJsonObject("conversion_rates");
		} catch (Exception error) {
			throw new RuntimeException("No se pudo obtener las tasas de cambio.", error);
		}
	}

	// Método para convertir una cantidad de una moneda a otra
	private static double convertCurrency(double amount, double rate) {
		return amount * rate;
	}

	// Método principal que ejecuta el programa
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		CurrencyMenu menu = new CurrencyMenu();

		while (true) {
			System.out.println(menu.getMenuString());
			String input = scanner.nextLine();

			// Verificar si la entrada es un solo dígito numérico o contiene un espacio en blanco
			if (input.contains(" ") || !(input.length() == 1 && Character.isDigit(input.charAt(0)))) {
				System.out.println("Opción inválida. Por favor, seleccione una opción correcta.");
				continue; // Vuelve al inicio del bucle para pedir una nueva entrada
			}
			int option = Integer.parseInt(input);

			// Termina la ejecución del programa si la opción ingresada es igual a 9
			if (option == 9) {
				System.out.println("Saliendo del sistema. ¡Hasta luego!");
				break;
			}

			// Procesar la opción selecionada
			String fromCurrencyCode = "";
			String toCurrencyCode = "";

			// Selecciona las monedas de origen y destino según la opción ingresada por el usuario
			switch (option) {
				// Casos para las diferentes opciones de conversión de moneda
				case 1 -> { fromCurrencyCode = "USD"; toCurrencyCode = "EUR"; }
				case 2 -> { fromCurrencyCode = "EUR"; toCurrencyCode = "USD"; }
				case 3 -> { fromCurrencyCode = "USD"; toCurrencyCode = "JPY"; }
				case 4 -> { fromCurrencyCode = "JPY"; toCurrencyCode = "USD"; }
				case 5 -> { fromCurrencyCode = "USD"; toCurrencyCode = "BRL"; }
				case 6 -> { fromCurrencyCode = "BRL"; toCurrencyCode = "USD"; }
				case 7 -> { fromCurrencyCode = "USD"; toCurrencyCode = "ARS"; }
				case 8 -> { fromCurrencyCode = "ARS"; toCurrencyCode = "USD"; }
			}

			// Solicita al usuario ingresar la cantidad a convertir
			System.out.println("Introduce la cantidad a convertir: ");
			String amountInput = scanner.nextLine();

			// Verificar si se ingresaron múltiples cantidades o si la entrada es no numérica
			if (amountInput.contains(" ") || !amountInput.matches("-?\\d+(\\.\\d+)?")) {
				System.out.println("Cantidad inválida. Por favor, ingrese un único número.");
				continue;
			}
			double amount = Double.parseDouble(amountInput);

			// Obtiene las tasas de cambio para la moneda de origen desde la API
			JsonObject rates = getRates(fromCurrencyCode);

			// Verifica si la tasa de cambio para la moneda de destino está disponible
			if (rates.has(toCurrencyCode)) {
				double rate = rates.get(toCurrencyCode).getAsDouble();
				double convertedAmount = convertCurrency(amount, rate);
				System.out.printf("%.2f %s es igual a %.2f %s%n", amount, fromCurrencyCode, convertedAmount, toCurrencyCode);
			} else {
				System.out.println("No se pudo encontrar la tasa de cambio para la moneda de destino.");
			}
		}

		scanner.close();
	}
}
