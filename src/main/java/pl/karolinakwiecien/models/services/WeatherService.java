package pl.karolinakwiecien.models.services;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.karolinakwiecien.models.Utils;
import pl.karolinakwiecien.models.WeatherModel;
import pl.karolinakwiecien.models.WeatherObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WeatherService {
    private static WeatherService ourInstance = new WeatherService();

    public static WeatherService getInstance() {
        return ourInstance;
    }

    private ExecutorService executorService;
    private List<WeatherObserver> weatherObserverList;
    private List<WeatherModel> weatherServiceList;

    private WeatherService() {
        weatherObserverList = new ArrayList<>();
        executorService = Executors.newSingleThreadExecutor();

    }

    public void registerObserver(WeatherObserver weatherObserver) {
        weatherObserverList.add(weatherObserver);
    }

    public void getWeather(final String city) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                String websiteResponse = Utils.readWebsiteContent("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=d726ad9fa56df47403f825382495aa7d");

                String description = null;
                int temperature;
                int pressure;
                int humidity;
                int clouds;

                JSONObject root = new JSONObject(websiteResponse);

                JSONArray weatherObject = root.getJSONArray("weather");

                for (int i = 0; i < weatherObject.length(); i++) {
                    JSONObject elementInArray = weatherObject.getJSONObject(i);
                    description = elementInArray.getString("main");
                }

                JSONObject mainObject = root.getJSONObject("main");
                temperature = (int) mainObject.getFloat("temp");
                pressure = mainObject.getInt("pressure");
                humidity = mainObject.getInt("humidity");

                JSONObject cloudsObject = root.getJSONObject("clouds");
                clouds = cloudsObject.getInt("all");

                WeatherModel weatherModel = new WeatherModel.Builder(city)
                        .setClouds(clouds)
                        .setHumidity(humidity)
                        .setPressure(pressure)
                        .setTemperature(temperature)
                        .setWeatherComment(description)
                        .build();

                notifyObservers(weatherModel);
            }
        });
    }

    public void getWeatherForNext5Days(final String city) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                String websiteResponse = Utils.readWebsiteContent("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=d726ad9fa56df47403f825382495aa7d");

                String description = null;
                int temperature;
                int pressure;
                int humidity;
                int clouds;
                String dates = null;

                JSONObject root = new JSONObject(websiteResponse);
                JSONArray listArray = root.getJSONArray("list");
                JSONObject mainObject = null;
                JSONObject cloudsObject = null;
                weatherServiceList = new LinkedList<>();

                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject objectToFind = listArray.getJSONObject(i);
                    mainObject = objectToFind.getJSONObject("main");
                    cloudsObject = objectToFind.getJSONObject("clouds");
                    clouds = cloudsObject.getInt("all");
                    temperature = (int) mainObject.getFloat("temp");
                    pressure = mainObject.getInt("pressure");
                    humidity = mainObject.getInt("humidity");
                    dates = objectToFind.getString("dt_txt");
                    description = objectToFind.getJSONArray("weather").getJSONObject(0).getString("main");


                    weatherServiceList.add(new WeatherModel.Builder(city)
                            .setDates(dates)
                            .setClouds(clouds)
                            .setHumidity(humidity)
                            .setPressure(pressure)
                            .setTemperature(temperature)
                            .setWeatherComment(description)
                            .build());
                }
                for (WeatherModel weatherModel : weatherServiceList) {
                    System.out.println(weatherModel.toString());
                }
            }
        });
    }

    private void notifyObservers(WeatherModel weatherModel) {
        for (WeatherObserver weatherObserver : weatherObserverList) {
            weatherObserver.onWeatherComing(weatherModel);
        }
    }

    private void notifyObserver(List<WeatherModel> weatherServiceList) {
        for (WeatherObserver weatherObserver : weatherObserverList) {
            weatherObserver.onWeatherLoadList(weatherServiceList);
        }
    }
}




