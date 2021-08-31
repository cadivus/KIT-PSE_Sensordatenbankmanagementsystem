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
    driver.find_element_by_xpath('//*[@id="password"]').send_keys(password)
    driver.find_element_by_xpath('//*[@id="root"]/div/main/div/form/button/span[1]').submit()

# searches for Crowdsensing Node things
def search(driver):
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[1]/div[1]/div/div/div/input').send_keys('Crowdsensing Node')

# get Help for a thing
def helpThing(driver):
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[1]/td[2]/button').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div[3]/div/div[1]/button').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div[2]/div[2]/button').click()
    sleep(1)
    driver.find_element_by_xpath('/html/body/div[2]/div[3]').click()

# goes back to the startpage
def home(driver):
    driver.find_element_by_xpath('//*[@id="root"]/header/div/button[1]').click()

# logs out
def logout(driver):
    driver.find_element_by_xpath('//*[@id="root"]/header/div/button[4]/span[1]/h6').click()


if __name__ == '__main__':
    driver = webdriver.Chrome(driverpath)
    driver.maximize_window()
    driver.implicitly_wait(10)

    startpage(driver)
    login(driver, 'scenario4@test.com')
    search(driver)
    helpThing(driver)
    logout(driver)
    home(driver)
