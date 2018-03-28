package pl.karolinakwiecien.models;

import java.util.List;

public interface WeatherObserver {
    void onWeatherComing(WeatherModel weatherModel);

    void onWeatherLoadList(List<WeatherModel> weatherModelList);
}
