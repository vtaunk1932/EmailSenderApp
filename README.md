# Email Sender App

A simple RESTful API built with Spring Boot that allows developers to send emails via a simple HTTP POST request.

## ✨ Features

* Send email to a single recipient.
* Simple JSON-based request body.
* Easy to configure for any SMTP server (e.g., Gmail, SendGrid).

## 🛠️ Technologies Used

* Java 17
* Spring Boot 3
* Maven
* Spring Mail

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

You will need the following software installed:
* Java Development Kit (JDK) 17 or higher
* Apache Maven

### Installation

1.  **Clone the repository**
    ```bash
    git clone [https://github.com/vtaunk1932/EmailSenderApp.git](https://github.com/vtaunk1932/EmailSenderApp.git)
    ```
2.  **Navigate to the project directory**
    ```bash
    cd EmailSenderApp
    ```
3.  **Configure your email credentials**
    Open `src/main/resources/application.properties` and add your SMTP server details and credentials.
    ```properties
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=your-email@gmail.com
    spring.mail.password=your-app-password # Use an App Password for Gmail
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    ```
4.  **Run the application**
    ```bash
    mvn spring-boot:run
    ```
The server will start on `http://localhost:8080`.

## ⚙️ Usage

Send a `POST` request to `/api/email/send` with a JSON body containing the `to`, `subject`, and `message`.

### Example Request using cURL

```bash
curl -X POST \
  http://localhost:8080/api/email/send \
  -H 'Content-Type: application/json' \
  -d '{
    "to": "recipient@example.com",
    "subject": "Greetings!",
    "message": "This is a test email sent from the Spring Boot application."
  }'
