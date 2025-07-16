# Email Sender App 📧

![Project Screenshot]<img width="938" height="1012" alt="image" src="https://github.com/user-attachments/assets/0c8857dd-6f5d-47c3-b66a-d51ad6b5be03" />


A full-stack application featuring a user-friendly React frontend and a robust Spring Boot backend. This app provides a simple and elegant interface to compose and send emails directly from your browser, powered by a RESTful API.

---

## ✨ Features

* **Modern UI:** A clean and responsive user interface built with React.
* **Powerful Backend:** A secure and efficient REST API built with Spring Boot.
* **Simple Emailing:** Send emails to any recipient with a subject and message body.
* **Easy Configuration:** Configure your SMTP server details in a single properties file.

---

## 🛠️ Tech Stack

| Frontend          | Backend         |
| ----------------- | --------------- |
| ![React](https://img.shields.io/badge/-React-61DAFB?logo=react&logoColor=white)         | ![Java](https://img.shields.io/badge/-Java-007396?logo=java&logoColor=white)           |
| ![JavaScript](https://img.shields.io/badge/-JavaScript-F7DF1E?logo=javascript&logoColor=black) | ![Spring](https://img.shields.io/badge/-Spring-6DB33F?logo=spring&logoColor=white)       |
| ![HTML5](https://img.shields.io/badge/-HTML5-E34F26?logo=html5&logoColor=white)         | ![Maven](https://img.shields.io/badge/-Maven-C71A36?logo=apache-maven&logoColor=white)       |
| ![CSS3](https://img.shields.io/badge/-CSS3-1572B6?logo=css3&logoColor=white)           |                 |

---

## 🚀 Getting Started

Follow these instructions to get a local copy of the project up and running.

### Prerequisites

Ensure you have the following software installed on your machine:
* Java Development Kit (JDK) 17 or higher
* Apache Maven
* Node.js and npm

### Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/vtaunk1932/EmailSenderApp.git](https://github.com/vtaunk1932/EmailSenderApp.git)
    cd EmailSenderApp
    ```

2.  **Backend Setup (Terminal 1):**
    ```bash
    # Navigate to the backend directory
    # (Assuming it's named 'emailsenderapp', change if necessary)
    cd emailsenderapp

    # Configure your email credentials in `src/main/resources/application.properties`
    # You MUST add your SMTP server details (host, username, password) here.

    # Run the backend server
    mvn spring-boot:run
    ```
    The backend API will now be running on `http://localhost:8080`.

3.  **Frontend Setup (Terminal 2):**
    Open a **new terminal window** for this step.
    ```bash
    # Navigate to the frontend directory
    # (Assuming it's named 'frontend', change if necessary)
    cd path/to/your/frontend

    # Install dependencies
    npm install

    # Run the React application
    npm start
    ```
    The frontend will automatically open in your browser at `http://localhost:3000`.

---

## ⚙️ Usage

Once both the backend and frontend are running, you can use the application:

1.  Open your web browser and go to `http://localhost:3000`.
2.  Fill out the recipient's email, subject, and message in the form.
3.  Click the "Send Email" button.

You will receive a notification indicating whether the email was sent successfully.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/vtaunk1932/EmailSenderApp/issues).

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 📬 Contact

**[Your Name]**

* **GitHub:** [@vtaunk1932](https://github.com/vtaunk1932)
* **LinkedIn:** [linkedin.com/in/your-linkedin-profile](https://linkedin.com/in/your-linkedin-profile)
* **Email:** [your-email@example.com](mailto:your-email@example.com)
