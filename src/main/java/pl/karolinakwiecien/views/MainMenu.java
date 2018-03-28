package pl.karolinakwiecien.views;

import pl.karolinakwiecien.models.services.WeatherService;

import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;
    private MainMenu menu;

    public MainMenu() {
        scanner = new Scanner(System.in);
    }

    public void printMenu() {
        System.out.println("=-------------------------=");
        System.out.println("=---Witaj w Pogodynce---=");
    }

    public String showMenu() {
        System.out.println("1 - pogoda na teraz,");
        System.out.println("2 - pogoda na 5 dni");
        System.out.println("Co chcesz zrobic?");
        return scanner.nextLine();
    }

    public String getCityFromUser() {
        System.out.println("=== Podaj miasto: ===");
        return scanner.nextLine();
    }

    public void cleanConsole(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();

        }
    }

    public void sendMessageToConsole(String message) {
        System.out.println(message);
    }

}
