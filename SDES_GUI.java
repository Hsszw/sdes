import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SDES_GUI extends JFrame {
    private JRadioButton asciiButton, binaryButton;
    private JButton encryptButton, decryptButton;
    private JTextField inputText, keyText, outputText;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SDES_GUI::new);
    }

    public SDES_GUI() {
        setTitle("S-DES加解密");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 明文输入框
        JLabel inputLabel = new JLabel("请输入明文：");
        inputText = new JTextField(20);
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(inputLabel);
        inputPanel.add(inputText);

        // 密钥输入框
        JLabel keyLabel = new JLabel("请输入密钥（10位二进制数）：");
        keyText = new JTextField(10);
        JPanel keyPanel = new JPanel(new FlowLayout());
        keyPanel.add(keyLabel);
        keyPanel.add(keyText);

        // 加密和解密按钮
        encryptButton = new JButton("加密");
        decryptButton = new JButton("解密");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        // 加密方式选择
        asciiButton = new JRadioButton("ASCII编码加密", true);
        binaryButton = new JRadioButton("二进制加密");
        ButtonGroup encryptionGroup = new ButtonGroup();
        encryptionGroup.add(asciiButton);
        encryptionGroup.add(binaryButton);
        JPanel encryptionPanel = new JPanel(new FlowLayout());
        encryptionPanel.add(asciiButton);
        encryptionPanel.add(binaryButton);

        // 输出框
        JLabel outputLabel = new JLabel("输出：");
        outputText = new JTextField(20);
        outputText.setEditable(false);
        JPanel outputPanel = new JPanel(new FlowLayout());
        outputPanel.add(outputLabel);
        outputPanel.add(outputText);

        // 添加组件到窗口中
        setLayout(new GridLayout(5, 1));
        add(inputPanel);
        add(keyPanel);
        add(buttonPanel);
        add(encryptionPanel);
        add(outputPanel);

        // 注册加密和解密按钮的监听器
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();

                if (key.length() != 10) {
                    JOptionPane.showMessageDialog(null, "请输入正确的密钥！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder ciphertext = new StringBuilder();
                boolean isASCII = asciiButton.isSelected();
                if (isASCII) {
                    for (int i = 0; i < input.length(); i++) {
                        char c = input.charAt(i);
                        int plaintext = (int) c;
                        int keyInt = Integer.parseInt(key, 2);
                        int encrypted = SDES.encrypt(plaintext, keyInt);
                        ciphertext.append((char) encrypted);
                        outputText.setText(ciphertext.toString());
                    }
                } else {
                    int plaintext = Integer.parseInt(input, 2);
                    int keyInt = Integer.parseInt(key, 2);
                    int Bciphertext = SDES.encrypt(plaintext, keyInt);
                    outputText.setText(String.format("%8s", Integer.toBinaryString(Bciphertext)).replace(' ', '0'));
                }


            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();

                if (key.length() != 10) {
                    JOptionPane.showMessageDialog(null, "请输入正确的密钥！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder plaintext = new StringBuilder();
                boolean isASCII = asciiButton.isSelected();
                if (isASCII) {
                    for (int i = 0; i < input.length(); i++) {
                        char c = input.charAt(i);
                        int ciphertext = (int) c;
                        int keyInt = Integer.parseInt(key, 2);
                        int decrypted = SDES.decrypt(ciphertext, keyInt);
                        plaintext.append((char) decrypted);
                        outputText.setText(plaintext.toString());
                    }
                } else {
                    int ciphertext = Integer.parseInt(input, 2);
                    int keyInt = Integer.parseInt(key, 2);
                    int Bplaintext = SDES.decrypt(ciphertext, keyInt);
                    outputText.setText(String.format("%8s", Integer.toBinaryString(Bplaintext)).replace(' ', '0'));
                }


            }
        });


        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();

                if (key.length() != 10) {
                    JOptionPane.showMessageDialog(null, "请输入正确的密钥！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder plaintext = new StringBuilder();
                boolean isASCII = asciiButton.isSelected();
                if (isASCII) {
                    for (int i = 0; i < input.length(); i++) {
                        char c = input.charAt(i);
                        int ciphertext = (int) c;
                        int keyInt = Integer.parseInt(key, 2);
                        int decrypted = SDES.decrypt(ciphertext, keyInt);
                        plaintext.append((char) decrypted);
                        outputText.setText(plaintext.toString());
                    }
                } else {
                    int ciphertext = Integer.parseInt(input, 2);
                    int keyInt = Integer.parseInt(key, 2);
                    int Bplaintext = SDES.decrypt(ciphertext, keyInt);
                    outputText.setText(Integer.toBinaryString(Bplaintext));
                }


            }
        });

        // 显示窗口
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

//ASCII码加密
/*
public class SDES_GUI extends JFrame {
    private JButton encryptButton, decryptButton;
    private JTextField inputText, keyText, outputText;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SDES_GUI::new);
    }

    public SDES_GUI() {
        setTitle("S-DES加解密");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 明文输入框
        JLabel inputLabel = new JLabel("请输入明文（ASCII编码字符串）：");
        inputText = new JTextField(20);
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(inputLabel);
        inputPanel.add(inputText);

        // 密钥输入框
        JLabel keyLabel = new JLabel("请输入密钥（10位二进制数）：");
        keyText = new JTextField(10);
        JPanel keyPanel = new JPanel(new FlowLayout());
        keyPanel.add(keyLabel);
        keyPanel.add(keyText);

        // 加密和解密按钮
        encryptButton = new JButton("加密");
        decryptButton = new JButton("解密");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        // 输出框
        JLabel outputLabel = new JLabel("输出：");
        outputText = new JTextField(20);
        outputText.setEditable(false);
        JPanel outputPanel = new JPanel(new FlowLayout());
        outputPanel.add(outputLabel);
        outputPanel.add(outputText);

        // 添加组件到窗口中
        setLayout(new GridLayout(4, 1));
        add(inputPanel);
        add(keyPanel);
        add(buttonPanel);
        add(outputPanel);

        // 注册加密和解密按钮的监听器
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();

                if (key.length() != 10) {
                    JOptionPane.showMessageDialog(null, "请输入正确的密钥！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder ciphertext = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c > 127) {
                        JOptionPane.showMessageDialog(null, "输入包含非ASCII字符！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int plaintext = (int) c;
                    int keyInt = Integer.parseInt(key, 2);
                    int encrypted = SDES.encrypt(plaintext, keyInt);
                    ciphertext.append((char) encrypted);
                }
                outputText.setText(ciphertext.toString());
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();

                if (key.length() != 10) {
                    JOptionPane.showMessageDialog(null, "请输入正确的密钥！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder plaintext = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);

                    int ciphertext = (int) c;
                    int keyInt = Integer.parseInt(key, 2);
                    int decrypted = SDES.decrypt(ciphertext, keyInt);
                    plaintext.append((char) decrypted);
                }
                outputText.setText(plaintext.toString());
            }
        });

        // 显示窗口
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
*/
//8位二进制加密
/*
public class SDES_GUI extends JFrame {
    private JButton encryptButton, decryptButton;
    private JTextField inputText, keyText, outputText;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SDES_GUI::new);
    }

    public SDES_GUI() {
        setTitle("S-DES加解密");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 明文输入框
        JLabel inputLabel = new JLabel("请输入明文（8位二进制数）：");
        inputText = new JTextField(10);
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(inputLabel);
        inputPanel.add(inputText);

        // 密钥输入框
        JLabel keyLabel = new JLabel("请输入密钥（10位二进制数）：");
        keyText = new JTextField(10);
        JPanel keyPanel = new JPanel(new FlowLayout());
        keyPanel.add(keyLabel);
        keyPanel.add(keyText);

        // 加密和解密按钮
        encryptButton = new JButton("加密");
        decryptButton = new JButton("解密");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        // 输出框
        JLabel outputLabel = new JLabel("输出：");
        outputText = new JTextField(20);
        outputText.setEditable(false);
        JPanel outputPanel = new JPanel(new FlowLayout());
        outputPanel.add(outputLabel);
        outputPanel.add(outputText);

        // 添加组件到窗口中
        setLayout(new GridLayout(4, 1));
        add(inputPanel);
        add(keyPanel);
        add(buttonPanel);
        add(outputPanel);

        // 注册加密和解密按钮的监听器
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();

                if (input.length() != 8 || key.length() != 10) {
                    JOptionPane.showMessageDialog(null, "请输入正确的明文和密钥！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int plaintext = Integer.parseInt(input, 2);
                int keyInt = Integer.parseInt(key, 2);
                int ciphertext = SDES.encrypt(plaintext, keyInt);
                outputText.setText(Integer.toBinaryString(ciphertext));
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();

                if (input.length() != 8 || key.length() != 10) {
                    JOptionPane.showMessageDialog(null, "请输入正确的明文和密钥！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ciphertext = Integer.parseInt(input, 2);
                int keyInt = Integer.parseInt(key, 2);
                int plaintext = SDES.decrypt(ciphertext, keyInt);
                outputText.setText(Integer.toBinaryString(plaintext));
            }
        });

        // 显示窗口
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
*/
