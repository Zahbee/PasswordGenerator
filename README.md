# 🔐 Simple Java Password Generator

A basic password generator built using Java Swing. It allows users to choose the password length and select character types (uppercase, lowercase, numbers, and special characters). The app generates a random password based on these settings and includes a button to copy the password to the clipboard. Simple UI, easy to use.

---

## ✨ Features

- Set desired password **length**
- Choose which character types to include:
  - Uppercase letters (A–Z)
  - Lowercase letters (a–z)
  - Numbers (0–9)
  - Special characters (!@#$%^&*()-_=+<>?)
- **Generate** a secure password at the click of a button
- **Copy** the generated password to your clipboard instantly
- Simple, clean, and intuitive **graphical interface (Swing)**

---

## 🖼️ User Interface

Built using **Java Swing**, the UI includes:

- `JFrame` as the main window
- `JLabels` for form descriptions (e.g., "Password Length", "Generated Password")
- `JSpinner` or `JTextField` for entering password length
- `JCheckBoxes` to select character types (uppercase, lowercase, numbers, special characters)
- `JButton` to generate the password
- `JTextField` to display the generated password (non-editable)
- `JButton` to copy the password to clipboard

Layout managers like `GridBagLayout` are used for a neat and flexible component arrangement.

---

## 🧠 Password Generation Logic

- Character sets:
  - Lowercase: `abcdefghijklmnopqrstuvwxyz`
  - Uppercase: `ABCDEFGHIJKLMNOPQRSTUVWXYZ`
  - Numbers: `0123456789`
  - Special: `!@#$%^&*()-_=+<>?`
- On clicking the **Generate** button:
  - Fetch user input for length and selected character types
  - Combine selected character sets
  - Use `SecureRandom` to generate a random password
  - Password is constructed by selecting characters from the combined pool

> **Note:** For simplicity, each password is generated randomly from the selected pool. Guaranteed inclusion of at least one character from each selected category is not implemented in this basic version.

---

## 📋 Copy to Clipboard

- Utilizes:
  - `java.awt.Toolkit`
  - `java.awt.datatransfer.Clipboard`
  - `java.awt.datatransfer.StringSelection`
- The "Copy" button copies the generated password to the clipboard using the system clipboard API.

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/password-generator-java.git

