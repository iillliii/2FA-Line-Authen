*** Settings ***
Documentation     A test suite with a single test for valid login.
...
...               This test has a workflow that is created using keywords in
...               the imported resource file.
Resource          resource.robot

*** Test Cases ***
Upload Checkfile
    Given csv file to process healthcheck
    When user click "submit" button
    Then result Page Should Be Open

*** Keywords ***
Csv file to process healthcheck
    Choose File    name:file    ${EXECDIR}${/}sample.csv

User click "${button}" button
    Submit Form
    Wait Until Page Contains Element    class:result    20