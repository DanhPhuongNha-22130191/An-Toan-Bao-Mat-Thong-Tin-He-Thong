package gui.helper;

import util.KeyUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

/**
 * Lớp tiện ích xử lý các thao tác liên quan đến file
 * Bao gồm tải file, lưu file và hỗ trợ kéo thả
 */
public class FileHandler {

    /**
     * Tải khóa từ file PEM
     *
     * @param isPrivate   true nếu tải khóa riêng tư, false nếu tải khóa công khai
     * @param textArea    Vùng văn bản để hiển thị nội dung khóa
     * @param statusLabel Nhãn trạng thái để cập nhật thông báo
     * @param parent      Thành phần cha để hiển thị hộp thoại
     */
    public static void loadKeyFromFile(boolean isPrivate, JTextArea textArea, JLabel statusLabel, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Tệp PEM", "pem"));

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String content = new String(Files.readAllBytes(file.toPath()));

                if (isPrivate) {
                    KeyUtils.readPrivateKeyFromString(content); // Kiểm tra tính hợp lệ
                    textArea.setText(content);
                    statusLabel.setText("Đã tải khóa riêng tư: " + file.getName());
                } else {
                    KeyUtils.readPublicKeyFromString(content); // Kiểm tra tính hợp lệ
                    textArea.setText(content);
                    statusLabel.setText("Đã tải khóa công khai: " + file.getName());
                }
            } catch (Exception e) {
                showError(parent, statusLabel, "Lỗi Tải Khóa", e.getMessage());
            }
        }
    }

    /**
     * Lưu khóa vào file PEM
     *
     * @param isPrivate   true nếu lưu khóa riêng tư, false nếu lưu khóa công khai
     * @param textArea    Vùng văn bản chứa nội dung khóa
     * @param statusLabel Nhãn trạng thái để cập nhật thông báo
     * @param parent      Thành phần cha để hiển thị hộp thoại
     */
    public static void saveKeyToFile(boolean isPrivate, JTextArea textArea, JLabel statusLabel, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Tệp PEM", "pem"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            String content = textArea.getText();
            if (content.isEmpty()) {
                showError(parent, statusLabel, "Lỗi", "Không có nội dung khóa để lưu");
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
                showInfo(parent, statusLabel, "Thành công", "Khóa đã được lưu thành công vào " + path);
            } catch (Exception e) {
                showError(parent, statusLabel, "Lỗi Lưu Khóa", e.getMessage());
            }
        }
    }

    /**
     * Tải dữ liệu từ file
     *
     * @param textArea    Vùng văn bản để hiển thị nội dung dữ liệu
     * @param statusLabel Nhãn trạng thái để cập nhật thông báo
     * @param parent      Thành phần cha để hiển thị hộp thoại
     */
    public static void loadDataFromFile(JTextArea textArea, JLabel statusLabel, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String content = new String(Files.readAllBytes(file.toPath()));
                textArea.setText(content);
                statusLabel.setText("Đã tải dữ liệu từ: " + file.getName());
            } catch (Exception e) {
                showError(parent, statusLabel, "Lỗi Tải Dữ Liệu", e.getMessage());
            }
        }
    }

    /**
     * Thiết lập kéo thả file cho khóa
     *
     * @param textArea    Vùng văn bản để hiển thị nội dung khóa
     * @param isPrivate   true nếu xử lý khóa riêng tư, false nếu xử lý khóa công khai
     * @param statusLabel Nhãn trạng thái để cập nhật thông báo
     * @param parent      Thành phần cha để hiển thị hộp thoại
     */
    public static void setupKeyDragAndDrop(JTextArea textArea, boolean isPrivate, JLabel statusLabel, Component parent) {
        new DropTarget(textArea, new DropTargetListener() {
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
                            textArea.setText(content);
                            statusLabel.setText("Đã tải khóa riêng tư: " + files.get(0).getName());
                        } else {
                            KeyUtils.readPublicKeyFromString(content); // Kiểm tra tính hợp lệ
                            textArea.setText(content);
                            statusLabel.setText("Đã tải khóa công khai: " + files.get(0).getName());
                        }
                    }
                } catch (Exception e) {
                    showError(parent, statusLabel, "Lỗi Kéo Thả", e.getMessage());
                }
                event.dropComplete(true);
            }

            @Override
            public void dragEnter(DropTargetDragEvent event) {}
            @Override
            public void dragOver(DropTargetDragEvent event) {}
            @Override
            public void dropActionChanged(DropTargetDragEvent event) {}
            @Override
            public void dragExit(DropTargetEvent event) {}
        });
    }

    /**
     * Thiết lập kéo thả file cho dữ liệu
     *
     * @param textArea    Vùng văn bản để hiển thị nội dung dữ liệu
     * @param statusLabel Nhãn trạng thái để cập nhật thông báo
     * @param parent      Thành phần cha để hiển thị hộp thoại
     */
    public static void setupDataDragAndDrop(JTextArea textArea, JLabel statusLabel, Component parent) {
        new DropTarget(textArea, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> files = (List<File>) event.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        String content = new String(Files.readAllBytes(files.get(0).toPath()));
                        textArea.setText(content);
                        statusLabel.setText("Đã tải dữ liệu bằng kéo thả");
                    }
                } catch (Exception e) {
                    showError(parent, statusLabel, "Lỗi Kéo Thả", e.getMessage());
                }
            }
        });
    }

    /**
     * Hiển thị hộp thoại lỗi
     *
     * @param parent      Thành phần cha để hiển thị hộp thoại
     * @param statusLabel Nhãn trạng thái để cập nhật thông báo
     * @param title       Tiêu đề hộp thoại
     * @param message     Nội dung thông báo lỗi
     */
    public static void showError(Component parent, JLabel statusLabel, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
        statusLabel.setText("Lỗi: " + message);
    }

    /**
     * Hiển thị hộp thoại thông tin
     *
     * @param parent      Thành phần cha để hiển thị hộp thoại
     * @param statusLabel Nhãn trạng thái để cập nhật thông báo
     * @param title       Tiêu đề hộp thoại
     * @param message     Nội dung thông báo
     */
    public static void showInfo(Component parent, JLabel statusLabel, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
        statusLabel.setText(message);
    }
}