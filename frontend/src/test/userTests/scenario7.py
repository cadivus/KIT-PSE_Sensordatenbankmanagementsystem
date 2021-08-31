from time import sleep
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
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

# subscribes to the first 4 things with default settings
def replayMultiple(driver):
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[1]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[2]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[3]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[4]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[5]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[6]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[7]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[8]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[9]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[2]/table/tbody/tr[10]/td[1]/span/span[1]/input').click()
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div[1]/div[3]/div/button').click()
    setSettings(driver)
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div[2]/div[1]/button').submit()

# sets settings
def setSettings(driver):
    driver.find_element_by_xpath('//*[@id="datetime-start"]').send_keys('1')
    slider = driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div[2]/div[1]/div/table/tbody/tr[3]/td[1]/span').click()
    sleep(1)
    slider.send_keys(Keys.ARROW_RIGHT)
    sleep(1)
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/div/div/div[2]/div[1]/button/span[1]').click()

# logs out
def logout(driver):
    driver.find_element_by_xpath('//*[@id="root"]/header/div/button[4]/span[1]/h6').click()


if __name__ == '__main__':
    driver = webdriver.Chrome(driverpath)
    driver.maximize_window()
    driver.implicitly_wait(10)

    startpage(driver)
    login(driver, 'scenario7@test.com')
    replayMultiple(driver)
    logout(driver)
