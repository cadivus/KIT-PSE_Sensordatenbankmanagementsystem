# KIT-PSE_Sensor Ultra-lightweight Supervision: Active Meteorological Observation General Use System

## Quick Start
Using the KIT-PSE_Sensor Ultra-lightweight Supervision is a five-step process:

1. Clone this repository into a directory of your choise
2. Modify environment variables in `docker-compose.yml`
3. Install Docker and Docker Compose
4. Build the project with the command `docker-compose build`
5. Run the docker-container with the command `docker-compose up`
6. Access the webapp `http://localhost:3001/`

## How to use the webseite
To have the best user experience, it is important to **log in** first. For this you only need an email address. To access your account, you will receive a new login code for verification every time you log in.

After you have logged in, you can either **subscribe** to one or more things. Then, depending on your choice, you will receive an email if the specified thing is down or a regular report about this thing.\
Alternatively you can **replay** one or more things. Using the replay page you can now decide about the time period and the speed with which the replay should be executed.\
You can also **export** the data of a datastream over a period of time as a CSV file on the information page of a datastream.

## Coverage
Code coverage backend:              ![Coverage](.github/badges/jacoco1.svg) ![Branches](.github/badges/branches1.svg) \
Code coverage notification system:  ![Coverage](.github/badges/jacoco2.svg) ![Branches](.github/badges/branches2.svg) \
Code coverage frontend:  ![Functions](.github/badges/frontend/badge-functions.svg) ![Branches](.github/badges/frontend/badge-branches.svg) 
