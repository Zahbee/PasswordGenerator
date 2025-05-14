import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGeneratorApp {

    // Character sets
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+<>?";

    // UI Components
    private JFrame frame;
    private JSpinner lengthSpinner;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox lowercaseCheckBox;
    private JCheckBox numbersCheckBox;
    private JCheckBox specialCharsCheckBox;
    private JTextField passwordField;
    private JButton generateButton;
    private JButton copyButton;

    public PasswordGeneratorApp() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Password Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Password Length
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Password Length:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2; // Span across 2 columns
        SpinnerModel lengthModel = new SpinnerNumberModel(12, 4, 128, 1); // Default 12, min 4, max 128, step 1
        lengthSpinner = new JSpinner(lengthModel);
        frame.add(lengthSpinner, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Character Type Checkboxes
        gbc.gridx = 0;
        gbc.gridy = 1;
        lowercaseCheckBox = new JCheckBox("Include Lowercase (a-z)", true);
        frame.add(lowercaseCheckBox, gbc);

        gbc.gridx = 1;
        uppercaseCheckBox = new JCheckBox("Include Uppercase (A-Z)", true);
        frame.add(uppercaseCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        numbersCheckBox = new JCheckBox("Include Numbers (0-9)", true);
        frame.add(numbersCheckBox, gbc);

        gbc.gridx = 1;
        specialCharsCheckBox = new JCheckBox("Include Special (!@#$%)", true);
        frame.add(specialCharsCheckBox, gbc);

        // Generate Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        generateButton = new JButton("Generate Password");
        frame.add(generateButton, gbc);

        // Generated Password Display
        gbc.gridy = 4;
        passwordField = new JTextField(20); // Adjust width as needed
        passwordField.setEditable(false);
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        frame.add(passwordField, gbc);

        // Copy Button
        gbc.gridy = 5;
        copyButton = new JButton("Copy to Clipboard");
        frame.add(copyButton, gbc);
        gbc.gridwidth = 1; // Reset

        // --- Event Listeners ---
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyPasswordToClipboard();
            }
        });

        frame.pack(); // Adjusts window size to fit components
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    private void generatePassword() {
        int length = (Integer) lengthSpinner.getValue();
        StringBuilder characterPool = new StringBuilder();
        List<Character> passwordChars = new ArrayList<>();
        SecureRandom random = new SecureRandom();

        // Build the character pool and ensure at least one char from selected types
        if (lowercaseCheckBox.isSelected()) {
            characterPool.append(LOWERCASE_CHARACTERS);
            if (length > passwordChars.size()) { // Ensure we don't add more mandatory chars than length
                passwordChars.add(LOWERCASE_CHARACTERS.charAt(random.nextInt(LOWERCASE_CHARACTERS.length())));
            }
        }
        if (uppercaseCheckBox.isSelected()) {
            characterPool.append(UPPERCASE_CHARACTERS);
             if (length > passwordChars.size()) {
                passwordChars.add(UPPERCASE_CHARACTERS.charAt(random.nextInt(UPPERCASE_CHARACTERS.length())));
            }
        }
        if (numbersCheckBox.isSelected()) {
            characterPool.append(NUMERIC_CHARACTERS);
            if (length > passwordChars.size()) {
                passwordChars.add(NUMERIC_CHARACTERS.charAt(random.nextInt(NUMERIC_CHARACTERS.length())));
            }
        }
        if (specialCharsCheckBox.isSelected()) {
            characterPool.append(SPECIAL_CHARACTERS);
            if (length > passwordChars.size()) {
                passwordChars.add(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
            }
        }

        if (characterPool.length() == 0) {
            passwordField.setText("Select character types!");
            return;
        }

        // Fill the rest of the password length from the combined pool
        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(characterPool.charAt(random.nextInt(characterPool.length())));
        }

        // Shuffle the characters to ensure randomness of positions
        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        for (Character ch : passwordChars) {
            password.append(ch);
        }

        passwordField.setText(password.toString());
    }

    private void copyPasswordToClipboard() {
        String password = passwordField.getText();
        if (password != null && !password.isEmpty()) {
            StringSelection stringSelection = new StringSelection(password);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            // Optionally, provide user feedback (e.g., a temporary message or change button text)
            JOptionPane.showMessageDialog(frame, "Password copied to clipboard!", "Copied", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "No password generated to copy.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Ensure GUI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PasswordGeneratorApp();
            }
        });
    }
}