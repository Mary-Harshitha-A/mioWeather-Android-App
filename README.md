# mioWeather Android App
Weather App

This App helps user to get the current location's current weather information
FetchCurrentLocation activity fetches the user's current location
MainActivity is the activity containing the navigation drawer to switch between the screens(fragments)
By default home fragment loads into the mainactivity displaying the current weather data of the current device's location

Home.java - contains the current weather info of current locaion
This fragment has three card view
cardview1 - Current locations weather data
cardview2 - breif view of forecasts for every three hours for the next five days also contains a button, (Details) which navigates user to detailed forecast activity
cardview3 - weather info of current location
