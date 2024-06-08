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
			int option = scanner.nextInt();

			if (option == 9) {
				System.out.println("Saliendo del sistema. ¡Hasta luego!");
				break;
			}

			String fromCurrency;
			String toCurrency;

			// Selecciona las monedas de origen y destino según la opción ingresada por el usuario
			switch (option) {
				// Casos para las diferentes opciones de conversión de moneda
				case 1 -> {
					fromCurrency = "USD";
					toCurrency = "EUR";
				}
				case 2 -> {
					fromCurrency = "EUR";
					toCurrency = "USD";
				}
				case 3 -> {
					fromCurrency = "USD";
					toCurrency = "JPY";
				}
				case 4 -> {
					fromCurrency = "JPY";
					toCurrency = "USD";
				}
				case 5 -> {
					fromCurrency = "USD";
					toCurrency = "BRL";
				}
				case 6 -> {
					fromCurrency = "BRL";
					toCurrency = "USD";
				}
				case 7 -> {
					fromCurrency = "USD";
					toCurrency = "PEN";
				}
				case 8 -> {
					fromCurrency = "PEN";
					toCurrency = "USD";
				}
				// Los demás casos siguen un patrón similar...
				default -> {
					System.out.println("Opción inválida. Por favor, seleccione una opción del 1 al 9.");
					continue;
				}
			}

			// Solicita al usuario ingresar la cantidad a convertir
			System.out.println("Introduce la cantidad a convertir: ");
			double amount = scanner.nextDouble();

			// Obtiene las tasas de cambio para la moneda de origen desde la API
			JsonObject rates = getRates(fromCurrency);

			// Verifica si la tasa de cambio para la moneda de destino está disponible
			if (rates.has(toCurrency)) {
				double rate = rates.get(toCurrency).getAsDouble();
				double convertedAmount = convertCurrency(amount, rate);
				System.out.printf("%.2f %s es igual a %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
			} else {
				System.out.println("No se pudo encontrar la tasa de cambio para la moneda de destino.");
			}
		}

		scanner.close();
	}
}
