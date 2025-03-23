# Selenium Test on BrowserStack

## Overview
This project is a Selenium-based web scraping and testing solution designed to scrape articles from the Opinion section of the Spanish news website *El País*, translate article headers, analyze word frequency, and execute cross-browser testing using BrowserStack.

## Features
- Scrapes the first five articles from the Opinion section of *El País*.
- Extracts and prints the article titles and content in Spanish.
- Downloads and saves cover images of articles (if available).
- Translates article titles to English using Google Translate API.
- Analyzes translated headers to identify repeated words.
- Runs tests locally and on BrowserStack across multiple browsers and devices.

## Project Structure
```
src/main/java/
|-- Article.java              # Represents an article with title, content, and image URL
|-- BrowserStackTest.java     # Handles execution of Selenium tests on BrowserStack
|-- GoogleTranslatorUtil.java # Integrates Google Translate API for text translation
|-- LocalTest.java            # Runs Selenium tests locally for verification
|-- Utils.java                # Contains utility functions for web scraping and file handling
|-- WordFrequencyUtil.java    # Analyzes word frequency in translated headers
```

## Prerequisites
- Java 17
- Maven
- BrowserStack account & credentials
- Google Translate API key (or any alternative translation API)

## Setup and Installation
1. **Clone the repository**
   ```sh
   git clone <repository-url>
   cd <repository-name>
   ```
2. **Install dependencies**
   ```sh
   mvn clean install
   ```
3. **Configure API Keys & Credentials**
   - Please update (I might delete the Google project from my creds after 7 days) `GoogleTranslatorUtil.java` with your Google Translate API key.
   - Please update (I might delete the browserstack creds after 7 days) `BrowserStackTest.java` with your BrowserStack username and access key.

## Usage
### Running Tests Locally
```sh
mvn test -Dtest=LocalTest
```

### Running Tests on BrowserStack
```sh
mvn test -Dtest=BrowserStackTest
```

## Expected Output
1. Titles and content of the first five articles in Spanish.
2. Cover images downloaded to the local machine.
3. Translated article titles printed in English.
4. Word frequency analysis output for translated headers.

## Technologies Used
- Selenium WebDriver
- Java
- Maven
- Google Translate API
- BrowserStack

## License
This project is licensed under the MIT License.

## Author
Megha Varshney

