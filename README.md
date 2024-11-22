# Weather tracker

![Overview](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/5919a763-bea5-4332-9319-a6942143dfdb)

## Overview
Web application for viewing the current weather. The user can register and add one or more locations (cities, villages, other points) to the collection, after which the main page of the application starts displaying the list of locations with their current weather.. The idea is taken from [here](https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/)

## Technologies / tools used:

### Backend

![java-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/bc1ab298-7a78-42ec-8813-05b38668310e)
<img src="https://img.shields.io/badge/Spring-MVC-black?style=flat&logo=spring&labelColor=black" alt="Spring MVC" style="height:28px; width:auto;">
![hibernate-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/071df0a5-79ef-4435-9c98-5a9b2383d420)
![postgresql](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/8922bdba-ad57-4d69-b68c-ec505fff82e0)
![maven-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/159c5f30-83db-49a2-906a-fc92a071eeff)
![liquibase-logo](https://github.com/user-attachments/assets/a3aff29d-e3f2-4ae4-ad09-ee3429ce43c8)
![opeanweatherapi](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/78bce6ce-0faf-4d08-bf48-cc12cea9cc83)


### Testing

![junit-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/a1a05826-fecb-4b7a-827c-946ffc72da32)
![mockito](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/c405a582-b268-4b82-b3e8-461d77b7f39c)
![docker-testcontainers-logo](https://github.com/user-attachments/assets/6f62d4c3-a4d3-4d38-9e6a-124cd5e7e0b0)

### Frontend

![thymeleaf](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/5c5cda5f-c5d6-42c8-893b-3737e8d04db2)
![html-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/cf73900e-a565-405d-b7dd-cc05f9429c2f)
![css-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/d7d9ecf6-1cfb-4fe1-ba32-dd43d59921a8)
<img src="https://img.shields.io/badge/Bootstrap-black?style=flat-square&logo=bootstrap&labelColor=black" alt="Bootstrap" style="height:28px; width:auto;">

### Deploy

![dockerfile](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/e22a80da-ca5a-438b-a5f5-605393f3208d)
![docker-compose](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/82390fb8-e6d4-4b15-b175-78eead5bc360)


## Application interface

### Home page

![Home-page](https://github.com/user-attachments/assets/c4f9bf44-39ef-4563-8b28-654514c043d2)

Url -'/home'
- Links to the main page where you can see all liked locations and delete them.Delete mapping to "locations/delete/{id}" where id is your location id.
- Navbar:
- Sign Out: Sends a POST request to /logout to sign out the user.Delete cookie and invalidate your session
- Search: Sends a GET request to /search?query=query, where query is the user-provided search term.

### Search page

![Search-page](https://github.com/user-attachments/assets/3d968860-9f22-40e8-a9b0-079b16c12a0d)

- result of your search term.  Geocoding API call for finding longitude and latitude and then by coordinates get temperature from openweather.
- if you press "heart" than it will save it like your liked location with post mapping to "locations/add". 


### Login page

![Login page](https://github.com/user-attachments/assets/bb36210c-5ef7-4505-9b79-332519eedc64)

- login page. Post mapping to "/login" when you press login. Creates cookie and send to your browser, update your session.

### Register page

![Register page](https://github.com/user-attachments/assets/bba7c031-00ae-44f9-9334-704affa013bd)

-register page. Post mapping to "/register". Creates session and cookie for you and save your login and encrypted password.

### Error pages

![404-500](https://github.com/user-attachments/assets/a2853c67-22dd-493e-b94c-6604ef1fae81)

- occur when something went wrong in back or get mapper doesnt exists to your url

## Database diagram

![diagram](https://github.com/user-attachments/assets/bca470c9-eef5-4cd9-85d3-da01c96a1cf9)


## Requirements
  + Java 11+
  + Apache Maven
  + Tomcat 10
  + Intellij IDEA
  + Docker
  + OpenWeather Api Key

## Project launch

+ Clone the repository:

   ```
   git clone https://github.com/ssss1131/weather_tracker.git
   ```
+ Open your cloned repository folder in Intellij IDEA
  
+ Open the console(Alt + F12) and type `docker-compose -f docker-compose-dev.yaml up -d`
  
  ![Screen-cmd](https://github.com/user-attachments/assets/0661c70d-6c6d-406b-82c0-4d7b151aa53a)


+ Open the application.properties file and insert your OpenWeatherAPI key there to weather.api.key

  ![image](https://github.com/user-attachments/assets/d4cb67c3-21bd-4a13-bca0-39a9fbdf018e)


+ In Intellij IDEA, select Run -> Edit Configuration. In the pop-up window, click "+" and add Tomcat :
   
    ![Add tomcat](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/66f677af-ce05-4676-8dc7-09bc8cbf5db5)

+ Then click "Fix" : 

    ![Fix](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/a494e8f2-b579-45df-a006-084c123b3cc9)


+ In the window that pops up, select :

   ![War](https://github.com/user-attachments/assets/15711759-1410-4322-b0a1-194ed9d2a7db)


+ In the Application context leave the following :
   
   ![Application context](https://github.com/VladislavLevchikIsAProger/currency_exchange/assets/153897612/895091c7-dd29-49b9-8edc-c9b5f29cf018)

+ Click Apply and start Tomcat
