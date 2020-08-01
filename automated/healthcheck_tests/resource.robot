*** Settings ***
Documentation     A resource file with reusable keywords and variables.
...
...               The system specific keywords created here form our own
...               domain specific language. They utilize keywords provided
...               by the imported SeleniumLibrary.
Library           SeleniumLibrary

*** Variables ***
${SERVER}         localhost:8080
${BROWSER}        Chrome
${DELAY}          0
${VALID USER}     demo
${VALID PASSWORD}    mode
${LOGIN URL}      https://access.line.me/oauth2/v2.1/login?
${HEALTHCHECK URL}    http://${SERVER}/index
${RESULT URL}    http://${SERVER}/processCheck
${WELCOME URL}    http://${SERVER}/index
${ERROR URL}      http://${SERVER}/error.html

*** Keywords ***
Open Browser To Login Page
    Open Browser    ${HEALTHCHECK URL}    ${BROWSER}
    Maximize Browser Window
    Set Selenium Speed    ${DELAY}
    Login Page Should Be Open

Login Page Should Be Open
    Title Should Be    LINE Login

Go To Healthcheck Page
    Go To    ${HEALTHCHECK URL}

Go To Login Page
    Go To    ${LOGIN URL}
    Login Page Should Be Open

Input Username
    [Arguments]    ${username}
    Input Text    tid    ${username}

Input Password
    [Arguments]    ${password}
    Input Text    tpasswd    ${password}

Submit Credentials
    Submit Form

Healthcheck Page Should Be Open
    Location Should Be    ${HEALTHCHECK URL}
    Title Should Be    HEALTHCHECK

Result Page Should Be Open
    Title Should Be    HEALTHCHECK - RESULT