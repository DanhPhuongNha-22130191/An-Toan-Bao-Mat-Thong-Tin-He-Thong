package gui;

import context.KeySize;
import context.SignatureAlgorithm;
import context.ToolContext;
import gui.helper.FileHandler;
import gui.helper.UIComponents;
import util.KeyUtils;

import javax.swing.*;
import java.awt.*;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Lớp giao diện chính cho công cụ quản lý chữ ký số
 * Cung cấp các chức năng tạo khóa, ký và xác thực dữ liệu
 */
public class GUI extends JFrame {
    private JTextArea privateKeyTextArea;  // Vùng hiển thị khóa riêng tư
    private JTextArea publicKeyTextArea;   // Vùng hiển thị khóa công khai
    private JTextArea dataTextArea;        // Vùng nhập dữ liệu cần ký
    private JTextArea signatureTextArea;   // Vùng hiển thị chữ ký số
    private JLabel statusLabel;            // Thanh trạng thái
    private final ToolContext toolContext; // Context chứa các công cụ mã hóa

    /**
     * Constructor khởi tạo giao diện chính
     */
    public GUI() {
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
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header
        JPanel headerPanel = UIComponents.createHeaderPanel(
                "Công Cụ Chữ Ký Số",
                "Tạo khóa, ký dữ liệu và xác thực chữ ký"
        );
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabbedPane = createTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Status
        statusLabel = UIComponents.createStatusLabel("Sẵn sàng");
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setupDragAndDrop();
    }

    /**
     * Tạo bộ tab chứa các chức năng chính
     */
    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = UIComponents.createStyledTabbedPane();

        JPanel keyManagementTab = createKeyManagementTab();
        tabbedPane.addTab("🔑 Quản Lý Khóa", keyManagementTab);

        JPanel signVerifyTab = createSignVerifyTab();
        tabbedPane.addTab("✍️ Ký & Xác Thực", signVerifyTab);

