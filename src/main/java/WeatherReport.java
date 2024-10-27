import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WeatherReport {

    static int optionChoice;
    static String lon;
    static String lat;
    static int limit;

    public static void main(String[] args) throws IOException, InterruptedException {

        final String URL = "https://api.weather.yandex.ru/v2/forecast";
        final String HEADER = "X-Yandex-Weather-Key";
        final String HEADER_VALUE = "b1888297-4513-4390-8eb5-09286bf69278";


        System.out.println("Choose your option:");
        System.out.println("1. Weather forecast by lon & lat");
        System.out.println("2. Average weather temperature by date");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you option: ");
        optionChoice = scanner.nextInt();

        switch (optionChoice) {
            case 1:
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Insert longitude as hh.ss: ");
                lon = scanner1.nextLine();
                System.out.println("Insert latitude as hh.ss: ");
                lat = scanner1.nextLine();

                HttpClient clientFullReport = HttpClient.newHttpClient();
                URI uriFullReport = URI.create(URL + "?" + "lat=" + lat + "&" + "lon=" + lon);
                HttpRequest requestFullReport = HttpRequest.newBuilder()
                        .uri(uriFullReport)
                        .header(HEADER, HEADER_VALUE)
                        .GET()
                        .build();
                HttpResponse<String> responseFullReport = clientFullReport.send(requestFullReport, HttpResponse.BodyHandlers.ofString());
                String WeatherFullResponse = responseFullReport.body();

                ObjectMapper mapperFullReport = new ObjectMapper();

                JsonNode jsonFullResponse = mapperFullReport.readTree(WeatherFullResponse);
                JsonNode factTemp = jsonFullResponse.get("fact");
                int temp = factTemp.get("temp").asInt();

                System.out.println("Server response equals to: " + jsonFullResponse);

                System.out.println("Factual temperature equals to: " + temp);

                break;
            case 2:
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Insert longitude as hh.ss: ");
                lon = scanner2.nextLine();
                System.out.println("Insert latitude as hh.ss: ");
                lat = scanner2.nextLine();
                System.out.println("Insert date limit: ");
                limit = scanner2.nextInt();

                HttpClient clientLimitReport = HttpClient.newHttpClient();
                URI uriLimitReport = URI.create(URL + "?" + "lat=" + lat + "&" + "lon=" + lon + "&" + "limit=" + limit);
                HttpRequest requestLimitReport = HttpRequest.newBuilder()
                        .uri(uriLimitReport)
                        .header(HEADER, HEADER_VALUE)
                        .GET()
                        .build();
                HttpResponse<String> responseLimitReport = clientLimitReport.send(requestLimitReport, HttpResponse.BodyHandlers.ofString());
                String WeatherLimitResponse = responseLimitReport.body();

                ObjectMapper mapperLimitReport = new ObjectMapper();
                JsonNode jsonLimitResponse = mapperLimitReport.readTree(WeatherLimitResponse);
                JsonNode forecast = jsonLimitResponse.get("forecast");
                JsonNode parts = forecast.get("parts");
                JsonNode dayTemp = parts.get("day");
                int avgTemp = dayTemp.get("temp_avg").asInt();

                System.out.println("Average temperature by limit equals to: " + parts);
        }
    }
}
