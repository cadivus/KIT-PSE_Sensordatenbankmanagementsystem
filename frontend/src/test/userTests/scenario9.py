import random
from time import sleep
from selenium import webdriver
import pathlib

path = pathlib.Path().resolve()
driverpath = str(path) + '/chromedriver'

# opens the startpage
def startpage(driver):
    driver.get("http://localhost:3001")
    assert "KIT PSE sensor management system" in driver.title

# logs in after entering the password
def login(driver, mail):
    driver.find_element_by_xpath('//*[@id="root"]/header/div/button[4]/span[1]/h6').click()
    driver.find_element_by_xpath('//*[@id="email"]').send_keys(mail)
    driver.find_element_by_xpath('//*[@id="root"]/div/main/div/form/button').submit()
    print('Bitte Passwort eingeben um fortzufahren: ')
    password = input()
    for entry in driver.get_log('browser'):
        print(entry)
    driver.find_element_by_xpath('//*[@id="password"]').send_keys(password)
    driver.find_element_by_xpath('//*[@id="root"]/div/main/div/form/button/span[1]').submit()

# subscribes to the first 3 Things with default settings
def subscribeMultiple(driver):
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[1]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[2]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[3]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[1]/div[2]/div/button/span[1]').click()
    randomSettings(driver)
    sleep(1)
    driver.find_element_by_xpath('//*[@id="root"]/header/div/button[1]').click()


# subsribe to a random Thing with random settings
def subscribeSingle(driver):
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[4]/td[2]/button').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div[3]/div/div[2]/button').click()
    randomSettings(driver)

def randomSettings(driver):
    driver.find_element_by_xpath('//*[@id="standard-number"]').clear()
    driver.find_element_by_xpath('//*[@id="standard-number"]').send_keys(1)
    rand1 = random.randint(0, 1)
    rand2 = random.randint(0, 1)
    if rand1:
        driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div/div/table/tbody/tr[2]/th/span/span[1]/input').click()
    if rand2:
        driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div/div/table/tbody/tr[1]/th/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/button').click()


if __name__ == '__main__':
    driver = webdriver.Chrome(driverpath)
    driver.maximize_window()
    driver.implicitly_wait(10)

    startpage(driver)
    login(driver, 'mail@test.com1')
    subscribeMultiple(driver)
    subscribeSingle(driver)