        return tabbedPane;
    }

    /**
     * Tạo tab quản lý khóa
     */
    private JPanel createKeyManagementTab() {
        JPanel mainPanel = createStyledPanel(new BorderLayout(20, 20));

        JPanel generatePanel = createGeneratePanel();
        mainPanel.add(generatePanel, BorderLayout.NORTH);

        JPanel keysPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        keysPanel.setBackground(Color.WHITE);
        keysPanel.add(createKeyPanel("Khóa Riêng Tư", true, privateKeyTextArea));
        keysPanel.add(createKeyPanel("Khóa Công Khai", false, publicKeyTextArea));
        mainPanel.add(keysPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /**
     * Tạo panel cho nút tạo cặp khóa và các combobox
     */
    private JPanel createGeneratePanel() {
        JPanel generatePanel = createStyledPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Tạo combobox cho key sizes
        JComboBox<KeySize> keySizeCombo = new JComboBox<>(KeySize.values());
        keySizeCombo.setSelectedItem(KeySize.SIZE_2048);
        generatePanel.add(UIComponents.createLabeledComboBox("Keysize:", keySizeCombo, 100));

        // Tạo combobox cho signature algorithms
        JComboBox<SignatureAlgorithm> signatureAlgCombo = new JComboBox<>(SignatureAlgorithm.values());
        signatureAlgCombo.setSelectedItem(SignatureAlgorithm.SHA256_WITH_RSA);
        generatePanel.add(UIComponents.createLabeledComboBox("Thuật toán ký:", signatureAlgCombo, 180));

        // Nút tạo khóa
        JButton generateButton = UIComponents.createStyledButton("Tạo Cặp Khóa Mới", new Color(59, 130, 246));
        generateButton.addActionListener(e -> {
            KeySize selectedKeySize = (KeySize) keySizeCombo.getSelectedItem();
            SignatureAlgorithm selectedSignatureAlg = (SignatureAlgorithm) signatureAlgCombo.getSelectedItem();
            toolContext.setKeySize(selectedKeySize);
            toolContext.setSignatureAlgorithm(selectedSignatureAlg);
            try {
                toolContext.generateKeyPair();
                privateKeyTextArea.setText(KeyUtils.convertKeyToPEM(toolContext.getPrivateKey()));
                publicKeyTextArea.setText(KeyUtils.convertKeyToPEM(toolContext.getPublicKey()));
                showInfo("Thành công", "Tạo khóa thành công!");
            } catch (Exception ex) {
                showError("Lỗi", "Lỗi tạo khóa: " + ex.getMessage());
            }
        });
        generatePanel.add(generateButton);

        return generatePanel;
    }

    /**
     * Tạo panel cho từng loại khóa hoặc dữ liệu với các nút chức năng
     */
    private JPanel createKeyPanel(String title, boolean isPrivate, JTextArea textAreaField) {
        JPanel panel = UIComponents.createTitledPanel(title);

        JTextArea textArea = UIComponents.createStyledTextArea(isPrivate ? "Consolas" : "Segoe UI", isPrivate ? 12 : 13);
        if (isPrivate) {
            privateKeyTextArea = textArea;
        } else {
            publicKeyTextArea = textArea;
        }

        JScrollPane scrollPane = UIComponents.createStyledScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel(textArea, isPrivate ? "Khóa riêng tư" : "Khóa công khai", isPrivate, false);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Tạo tab ký và xác thực chữ ký
     */
    private JPanel createSignVerifyTab() {
        JPanel mainPanel = createStyledPanel(new BorderLayout(20, 20));

        // Panel dữ liệu
        JPanel dataPanel = createTextAreaPanel("Dữ Liệu Cần Ký/Xác Thực", false, true);
        dataTextArea = (JTextArea) ((JScrollPane) dataPanel.getComponent(0)).getViewport().getView();

        // Panel chữ ký
        JPanel signaturePanel = createTextAreaPanel("Chữ Ký Số", false, false);
        signatureTextArea = (JTextArea) ((JScrollPane) signaturePanel.getComponent(0)).getViewport().getView();

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
     * Tạo panel chung cho vùng văn bản với các nút chức năng
     */
    private JPanel createTextAreaPanel(String title, boolean isPrivate, boolean isData) {
        JPanel panel = UIComponents.createTitledPanel(title);

        JTextArea textArea = UIComponents.createStyledTextArea(isPrivate ? "Consolas" : "Segoe UI", isPrivate ? 12 : 13);
        JScrollPane scrollPane = UIComponents.createStyledScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel(textArea, isData ? "Dữ liệu" : "Chữ ký", isPrivate, isData);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Tạo panel chứa các nút chức năng (Tải, Lưu, Xóa, Sao chép)
     */
    private JPanel createButtonPanel(JTextArea textArea, String type, boolean isPrivate, boolean isData) {
        JPanel buttonPanel = createStyledPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Tải cho Dữ liệu và cả Khóa (riêng tư + công khai)
        if (type.equals("Dữ liệu") || type.contains("Khóa")) {
            JButton loadButton = UIComponents.createSmallButton("Tải");
            loadButton.addActionListener(e -> {
                if (type.equals("Dữ liệu")) {
                    FileHandler.loadDataFromFile(textArea, statusLabel, this);
                } else {
                    FileHandler.loadKeyFromFile(isPrivate, textArea, statusLabel, this);
                }
            });
            buttonPanel.add(loadButton);
        }

        // Lưu cho Khóa (riêng tư + công khai)
        if (type.contains("Khóa")) {
            JButton saveButton = UIComponents.createSmallButton("Lưu");
            saveButton.addActionListener(e -> FileHandler.saveKeyToFile(isPrivate, textArea, statusLabel, this));
            buttonPanel.add(saveButton);
        }


        if (!isData || isPrivate || (isData && !isPrivate)) {
            JButton clearButton = UIComponents.createSmallButton("Xóa");
            clearButton.addActionListener(e -> clearContent(textArea, type));
            buttonPanel.add(clearButton);
        }

        JButton copyButton = UIComponents.createSmallButton("Sao Chép");
        copyButton.addActionListener(e -> copyToClipboard(textArea));
        buttonPanel.add(copyButton);

        return buttonPanel;
    }

    /**
     * Tạo panel chứa các nút thao tác chính (Ký, Xác thực)
     */
    private JPanel createOperationsPanel() {
        JPanel panel = createStyledPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton signButton = UIComponents.createStyledButton("Ký Dữ Liệu", new Color(16, 185, 129));
        JButton verifyButton = UIComponents.createStyledButton("Xác Thực Chữ Ký", new Color(139, 92, 246));

        signButton.addActionListener(e -> signData());
        verifyButton.addActionListener(e -> verifySignature());

        panel.add(signButton);
        panel.add(verifyButton);

        return panel;
    }

    /**
     * Tạo panel với các thuộc tính mặc định
     */
    private JPanel createStyledPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);
        if (layout instanceof BorderLayout) {
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }
        return panel;
    }

    /**
     * Hiển thị hộp thoại lỗi
     */
    private void showError(String title, String message) {
        FileHandler.showError(this, statusLabel, title, message);
    }

    /**
     * Hiển thị hộp thoại thông tin
     */
    private void showInfo(String title, String message) {
        FileHandler.showInfo(this, statusLabel, title, message);
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
     * Xóa nội dung vùng văn bản
     */
    private void clearContent(JTextArea textArea, String type) {
        textArea.setText("");
        statusLabel.setText("Đã xóa " + type.toLowerCase());
    }

    /**
     * Sao chép nội dung vào clipboard
     */
    private void copyToClipboard(JTextArea textArea) {
        textArea.selectAll();
        textArea.copy();
        textArea.select(0, 0);
        statusLabel.setText("Đã sao chép nội dung vào clipboard");
    }

    /**
     * Thiết lập chức năng kéo thả file
     */
    private void setupDragAndDrop() {
        FileHandler.setupKeyDragAndDrop(privateKeyTextArea, true, statusLabel, this);
        FileHandler.setupKeyDragAndDrop(publicKeyTextArea, false, statusLabel, this);
        FileHandler.setupDataDragAndDrop(dataTextArea, statusLabel, this);
    }
}