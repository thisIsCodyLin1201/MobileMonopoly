# 🚀 快速上傳到 GitHub

## 最簡單的方法（推薦）

### Windows 使用者

1. 雙擊執行 `upload_to_github.bat`
2. 按照螢幕指示操作
3. 完成！

### Linux/Mac 使用者

```bash
chmod +x upload_to_github.sh
./upload_to_github.sh
```

## 手動上傳（3 步驟）

### 1️⃣ 初始化並提交

```bash
git init
git branch -M main
git add .
git commit -m "Initial commit: Mobile Monopoly Android App"
```

### 2️⃣ 建立 GitHub 倉庫

前往 https://github.com/new 並建立名為 `MobileMonopoly` 的倉庫

### 3️⃣ 推送到 GitHub

```bash
# 替換 YOUR_USERNAME 為你的 GitHub 使用者名稱
git remote add origin https://github.com/YOUR_USERNAME/MobileMonopoly.git
git push -u origin main
```

## ⚠️ 重要提醒

- **確認** `.env` 檔案已在 `.gitignore` 中
- **檢查** `git status` 確保 `.env` 沒有被追蹤
- **驗證** 上傳後在 GitHub 搜尋是否有洩漏的憑證

## 🆘 需要幫助？

詳細說明請參考 [GITHUB_UPLOAD_GUIDE.md](GITHUB_UPLOAD_GUIDE.md)
