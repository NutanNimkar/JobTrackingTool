# JobTrackingTool

JobTrackingTool is a web application designed to simplify job application management. It helps users log and track job applications efficiently by integrating a React-based frontend with a Spring Boot backend that connects to the Google Sheets API.

---

## Features

- **Add Job Details:** Log job roles, company names, statuses (e.g., Waiting, Offer), and application dates.
- **Real-Time Updates:** Automatically updates a Google Sheet with the entered data.
- **Responsive Design:** A clean and user-friendly interface built with Material-UI.
- **Error Handling:** Validates input data with sensible defaults.

---

## Technologies Used

### Frontend
- **React**: For building the user interface.
- **Material-UI**: For responsive and stylish components.
- **Axios**: To handle API requests.

### Backend
- **Spring Boot**: To manage API requests and handle backend logic.
- **Java**: The primary backend programming language.
- **Google Sheets API**: To store and retrieve data dynamically.

---

## How to Run Locally

### Prerequisites
- Node.js and npm installed.
- Java and Maven installed.
- A Google Cloud project with Sheets API enabled.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/JobTrackingTool.git
   cd JobTrackingTool
2. Start backend
   ```bash
   cd backend
3. Start backend
   ```bash
   mvn spring-boot:run

4. Start frontend
   ```bash
   cd frontend
4. Start frontend
   ```bash
   npm install
   npm start

