<h1 align="center">Sloth Java</h1>
<br>

## What is it?

Sloth is lazy, he has to automate as much as he can to be lazy. Here he is trying to get automation done with Java!

Sloth Java features/abilities to load and performance test many different applications/server/protocol types: 
 -  Web - HTTP, HTTPS 
 -  SOAP / REST Webservices
 -  FTP
 -  Database via JDBC
 -  LDAP
 -  Message-oriented Middleware (MOM) via JMS

## The Latest Version

Details of the latest version, actually all versions, can only be found on github for now.

## Requirements

The following requirements exist for running sloth-java:

*  Java Interpreter:

    A fully compliant Java Environment is required 

*  Eclipse:

    A Java IDE is helpful, expecially the main stream one. 
    
* Robot, Selenium and Appium

    ALL of the most handy automation frameworks

## Installation Instructions

 * Release builds

## Running Sloth Java

1. Import the project as a Maven project
2. Run testng.xml(could have a different name) files as TestNG Suite
3. Report will be created as test-output/EstentReport/Test-Automation-yyyymmdd.html
4. Screen shot will be created under test-output/EstentReport/ScreenShot
5. Log will be created as test-output/EstentReport/Test-Automation.log

### Windows

1. Install and config JRE and eclipse first, good to have https://projectlombok.org/
2. Install chrome and firefox latest version
    
### Notes

1. Supported OS: Windows, Mac, Linux
2. Supprorted Browser: Chrome, Firefox, IE

### Author

1. Weipeng Zheng (weipeng.zheng.ca@gmail.com)
2. Tianle Zheng (tianle.zheng.ca@gmail.com)

## Java Appium Example Scripts

These demonstration scripts allow you to run an automated Appium tests on Sauce Labs platforms.

> ###### Disclaimer:
>
> The code in these scripts is provided on an "AS-IS" basis without warranty of any kind, either express or implied, including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. These scripts are provided for educational and demonstration purposes only, and should not be used in production. Issues regarding these scripts should be submitted through GitHub. These scripts are maintained by the Technical Services team at Sauce Labs.
>
> Some examples in this repository, such as `appium-example`, `parallel-testing`, and `headless`, may require a different account tier beyond free trial. Please contact the [Sauce Labs Sales Team](https://saucelabs.com/contact) for support and information.

<br />

### Prerequisites
* Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* Install [IntelliJ](https://www.jetbrains.com/idea/download/#section=mac) (or another IDE)
* Install [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Install [Maven](https://maven.apache.org/install.html)

### Environment Setup

1. Set Global Dependencies
    * Install [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Maven](https://maven.apache.org/install.html)
    * Or Install both with [Homebrew](http://brew.sh/)
    ```
    $ brew cask install java
    $ brew install maven
    ```
    * If installed manually, [set `$JAVA_HOME` and `$M2_HOME`](https://docs.oracle.com/cd/E21454_01/html/821-2532/inst_cli_jdk_javahome_t.html)
    * Clone this repository into a directory of your choice.
    ```
    $ git clone https://github.com/saucelabs-training/demo-java.git
    ```
    * Navigate to the `demo-java/appium-example`, for example:
    ```
    $ cd demo-java/appium-example
    ```

2. Set Sauce Credentials
    * In the terminal [export your Sauce Labs Credentials as environmental variables](https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials):
    ```
    $ export SAUCE_USERNAME=<your Sauce Labs username>
    $ export SAUCE_ACCESS_KEY=<your Sauce Labs access key>
    ```
 
 <br />
 
### Running the Tests

1. Resolve package dependencies (Use `sudo` if necessary)
	```
	$ mvn dependency:resolve
	```
2. Run the following command to run tests:
	```
	$ mvn clean test -pl appium-example
	```
3. Visit the [Sauce Labs Dashboard](https://saucelabs.com/beta/dashboard/) to see the results.

<br />

### Advice and Troubleshooting

There may be additional latency when using a remote webdriver to run tests on Sauce Labs, therefore timeouts or "Waits" may need to be increased. Please read the following wiki page on [tips regarding explicit waits](https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Explicit+Waits)

<br />

##### More Information
* [Sauce Labs Documentation](https://wiki.saucelabs.com/)
* [Appium Documentation](http://appium.io/slate/en/master/)
* [JDK Tutorials and Documentation](https://blogs.oracle.com/thejavatutorials/)
* [Maven Documentation](https://maven.apache.org/guides/)

