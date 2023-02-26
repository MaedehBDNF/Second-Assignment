import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.Scanner;

public class WeatherApp {
    public final static String apiKey = "07a12f7837574c17828115031232602";

    public static void main(String[] args) throws ParseException {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of city in capital letter: ");
        String cityName = in.nextLine();

        String weatherJSONString = getWeatherData(cityName);
        JSONObject weatherObject = jsonParser(weatherJSONString);

        System.out.println("Temperature: " + getTemperature(weatherObject));
        System.out.println("Humidity: " + getHumidity(weatherObject));
    }

    /**
     * Retrieves weather data for the specified city.
     *
     * @param city the name of the city for which weather data should be retrieved
     * @return a string representation of the weather data, or null if an error occurred
     */
    public static String getWeatherData(String city) {
        try {
            URL url = new URL("http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double getTemperature(JSONObject weatherJson) {
        return (double) ((JSONObject) weatherJson.get("current")).get("temp_c");
    }

    public static int getHumidity(JSONObject weatherJson) {
        return (int) (long) ((JSONObject) weatherJson.get("current")).get("humidity");
    }

    private static JSONObject jsonParser(String jsonStr) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonStr);
    }
}
