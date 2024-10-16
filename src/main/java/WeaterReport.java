import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WeaterReport {

    static int optionChoice;A
    static String lon;
    static String lat;

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Choose your option:");
        System.out.println("1. Weather forecast by lon & lat");
        System.out.println("2. Average weather temperature by date");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you option: ");
        optionChoice = scanner.nextInt();

        switch (optionChoice) {
            case 1:
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Введите долготу: ");
                lon = scanner1.nextLine();
                System.out.println("Введите широту: ");
                lat = scanner1.nextLine();

                HttpClient client = HttpClient.newHttpClient();
                URI uri = URI.create("https://api.weather.yandex.ru/v2/forecast?");
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(uri)
                        .header("X-Yandex-Weather-Key", "da24c7b5-4aae-4552-860a-e3fa91f7f959")
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("The response is: " + response.body());
                break;

        }

    }
}
