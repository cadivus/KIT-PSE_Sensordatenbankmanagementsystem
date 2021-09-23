# KIT-PSE_Sensor Ultra-lightweight Supervision: Active Meteorological Observation General Use System

## Quick Start
Using the KIT-PSE_Sensor Ultra-lightweight Supervision is a five-step process:

1. Clone this repo into a directory of your choise
2. Install Docker
3. Build the Repo with the command "docker-compose build"
4. Run the docker-container with the command "docker-compose up"
5. Access the website with the url "http://localhost:3001/

## How to use the webseite
To have the best user experience, it is important to **log in** first. For this you only need an email address. To access your account, you will be sent a new login code for verification every time you log in. \\

After you have logged in, you can either **subscribe** to one or more Things. Then, depending on your choice, you will receive an email if the Thing is down, or a regular report about the Thing.\
Alternatively you can **replay** one or more Things. On the replay page you can now decide about the time period and the speed with which the replay should be executed.\
You can also **export** the data of a datastream over a period of time as a CSV file on the information page of a datastream.

## Coverage
Code coverage backend:              ![Coverage](.github/badges/jacoco1.svg) ![Branches](.github/badges/branches1.svg) \
Code coverage notification system:  ![Coverage](.github/badges/jacoco2.svg) ![Branches](.github/badges/branches2.svg) \
Code coverage frontend:  ![Functions](.github/badges/frontend/badge-functions.svg) ![Branches](.github/badges/frontend/badge-branches.svg) 
