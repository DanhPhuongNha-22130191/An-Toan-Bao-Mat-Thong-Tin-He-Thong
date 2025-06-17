package gui.helper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Lớp tiện ích cung cấp các thành phần giao diện tái sử dụng
 */
public class UIComponents {

    /**
     * Tạo panel tiêu đề với tên ứng dụng và mô tả
     *
     * @param title       Tiêu đề của ứng dụng
     * @param description Mô tả của ứng dụng
     * @return JPanel chứa tiêu đề và mô tả
     */
    public static JPanel createHeaderPanel(String title, String description) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(248, 250, 252));
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(17, 24, 39));

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(107, 114, 128));

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(descriptionLabel);

        return headerPanel;
    }

    /**
     * Tạo JTabbedPane với định dạng tùy chỉnh
     *
     * @return JTabbedPane được định dạng
     */
    public static JTabbedPane createStyledTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        return tabbedPane;
    }

    /**
     * Tạo nhãn trạng thái
     *
     * @param initialText Văn bản trạng thái ban đầu
     * @return JLabel cho trạng thái
     */
    public static JLabel createStatusLabel(String initialText) {
        JLabel statusLabel = new JLabel(initialText);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(71, 85, 105));
        statusLabel.setBorder(new EmptyBorder(10, 20, 10, 20));
        return statusLabel;
    }

    /**
     * Tạo nút nhỏ với văn bản
     *
     * @param text Văn bản hiển thị trên nút
     * @return JButton với định dạng nhỏ
     */
    public static JButton createSmallButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(new Color(229, 231, 235));
        button.setForeground(new Color(17, 24, 39));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(209, 213, 219));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(229, 231, 235));
            }
        });

        return button;
    }

    /**
     * Tạo nút lớn với văn bản và màu nền tùy chỉnh
     *
     * @param text            Văn bản hiển thị trên nút
     * @param backgroundColor Màu nền của nút
     * @return JButton với định dạng lớn
     */
    public static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    /**
     * Tạo JComboBox với nhãn và kích thước tùy chỉnh
     *
     * @param labelText Văn bản nhãn
     * @param comboBox  JComboBox cần gắn nhãn
     * @param width     Chiều rộng của JComboBox
     * @return JPanel chứa nhãn và JComboBox
     */
    public static JPanel createLabeledComboBox(String labelText, JComboBox<?> comboBox, int width) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(71, 85, 105));

        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setPreferredSize(new Dimension(width, 30));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        panel.add(label);
        panel.add(comboBox);
        return panel;
    }

    /**
     * Tạo JTextArea với định dạng chung
     *
     * @param fontName Tên phông chữ
     * @param fontSize Kích thước phông chữ
     * @return JTextArea được định dạng
     */
    public static JTextArea createStyledTextArea(String fontName, int fontSize) {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(fontName, Font.PLAIN, fontSize));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setBackground(new Color(248, 250, 252));
        return textArea;
    }

    /**
     * Tạo JScrollPane với định dạng chung
     *
     * @param textArea JTextArea cần gắn vào JScrollPane
     * @return JScrollPane được định dạng
     */
    public static JScrollPane createStyledScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        return scrollPane;
    }

    /**
     * Tạo panel với tiêu đề và định dạng chung
     *
     * @param title Tiêu đề của panel
     * @return JPanel với định dạng và tiêu đề
     */
    public static JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(71, 85, 105)));
        panel.setBackground(Color.WHITE);
        return panel;
    }
}