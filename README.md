# KIT-PSE_Sensor Ultra-lightweight Supervision: Active Meteorological Observation General Use System

## About this project

This project was developed in the context of the course Software Engineering Practice at Karlsruhe Institute of Technology.
One of the formal requirements that had to be fulfilled was the implementation of a waterfall based, object-oriented approach. The project was implemented by a team of 5 students.
Design documents and requirements are not part of this repository.
The only given technical requirement was to use Java for the main part.

## Quick start
Using the KIT-PSE_Sensor Ultra-lightweight Supervision is a six-step process:

1. Clone this repository into a directory of your choise
2. Modify environment variables in `docker-compose.yml`
3. Install Docker and Docker Compose
4. Build the project with the command `docker-compose build`
5. Run the docker-container with the command `docker-compose up`
6. Access the webapp `http://localhost:3001/`

There may be some issues with using Docker with Windows for deploying the system.
It definitely works for Linux environments. We didn't experience problems with Intel Macs too.

## Configuration

The configuration can completely be changed in `docker-compose.yml`, when you use docker for deploying the project.

### Database connection settings
The variables  
`spring.datasource.url` url of the database  
`spring.datasource.username` username of the database user  
`spring.datasource.password` password of the database user  
are used to specify the database connection.

Advanced settings can be found in the `application.properties` file in `notification/src/main/resources`.
These settings can be overridden by `docker-compose.yml` too.

### System e-mail address
The e-mail address with which the alert, report, and confirmation code e-mails are sent can be changed using Docker's environment variables
in the `docker-compose.yml` file.  
The variables `MAIL_USERNAME` and `MAIL_PASSWORD` contain the e-mail address and password.  
This allows users to have the e-mails sent from custom mail addresses. Note however, that the mails are sent with Google's GMail. 
The custom mails must therefore also be GMail addresses.
Furthermore, to use your custom GMail address, you must allow less secure settings on that account. On your GMail account, go to 
Settings > Accounts and Import > Other Google Account settings. Under Security, scroll down and enable access for less secure apps. Otherwise,
GMail blocks automated mail sending attempts.  
If you want to use other mail addresses other than GMail for sending the system's mail, you can change the smtp-server and port with the
Docker environment variables
`MAIL_SMTP_HOST`, `MAIL_SMTP_PORT`, and `MAIL_SMTP_SOCKETFACTORY_PORT`  
in the `docker-compose.yml` file.  Currently, only STARTTLS is supported.

If you don't want to use Docker, you can use the `application.properties` file in `notification/src/main/resources`.

## Using the project
### How to use the website
To have the best user experience, it is important to **log in** first. For this you only need an email address. To access your account, you will receive a new login code for verification every time you log in.

After you have logged in, you can either **subscribe** to one or more things. Then, depending on your choice, you will receive an email if the specified thing is down or a regular report about this thing.\
Alternatively you can **replay** one or more things. Using the replay page you can now decide about the time period and the speed with which the replay should be executed.\
You can also **export** the data of a datastream over a period of time as a CSV file on the information page of a datastream.

### How to use the Replay
The replay page can be used to make the settings for the replay. After pressing Play you will see a link to the actual replay. 
link to the actual replay will be displayed. As soon as you open this link for the first time, the replay will start.

If you want to start the replay without the web page, you can also do this. For this you have to make a POST Http request
to http://localhost:3001/api/backend/observation/newSSE. The content type is application/json. As JSON
the settings will be passed manually.  
The JSON file has the format:
```
{"start": "DD/MM/YYYY HH:MN:SS", "end": "DD/MM/YYYY HH:MN:SS", "speed":NUM, "sensors":["thingID"]}
```
as an example with the dates:  
Start: `21/09/2021 12:00:00`  
End: `23/09/2021 12:00:00`  
Speed: `100`  
Sensors: `saqn:t:43ae704`  
```JSON
{"start": "21/09/2021 12:00:00", "end": "23/09/2021 12:00:00", "speed":100, "sensors":["saqn:t:43ae704"]}
```

## Coverage
Code coverage backend:              ![Coverage](.github/badges/jacoco1.svg) ![Branches](.github/badges/branches1.svg) \
Code coverage notification system:  ![Coverage](.github/badges/jacoco2.svg) ![Branches](.github/badges/branches2.svg) \
Code coverage frontend:  ![Functions](.github/badges/frontend/badge-functions.svg) ![Branches](.github/badges/frontend/badge-branches.svg) 
