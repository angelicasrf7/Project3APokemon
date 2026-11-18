
package Project3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokemonAnalyzer {

    public static void main(String[] args) {

        String pokemon1 = "pikachu";
        String pokemon2 = "charizard";

        int[] stats1 = getPokemonData(pokemon1);
        int[] stats2 = getPokemonData(pokemon2);

        if (stats1 != null && stats2 != null) {
            comparePokemon(pokemon1, stats1, pokemon2, stats2);
        }
    }

    //gets pokemon data
    public static int[] getPokemonData(String pokemon) {

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

                String data = response.body();

                int hp = getStat(data, "hp");
                int attack = getStat(data, "attack");

                return new int[] {hp, attack};

            } else {
                System.out.println("Could not find " + pokemon);
                return null;
            }

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return null;
        }
    }

    //gets pokemon stat
    public static int getStat(String data, String statName) {

    int position = data.indexOf("\"name\": \"" + statName + "\"");

    int start = data.indexOf("\"base_stat\":", position) + 12;
    int end = data.indexOf(",", start);

    return Integer.parseInt(data.substring(start, end).trim());
    }

    //compares pokeman
    public static void comparePokemon(String name1, int[] stats1,
                                      String name2, int[] stats2) {

        System.out.println("\n===== POKEMON BATTLE =====");

        System.out.println(name1 + " HP: " + stats1[0]);
        System.out.println(name1 + " Attack: " + stats1[1]);

        System.out.println(name2 + " HP: " + stats2[0]);
        System.out.println(name2 + " Attack: " + stats2[1]);

        int score1 = stats1[0] + stats1[1];
        int score2 = stats2[0] + stats2[1];

        if (score1 > score2) {
            System.out.println("\n" + name1 + " wins the comparison!");
        } else if (score2 > score1) {
            System.out.println("\n" + name2 + " wins the comparison!");
        } else {
            System.out.println("\nIt's a tie!");
        }
    }
}