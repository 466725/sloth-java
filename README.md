<h1 align="center">Sloth Java</h1>
<br>

## What is it?

Sloth is lazy, he has to automate as much as he can to be lazy. Here he is trying to get automation done with Java!

Sloth Java features/abilities to load and performance test many different applications/server/protocol types:

- Web - HTTP, HTTPS
- SOAP / REST Webservices
- FTP
- Database via JDBC
- LDAP
- Message-oriented Middleware (MOM) via JMS

## Running Sloth Java

- Import the project as a Maven project
- Run testng.xml(could have a different name) files as TestNG Suite
- Report will be created as test-output/EstentReport/Test-Automation-yyyymmdd.html
- Screenshot will be created under test-output/EstentReport/ScreenShot
- Log will be created as test-output/EstentReport/Test-Automation.log

### Prerequisites

* Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* Install [IntelliJ](https://www.jetbrains.com/idea/download/#section=mac) (or another IDE)
* Install [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Install [Maven](https://maven.apache.org/install.html)

### Environment Setup

1. Set Global Dependencies
    * Install [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
      and [Maven](https://maven.apache.org/install.html)
    * Or Install both with [Homebrew](http://brew.sh/)
    ```
    $ brew cask install java
    $ brew install maven
    ```
    * If installed manually, [set `$JAVA_HOME` and
      `$M2_HOME`](https://docs.oracle.com/cd/E21454_01/html/821-2532/inst_cli_jdk_javahome_t.html)
    * Clone this repository into a directory of your choice.
    ```
    $ git clone https://github.com/saucelabs-training/demo-java.git
    ```
    * Navigate to the `demo-java/appium-example`, for example:
    ```
    $ cd demo-java/appium-example
    ```

2. Set Sauce Credentials
    * In the
      terminal [export your Sauce Labs Credentials as environmental variables](https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials):
    ```
    $ export SAUCE_USERNAME=<your Sauce Labs username>
    $ export SAUCE_ACCESS_KEY=<your Sauce Labs access key>
    ```

3. Modify below part of maven settings.xml to use JDK 25 if you are using JDK 25
   <profiles>
   <profile>
   <id>jdk-25</id>
   <activation>
   <activeByDefault>true</activeByDefault>
   </activation>
   <properties>
   <java.version>25</java.version>
   <maven.compiler.source>25</maven.compiler.source>
   <maven.compiler.target>25</maven.compiler.target>
   <maven.compiler.release>25</maven.compiler.release>
   </properties>
   </profile>
   </profiles>
   <activeProfiles>
   <activeProfile>jdk-25</activeProfile>
   </activeProfiles>

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

There may be additional latency when using a remote webdriver to run tests on Sauce Labs, therefore, timeouts or "Waits"
may need to be increased. Please read the following wiki page
on [tips regarding explicit waits](https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Explicit+Waits)
<br />

### Author

1. Weipeng Zheng (weipeng.zheng.ca@gmail.com)
2. Tianle Zheng (tianle.zheng.ca@gmail.com)

##### More Information

* [Sauce Labs Documentation](https://wiki.saucelabs.com/)
* [Appium Documentation](http://appium.io/slate/en/master/)
* [JDK Tutorials and Documentation](https://blogs.oracle.com/thejavatutorials/)
* [Maven Documentation](https://maven.apache.org/guides/)

Created Job-hunting branch for job interview and git practice purpose. 