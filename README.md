# Healthcheck

Healthcheck is a Spring boot app for LINE hiring challenge which implemented LINE OAuth2. It can retry to send http post result report to [https://backend-challenge.line-apps.com/](https://backend-challenge.line-apps.com/) for 3 times if network issue occurred.

## Package
This package contains
- healthcheck (source code)
- healthcheck.jar 
- automated
- readme.md

## Installation (OS X)
Install some libraries that required for run automated test.

1. Go to the automated package and use python3 and pip to install robotFramework.

```bash
$ cd <package dir>/automated
$ python3 -m pip install -r requirements.txt
$ robot --version
$ Robot Framework 3.1.2 (Python 3.7.6 on darwin)
```
2. Make sure "brew" is already installed in your system, if no then go [here](https://brew.sh).
```bash
$ brew --version
```
3. Install chromedriver via brew if you don't have one go [here](https://www.python.org/downloads/windows/).
```bash
$ brew cask install chromedriver
```

## Installation (Windows)
Install some libraries that required for run automated test.
1. Check python version if no installed python in your system go [here](https://www.python.org/downloads/windows/).
```cmd
python --version
```
2. Go to the application package.

```bash
cd <package dir>\automated
pip install -r requirements.txt
robot --version
```
3. Download latest chrome driver [here](https://sites.google.com/a/chromium.org/chromedriver/downloads).
4. Unzip then add chromedriver.exe into PATH environment. 
## Run automated test (Both OS)
Before run automated test first thing you need to set up some variable by going to
```bash
$ cd <package dir>
$ java -jar healthcheck.jar
$ cd automated
$ robot --variable username:LINE_LOGIN_EMAIL --variable password:LINE_LOGIN_PASSWORD healthcheck_tests
```
Another way to access application after started, you can access to [http://localhost:8080](http://localhost:8080) and do healthcheck manually. The healthcheck result will appeared in result.log.

## Summary
The Healthcheck app is an application that you can check the website list from csv file, The csv file that you give to process should contain website list and must have format the same as sample.csv inside automated directory. After process completed it will send the healthcheck report via POST to endpoint which given in requirement.
