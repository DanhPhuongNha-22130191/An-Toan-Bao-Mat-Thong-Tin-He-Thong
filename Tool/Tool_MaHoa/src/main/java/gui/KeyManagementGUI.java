package gui;

import context.ToolContext;
import util.KeyUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

/**
 * Lớp giao diện chính cho công cụ quản lý chữ ký số
 * Cung cấp các chức năng tạo khóa, ký và xác thực dữ liệu
 */
public class KeyManagementGUI extends JFrame {
    private JTextArea privateKeyTextArea;  // Vùng hiển thị khóa riêng tư
    private JTextArea publicKeyTextArea;   // Vùng hiển thị khóa công khai
    private JTextArea dataTextArea;        // Vùng nhập dữ liệu cần ký
    private JTextArea signatureTextArea;   // Vùng hiển thị chữ ký số
    private JLabel statusLabel;            // Thanh trạng thái
    private final ToolContext toolContext; // Context chứa các công cụ mã hóa

    /**
     * Constructor khởi tạo giao diện chính
     */
    public KeyManagementGUI() {
        this.toolContext = ToolContext.getInstance();
        initializeFrame();
        createAndShowGUI();
    }

    /**
     * Khởi tạo cấu hình cơ bản của cửa sổ chính
     */
    private void initializeFrame() {
        setTitle("Công Cụ Chữ Ký Số");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 800));
        setMinimumSize(new Dimension(1000, 700));
    }

    /**
     * Tạo và hiển thị toàn bộ giao diện người dùng
     */
    private void createAndShowGUI() {
        // Panel chính với thiết kế hiện đại
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 250, 252));

        // Panel tiêu đề
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel nội dung với các tab
        JTabbedPane tabbedPane = createTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Thanh trạng thái
        statusLabel = new JLabel("Sẵn sàng");
        statusLabel.setBorder(new EmptyBorder(10, 15, 10, 15));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(241, 245, 249));
        statusLabel.setForeground(new Color(71, 85, 105));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setupDragAndDrop();
    }

    /**
     * Tạo panel tiêu đề với tên ứng dụng và mô tả
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 250, 252));

        JLabel titleLabel = new JLabel("Công Cụ Chữ Ký Số");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 41, 59));

        JLabel subtitleLabel = new JLabel("Tạo khóa, ký dữ liệu và xác thực chữ ký");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(248, 250, 252));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        return headerPanel;
    }

    /**
     * Tạo bộ tab chứa các chức năng chính
     */
    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(Color.WHITE);

        // Tab quản lý khóa
        JPanel keyManagementTab = createKeyManagementTab();
        tabbedPane.addTab("🔑 Quản Lý Khóa", keyManagementTab);

        // Tab ký và xác thực
        JPanel signVerifyTab = createSignVerifyTab();
        tabbedPane.addTab("✍️ Ký & Xác Thực", signVerifyTab);

        return tabbedPane;
    }

    /**
     * Tạo tab quản lý khóa với các chức năng tạo, load, save khóa
     */
    private JPanel createKeyManagementTab() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Panel tạo khóa
        JPanel generatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        generatePanel.setBackground(Color.WHITE);

        JButton generateButton = createStyledButton("Tạo Cặp Khóa Mới", new Color(59, 130, 246));
        generateButton.addActionListener(e -> generateKeyPair());
        generatePanel.add(generateButton);

        mainPanel.add(generatePanel, BorderLayout.NORTH);

        // Panel hiển thị khóa
        JPanel keysPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        keysPanel.setBackground(Color.WHITE);

        // Panel khóa riêng tư
        JPanel privateKeyPanel = createKeyPanel("Khóa Riêng Tư", true);
        keysPanel.add(privateKeyPanel);

        // Panel khóa công khai
        JPanel publicKeyPanel = createKeyPanel("Khóa Công Khai", false);
        keysPanel.add(publicKeyPanel);

        mainPanel.add(keysPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /**
     * Tạo panel cho từng loại khóa (riêng tư hoặc công khai)
     * @param title Tiêu đề của panel
     * @param isPrivate true nếu là khóa riêng tư, false nếu là khóa công khai
     */
    private JPanel createKeyPanel(String title, boolean isPrivate) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(71, 85, 105)));
        panel.setBackground(Color.WHITE);

        // Vùng văn bản hiển thị khóa
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setBackground(new Color(248, 250, 252));

        if (isPrivate) {
            privateKeyTextArea = textArea;
        } else {
            publicKeyTextArea = textArea;
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton loadButton = createSmallButton("Tải");
        JButton saveButton = createSmallButton("Lưu");
        JButton clearButton = createSmallButton("Xóa");
        JButton copyButton = createSmallButton("Sao chép");

        loadButton.addActionListener(e -> loadKeyFromFile(isPrivate));
        saveButton.addActionListener(e -> saveKeyToFile(isPrivate));
        clearButton.addActionListener(e -> clearKey(isPrivate));
        copyButton.addActionListener(e -> copyToClipboard(isPrivate ? privateKeyTextArea : publicKeyTextArea));

        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(copyButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Tạo tab ký và xác thực chữ ký
     */
    private JPanel createSignVerifyTab() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Panel dữ liệu
        JPanel dataPanel = createDataPanel();

        // Panel chữ ký
        JPanel signaturePanel = createSignaturePanel();

        // Chia đôi màn hình
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dataPanel, signaturePanel);
        splitPane.setDividerSize(8);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Panel các thao tác
        JPanel operationsPanel = createOperationsPanel();
        mainPanel.add(operationsPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    /**
     * Tạo panel nhập dữ liệu cần ký hoặc xác thực
     */
    private JPanel createDataPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                "Dữ Liệu Cần Ký/Xác Thực", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(71, 85, 105)));
        panel.setBackground(Color.WHITE);

        dataTextArea = new JTextArea();
        dataTextArea.setLineWrap(true);
        dataTextArea.setWrapStyleWord(true);
        dataTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dataTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        dataTextArea.setBackground(new Color(248, 250, 252));

        JScrollPane scrollPane = new JScrollPane(dataTextArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton loadDataButton = createSmallButton("Tải Dữ Liệu");
        JButton clearDataButton = createSmallButton("Xóa");

        loadDataButton.addActionListener(e -> loadDataFromFile());
        clearDataButton.addActionListener(e -> dataTextArea.setText(""));

        buttonPanel.add(loadDataButton);
        buttonPanel.add(clearDataButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Tạo panel hiển thị chữ ký số
     */
    private JPanel createSignaturePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                "Chữ Ký Số", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(71, 85, 105)));
        panel.setBackground(Color.WHITE);

        signatureTextArea = new JTextArea();
        signatureTextArea.setLineWrap(true);
        signatureTextArea.setWrapStyleWord(true);
        signatureTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        signatureTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        signatureTextArea.setBackground(new Color(248, 250, 252));

        JScrollPane scrollPane = new JScrollPane(signatureTextArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel chứa nút xóa
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton clearSignButton = createSmallButton("Xóa");
        clearSignButton.addActionListener(e -> signatureTextArea.setText(""));
        buttonPanel.add(clearSignButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Tạo panel chứa các nút thao tác chính (Ký, Xác thực)
     */
    private JPanel createOperationsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton signButton = createStyledButton("Ký Dữ Liệu", new Color(16, 185, 129));
        JButton verifyButton = createStyledButton("Xác Thực Chữ Ký", new Color(139, 92, 246));

        signButton.addActionListener(e -> signData());
        verifyButton.addActionListener(e -> verifySignature());

        panel.add(signButton);
        panel.add(verifyButton);

        return panel;
    }

    /**
     * Tạo nút có kiểu dáng đẹp với màu nền tùy chỉnh
     * @param text Văn bản hiển thị trên nút
     * @param backgroundColor Màu nền của nút
     */
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    /**
     * Tạo nút nhỏ với kiểu dáng nhẹ nhàng
     * @param text Văn bản hiển thị trên nút
     */
    private JButton createSmallButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(new Color(241, 245, 249));
        button.setForeground(new Color(71, 85, 105));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(226, 232, 240));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(241, 245, 249));
            }
        });

        return button;
    }

    /**
     * Cập nhật thông báo trạng thái
     * @param message Thông điệp cần hiển thị
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Hiển thị hộp thoại lỗi
     * @param title Tiêu đề hộp thoại
     * @param message Nội dung thông báo lỗi
     */
    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
        updateStatus("Lỗi: " + message);
    }

    /**
     * Hiển thị hộp thoại thông tin
     * @param title Tiêu đề hộp thoại
     * @param message Nội dung thông báo
     */
    private void showInfo(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        updateStatus(message);
    }

    /**
     * Xử lý tạo cặp khóa mới (khóa riêng tư và khóa công khai)
     */
    private void generateKeyPair() {
        try {
            toolContext.generateKeyPair();
            privateKeyTextArea.setText(KeyUtils.convertKeyToPEM(toolContext.getPrivateKey()));
            publicKeyTextArea.setText(KeyUtils.convertKeyToPEM(toolContext.getPublicKey()));
            updateStatus("Đã tạo cặp khóa mới thành công");
            showInfo("Thành công", "Cặp khóa mới đã được tạo thành công");
        } catch (Exception e) {
            showError("Lỗi Tạo Khóa", e.getMessage());
        }
    }

    /**
     * Tải khóa từ file PEM
     * @param isPrivate true nếu tải khóa riêng tư, false nếu tải khóa công khai
     */
    private void loadKeyFromFile(boolean isPrivate) {
        // Tạo đối tượng chọn file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Tệp PEM", "pem"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String content = new String(Files.readAllBytes(file.toPath()));

                if (isPrivate) {
                    // Kiểm tra định dạng khóa riêng tư
                    KeyUtils.readPrivateKeyFromString(content);
                    privateKeyTextArea.setText(content);
                    updateStatus("Đã tải khóa riêng tư: " + file.getName());
                } else {
                    // Kiểm tra định dạng khóa công khai
                    KeyUtils.readPublicKeyFromString(content);
                    publicKeyTextArea.setText(content);
                    updateStatus("Đã tải khóa công khai: " + file.getName());
                }
            } catch (Exception e) {
                showError("Lỗi Tải Khóa", e.getMessage());
            }
        }
    }

    /**
     * Lưu khóa vào file PEM
     * @param isPrivate true nếu lưu khóa riêng tư, false nếu lưu khóa công khai
     */
    private void saveKeyToFile(boolean isPrivate) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Tệp PEM", "pem"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String content = isPrivate ? privateKeyTextArea.getText() : publicKeyTextArea.getText();
            if (content.isEmpty()) {
                showError("Lỗi", "Không có nội dung khóa để lưu");
                return;
            }

            File file = fileChooser.getSelectedFile();
            String path = file.getPath();
            if (!path.toLowerCase().endsWith(".pem")) {
                path += ".pem";
            }

            try {
                try (FileWriter writer = new FileWriter(path)) {
                    writer.write(content);
                }
                showInfo("Thành công", "Khóa đã được lưu thành công vào " + path);
            } catch (Exception e) {
                showError("Lỗi Lưu Khóa", e.getMessage());
            }
        }
    }

    /**
     * Tải dữ liệu từ file để ký hoặc xác thực
     */
    private void loadDataFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String content = new String(Files.readAllBytes(file.toPath()));
                dataTextArea.setText(content);
                updateStatus("Đã tải dữ liệu từ: " + file.getName());
            } catch (Exception e) {
                showError("Lỗi Tải Dữ Liệu", e.getMessage());
            }
        }
    }

    /**
     * Ký dữ liệu bằng khóa riêng tư
     */
    private void signData() {
        try {
            String data = dataTextArea.getText();
            if (data.isEmpty()) {
                showError("Lỗi", "Vui lòng nhập dữ liệu cần ký");
                return;
            }

            PrivateKey privateKey = KeyUtils.readPrivateKeyFromString(privateKeyTextArea.getText());
            toolContext.setPrivateKey(privateKey);

            String signature = toolContext.sign(data);
            signatureTextArea.setText(signature);
            showInfo("Thành công", "Dữ liệu đã được ký thành công");
        } catch (Exception e) {
            showError("Lỗi Ký Dữ Liệu", e.getMessage());
        }
    }

    /**
     * Xác thực chữ ký bằng khóa công khai
     */
    private void verifySignature() {
        try {
            String data = dataTextArea.getText();
            String signature = signatureTextArea.getText();

            if (data.isEmpty() || signature.isEmpty()) {
                showError("Lỗi", "Cần có cả dữ liệu và chữ ký để xác thực");
                return;
            }

            PublicKey publicKey = KeyUtils.readPublicKeyFromString(publicKeyTextArea.getText());
            toolContext.setPublicKey(publicKey);

            boolean isValid = toolContext.verify(data, signature);
            showInfo("Kết Quả Xác Thực",
                    isValid ? "Chữ ký hợp lệ ✓" : "Chữ ký không hợp lệ ✗");
        } catch (Exception e) {
            showError("Lỗi Xác Thực", e.getMessage());
        }
    }

    /**
     * Xóa nội dung khóa
     * @param isPrivate true nếu xóa khóa riêng tư, false nếu xóa khóa công khai
     */
    private void clearKey(boolean isPrivate) {
        if (isPrivate) {
            privateKeyTextArea.setText("");
            updateStatus("Đã xóa khóa riêng tư");
        } else {
            publicKeyTextArea.setText("");
            updateStatus("Đã xóa khóa công khai");
        }
    }

    /**
     * Sao chép nội dung vào clipboard
     * @param textArea Vùng văn bản cần sao chép
     */
    private void copyToClipboard(JTextArea textArea) {
        textArea.selectAll();
        textArea.copy();
        textArea.select(0, 0);
        updateStatus("Đã sao chép nội dung vào clipboard");
    }

    /**
     * Thiết lập chức năng kéo thả file
     */
    private void setupDragAndDrop() {
        new DropTarget(privateKeyTextArea, new KeyDropTargetListener(true));
        new DropTarget(publicKeyTextArea, new KeyDropTargetListener(false));
        new DropTarget(dataTextArea, new DropTargetAdapter() {
            public void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> files = (List<File>) event.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        String content = new String(Files.readAllBytes(files.get(0).toPath()));
                        dataTextArea.setText(content);
                        updateStatus("Đã tải dữ liệu bằng kéo thả");
                    }
                } catch (Exception e) {
                    showError("Lỗi Kéo Thả", e.getMessage());
                }
            }
        });
    }

    /**
     * Lớp xử lý sự kiện kéo thả cho khóa
     */
    private class KeyDropTargetListener implements DropTargetListener {
        private final boolean isPrivate;

        /**
         * Constructor
         * @param isPrivate true nếu xử lý khóa riêng tư, false nếu xử lý khóa công khai
         */
        public KeyDropTargetListener(boolean isPrivate) {
            this.isPrivate = isPrivate;
        }

        @Override
        public void drop(DropTargetDropEvent event) {
            try {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                List<File> files = (List<File>) event.getTransferable()
                        .getTransferData(DataFlavor.javaFileListFlavor);
                if (!files.isEmpty()) {
                    String content = new String(Files.readAllBytes(files.get(0).toPath()));
                    if (isPrivate) {
                        KeyUtils.readPrivateKeyFromString(content); // Kiểm tra tính hợp lệ
                        privateKeyTextArea.setText(content);
                        updateStatus("Đã tải khóa riêng tư: " + files.get(0).getName());
                    } else {
                        KeyUtils.readPublicKeyFromString(content); // Kiểm tra tính hợp lệ
                        publicKeyTextArea.setText(content);
                        updateStatus("Đã tải khóa công khai: " + files.get(0).getName());
                    }
                }
            } catch (Exception e) {
                showError("Lỗi Kéo Thả", e.getMessage());
            }
            event.dropComplete(true);
        }

        @Override
        public void dragEnter(DropTargetDragEvent event) {
            // Không cần xử lý
        }

        @Override
        public void dragOver(DropTargetDragEvent event) {
            // Không cần xử lý
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent event) {
            // Không cần xử lý
        }

        @Override
        public void dragExit(DropTargetEvent event) {
            // Không cần xử lý
        }
    }
}