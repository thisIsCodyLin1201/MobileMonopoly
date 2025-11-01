package com.example.mobilemonopoly;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

public class DownloadPicture {

    // 資料庫連線資訊 - 從環境變數讀取
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        // 載入 .env 檔案（請根據實際路徑調整）
        Config.loadEnv(".env");
        
        // 從環境變數讀取資料庫連線資訊
        URL = Config.get("DB_URL");
        USER = Config.get("DB_USER");
        PASSWORD = Config.get("DB_PASSWORD");
        
        // 驗證必要的環境變數是否已設定
        if (URL == null || USER == null || PASSWORD == null) {
            throw new RuntimeException(
                "資料庫連線資訊未設定！請確保 .env 檔案存在並包含 DB_URL, DB_USER, DB_PASSWORD"
            );
        }
    }

    public static void main(String[] args) {
        // 指定下載目錄
        String saveDir = "downloaded_images";
        new File(saveDir).mkdirs(); // 如果資料夾不存在則建立

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Class.forName("org.postgresql.Driver");

            String query = "SELECT id, image_name, image_data FROM images";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String imageName = rs.getString("image_name");
                    InputStream imageData = rs.getBinaryStream("image_data");

                    String filePath = saveDir + File.separator + imageName;
                    if (!filePath.toLowerCase().endsWith(".jpg")) {
                        filePath += ".jpg";
                    }

                    //String filePath = saveDir + File.separator + imageName;
                    saveImageToFile(imageData, filePath);
                    System.out.println("下載成功: " + filePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 將 BLOB 數據存為檔案
    private static void saveImageToFile(InputStream inputStream, String filePath) throws Exception {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}

