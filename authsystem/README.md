# 🔒 AuthShield – Secure Login Authorization System

## 📌 Overview

AuthShield is a full-stack authentication system that enhances login security by requiring users to approve every login attempt through email before access is granted.

Instead of logging users in immediately after entering valid credentials, the system sends an approval email containing **Approve** and **Reject** options. Access is granted only after approval, adding an extra layer of protection.

---

## ✨ Features

- User Registration
- Secure Login
- Email-based Login Approval
- Approve / Reject Login Request
- JWT Authentication
- Password Encryption using BCrypt
- Login History
- Browser Detection
- IP Address Tracking
- Location Detection
- Dashboard
- Protected Routes
- Responsive React UI

---

## 🛠 Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- JavaMailSender
- MySQL
- Maven

### Frontend
- React
- Vite
- Tailwind CSS
- Axios
- React Router

---

## 📂 Project Structure

```
Backend
├── Controller
├── Service
├── Repository
├── Entity
├── DTO
├── Security

Frontend
├── Pages
├── Services
├── Components
```

---

## 🔄 Project Workflow

```
Register
      ↓
Login
      ↓
Approval Email
      ↓
Approve / Reject
      ↓
JWT Generated
      ↓
Dashboard
      ↓
Login History
```

---

## 🔐 Security Features

- BCrypt Password Hashing
- JWT Authentication
- Email Verification
- Login Approval
- Secure REST APIs

---

## 📸 Screenshots

- Login Page
- Register Page
- Waiting Page
- Approval Email
- Dashboard
- Login History

(Add screenshots here)

---

## 🚀 Future Improvements

- Two-Factor Authentication (2FA)
- Google OAuth Login
- Refresh Tokens
- Admin Dashboard
- Device Management
- Login Notifications

---

## 👩‍💻 Author

Samridhi Sharma