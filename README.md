# Automation Exercises – Selenium Automation Framework

A Java-based test automation framework for automating UI and API test scenarios on the Automation Exercise website.

## Prerequisites

Before running the project, make sure you have:

- Java JDK 21 or later
- Maven
- IntelliJ IDEA (recommended)
- Google Chrome browser

## Getting Started

**1. Clone the repository**

```bash
git clone https://github.com/AMAhmednasr/AutomationExcersies.git
```

**2. Open the project**

- Open IntelliJ IDEA.
- Select **Open** and choose the cloned project folder.
- Wait for Maven to download the required dependencies.

**3. Configure the project**

- Check the test configuration and required properties.
- Make sure your browser and Java environment are configured correctly.

**4. Run the tests**

From the terminal, execute:

```bash
mvn clean test
```

Or run the TestNG test classes directly from IntelliJ IDEA.

## Framework Features

- Selenium WebDriver with Page Object Model (POM)
- TestNG test execution
- UI and API automation
- Data-driven testing using JSON
- Reusable utilities and explicit waits
- Automatic screenshots and screen recording
- Logging and Allure reporting

## Test Scenarios

- User Registration and Login
- Product Browsing
- Shopping Cart
- Checkout and Payment
- API Registration

## Test Reports

Allure results are generated during test execution.

To generate and open the report, use:

```bash
allure serve allure-results
```

## Author

**Ahmed Abdelmoneim**

GitHub: https://github.com/AMAhmednasr