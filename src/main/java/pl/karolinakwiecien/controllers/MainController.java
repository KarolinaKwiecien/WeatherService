package pl.karolinakwiecien.controllers;

import pl.karolinakwiecien.models.WeatherModel;
import pl.karolinakwiecien.models.WeatherObserver;
import pl.karolinakwiecien.models.services.SaveToFile;
import pl.karolinakwiecien.models.services.WeatherService;
import pl.karolinakwiecien.views.MainMenu;

import java.util.List;

public class MainController implements WeatherObserver {

    private WeatherService weatherService = WeatherService.getInstance();
    private MainMenu menu;
    private SaveToFile file;

    public MainController() {
        weatherService.registerObserver(this);
        file = new SaveToFile();
        menu = new MainMenu();
    }

    public void run() {
        menu.printMenu();
        choiceMenu();
    }

    public void choiceMenu() {

        switch (menu.showMenu()) {
            case "1":
                String result = menu.getCityFromUser();
                weatherService.getWeather(result);

                file.saveToFile(result.toString());

                menu.cleanConsole(1);
                run();
                break;
            case "2":
                String result5DaysWeather = menu.getCityFromUser();
                weatherService.getWeatherForNext5Days(result5DaysWeather);

                file.saveToFile(result5DaysWeather.toString());

                menu.cleanConsole(1);
                run();
                break;
            default:
                System.out.println("Niepoprawny wybor");
                menu.cleanConsole(1);
                run();
        }
    }

    @Override
    public void onWeatherComing(WeatherModel weatherModel) {
        menu.sendMessageToConsole(weatherModel.toString());
        menu.cleanConsole(1);
        file.saveToFile(weatherModel.toString());
    }

    @Override

    public void onWeatherLoadList(List<WeatherModel> weatherServiceList) {
        for (WeatherModel weather : weatherServiceList) {
            menu.sendMessageToConsole(weather.toString());
        }
    }


}