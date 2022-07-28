# CurrencyTracker

# The project used:
___
MVVM pattern;
Dependency injection (Koin);
Room;
Retrofit;
Custom SplashScreen;
Navigation fragment;
SharedPreferences;
Coroutines;
LiveData.

# Screens
___
Splash fragment
![image](https://user-images.githubusercontent.com/95710591/181475247-34bbcb9d-338a-4406-aec9-0762c5573d11.png)
The loading screen required for visual smoothing application startup.

Currencies fragment
![image](https://user-images.githubusercontent.com/95710591/181475382-7ff0bfa5-eb37-4229-a673-851d6a8d6e82.png)
The main screen that displays a list of currencies, a search query bar, and a menu.

Details fragment
![image](https://user-images.githubusercontent.com/95710591/181475545-7b9732c1-ba54-46c0-8e18-eaf6045fa188.png)
Details screen, displays an abbreviated currency designation, currency symbols if available, currency type, and the exchange rate to the three major currencies.

Converter fragment
![image](https://user-images.githubusercontent.com/95710591/181475617-a14b220d-0868-4efc-8549-d79b5dd3261d.png)
![image](https://user-images.githubusercontent.com/95710591/181475648-2c0c67e4-d80c-4c1d-8d49-ac229c49ebc4.png)
Converter screen, displays two lists of currencies, a line to enter the amount of converted currency, a line to display the result of the conversion.

Settings fragment
![image](https://user-images.githubusercontent.com/95710591/181475733-a6989e0e-de2b-4ee1-b7db-e310b56691d6.png)
![image](https://user-images.githubusercontent.com/95710591/181475748-0e071de3-e49d-4c91-8cb6-f2966f775665.png)
Settings screen, displays a list of settings available to the user, such as changing the application theme, language, display the currency list on the Home screen, etc.

# Difficulties in development:
I could not implement a widget using a database to track a specific currency;
Few user-friendly api.

# The API used
___
https://api.coincap.io/
