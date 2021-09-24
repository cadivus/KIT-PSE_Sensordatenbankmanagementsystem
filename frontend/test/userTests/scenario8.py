from selenium import webdriver
import pathlib

driverpath = str(pathlib.Path().resolve()) + '/chromedriver'

# opens the startpage
def startpage(driver):
    driver.get("http://localhost:3001")
    assert "KIT PSE sensor management system" in driver.title

# logs in with wrong password
def login_wrong(driver, mail):
    driver.find_element_by_xpath('//*[@id="root"]/header/div/button[4]/span[1]/h6').click()
    driver.find_element_by_xpath('//*[@id="email"]').send_keys(mail)
    driver.find_element_by_xpath('//*[@id="root"]/div/main/div/form/button').submit()
    driver.find_element_by_xpath('//*[@id="password"]').send_keys('worng password')
    driver.find_element_by_xpath('//*[@id="root"]/div/main/div/form/button/span[1]').submit()

# retries login after entering the password
def login(driver, mail):
    driver.find_element_by_xpath('//*[@id="email"]').send_keys(mail)
    driver.find_element_by_xpath('//*[@id="root"]/div/main/div/form/button').submit()
    print('Bitte Passwort eingeben um fortzufahren: ')
    password = input()
    driver.find_element_by_xpath('//*[@id="password"]').send_keys(password)
    driver.find_element_by_xpath('//*[@id="root"]/div/main/div/form/button/span[1]').submit()

# logs out
def logout(driver):
    driver.find_element_by_xpath('//*[@id="root"]/header/div/button[4]/span[1]/h6').click()


if __name__ == '__main__':
    driver = webdriver.Chrome(driverpath)
    driver.maximize_window()
    driver.implicitly_wait(10)

    startpage(driver)
    login_wrong(driver, 'scenario8@test.com')
    login(driver, 'scenario8@test.com')
    logout(driver)
