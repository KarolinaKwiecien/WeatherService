package pl.karolinakwiecien.models;

import lombok.Data;

@Data
public class WeatherModel {

    private String cityName;
    private int temperature;
    private int pressure;
    private int humidity;
    private int clouds;
    private String weatherComment;
    private String dates;

    private WeatherModel(Builder builder) {
        cityName = builder.getCityName();
        temperature = builder.getTemperature();
        pressure = builder.getPressure();
        humidity = builder.getHumidity();
        clouds = builder.getClouds();
        weatherComment = builder.getWeatherComment();
        dates = builder.getDates();
    }

    public static class Builder {

        private String cityName;
        private int temperature;
        private int pressure;
        private int humidity;
        private int clouds;
        private String weatherComment;
        private String dates;


        public Builder(String cityName) {
            this.cityName = cityName;
        }

        public Builder setTemperature(int temp) {
            this.temperature = temp;
            return this;
        }

        public Builder setPressure(int pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder setHumidity(int humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setClouds(int clouds) {
            this.clouds = clouds;
            return this;
        }

        public Builder setWeatherComment(String weatherComment) {
            this.weatherComment = weatherComment;
            return this;
        }

        public Builder setDates(String dates) {
            this.dates = dates;
            return this;
        }

        public String getDates() {
            return dates;
        }

        public String getCityName() {
            return cityName;
        }

        public int getTemperature() {
            return temperature;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public int getClouds() {
            return clouds;
        }

        public String getWeatherComment() {
            return weatherComment;
        }

        public WeatherModel build() {
            return new WeatherModel(this);
        }
    }
}
