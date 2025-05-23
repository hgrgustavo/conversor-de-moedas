package conversor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;


public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print(
                """
                    Seja bem-vindo(a) ao Conversor de Moeda!\n
                    1) Dólar >>> Peso argentino
                    2) Dólar >>> Boliviano boliviano
                    3) Dólar >>> Real brasileiro
                    4) Dólar >>> Peso chileno
                    5) Dólar >>> Peso colombiano 
                    6) Real brasileiro >>> Dólar
                    7) Sair\n
                    Escolha uma opção válida:\s"""
            );

            int option = 0;

            if(scanner.hasNextInt() ) {
                option = scanner.nextInt(); // if there is another number  
            }

            if (option == 7) {
                System.out.print("Encerrando . . .");
                System.exit(0);
            }
            
            while (option > 7 || option < 1) {
                System.out.print("Opção inválida! Digite novamente: ");
                
                option = scanner.nextInt();
            }

            String base_code = "";

            switch (option) {
                case 1, 2, 3, 4, 5 -> base_code = "USD";
                case 6 -> base_code = "BRL";
            }

            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                HttpClient client = HttpClient.newBuilder().build();

                Dotenv dotenv = Dotenv.configure().directory(".").load();

                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://v6.exchangerate-api.com/v6/" + dotenv.get("EXCHANGE_RATE_API_TOKEN") + "/latest/" + base_code))
                    .GET()
                    .build();

                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

                ExchangeRateResponse rate = gson.fromJson(response.body(), ExchangeRateResponse.class);

                switch (option) {
                    case 1 -> System.out.println("Taxa de conversão para ARS: " + rate.conversion_rates().get("ARS"));
                    case 2 -> System.out.println("Taxa de conversão para BOB: " + rate.conversion_rates().get("BOB"));
                    case 3 -> System.out.println("Taxa de conversão para BRL: " + rate.conversion_rates().get("BRL"));
                    case 4 -> System.out.println("Taxa de conversão para CLP: " + rate.conversion_rates().get("CLP"));
                    case 5 -> System.out.println("Taxa de conversão para COP: " + rate.conversion_rates().get("COP"));
                    case 6 -> System.out.println("Taxa de conversão para USD: " + rate.conversion_rates().get("USD"));                
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }


    }
}
