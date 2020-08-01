*** Settings ***
Documentation     A test suite with a single test for valid login.
...
...               This test has a workflow that is created using keywords in
...               the imported resource file.
Resource          resource.robot

*** Test Cases ***
Valid Login
    Given browser is opened to login page
    When user "${username}" logs in with password "${password}"
    Then healthcheck Page Should Be Open

*** Keywords ***
Browser is opened to login page
    Open Browser To Login Page

User "${username}" logs in with password "${password}"
    Input username    ${username}
    Input password    ${password}
    Submit Credentials
    Wait Until Page Contains Element    name:file    20
