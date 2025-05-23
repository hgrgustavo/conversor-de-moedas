package conversor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class TokenManager {
    private static String apiToken;

    public static String getToken() {
        String token = System.getenv("EXCHANGE_RATE_API_TOKEN");

        if (token.isEmpty() || token == null) {
            setToken();
        }        

        return apiToken;
    }

    private static void setToken() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Por favor, insira o token da Exchange Rate API:");
            apiToken = scanner.nextLine();

            FileWriter fw = new FileWriter(".env");
            fw.write(String.format("EXCHANGE_RATE_API_TOKEN=%s", apiToken));
            fw.close();
            
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
