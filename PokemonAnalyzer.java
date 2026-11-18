package Project3;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokemonAnalyzer {
    
     public static void main(String[] args) {

        String pokemon = "pikachu";

        getPokemonData(pokemon);
    }

    public static void getPokemonData(String pokemon) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            String url = "https://pokeapi.co/api/v2/pokemon/" + pokemon;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("Error: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}

