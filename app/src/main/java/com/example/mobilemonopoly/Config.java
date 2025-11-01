package com.example.mobilemonopoly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置類別 - 從 .env 檔案讀取環境變數
 * 用於安全地管理敏感資訊（如資料庫憑證）
 */
public class Config {
    private static Map<String, String> envVars = new HashMap<>();
    private static boolean isLoaded = false;

    /**
     * 載入 .env 檔案
     * @param envFilePath .env 檔案的路徑
     */
    public static void loadEnv(String envFilePath) {
        if (isLoaded) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(envFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // 忽略空行和註解
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // 解析 KEY=VALUE 格式
                int equalsIndex = line.indexOf('=');
                if (equalsIndex > 0) {
                    String key = line.substring(0, equalsIndex).trim();
                    String value = line.substring(equalsIndex + 1).trim();
                    envVars.put(key, value);
                }
            }
            isLoaded = true;
            System.out.println("環境變數載入成功");
        } catch (IOException e) {
            System.err.println("無法載入 .env 檔案: " + e.getMessage());
            System.err.println("請確保 .env 檔案存在於專案根目錄");
        }
    }

    /**
     * 獲取環境變數值
     * @param key 變數名稱
     * @return 變數值，如果不存在則返回 null
     */
    public static String get(String key) {
        // 優先從系統環境變數獲取
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }
        // 其次從 .env 檔案獲取
        return envVars.get(key);
    }

    /**
     * 獲取環境變數值，如果不存在則返回預設值
     * @param key 變數名稱
     * @param defaultValue 預設值
     * @return 變數值或預設值
     */
    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 檢查環境變數是否已設定
     * @param key 變數名稱
     * @return 如果變數存在且非空則返回 true
     */
    public static boolean has(String key) {
        String value = get(key);
        return value != null && !value.isEmpty();
    }
}
