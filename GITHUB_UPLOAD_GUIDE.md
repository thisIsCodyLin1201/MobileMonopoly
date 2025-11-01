# 📦 GitHub 上傳指南

這份文件說明如何將 Mobile Monopoly 專案上傳到 GitHub。

## 🔐 上傳前的安全檢查

在上傳程式碼到 GitHub 之前，請務必完成以下步驟：

### ✅ 步驟 1：確認機密資訊已移除

1. 檢查 `.env` 檔案是否已建立並包含資料庫憑證
2. 確認 `DownloadPicture.java` 不再包含明文憑證
3. 檢查 `.gitignore` 是否包含 `.env`

執行以下命令檢查：

```bash
# 檢查 .gitignore 是否包含 .env
grep -n "^\.env$" .gitignore

# 檢查是否有其他包含憑證的檔案
grep -r "plhZsmsNkoQtcgE7p3h2zbx4c9Y3lPnd" . --exclude-dir=.git
```

### ✅ 步驟 2：驗證 .gitignore

確認 `.gitignore` 包含以下重要項目：

```gitignore
# 環境變數（機密資訊）
.env

# Android 建置輸出
*.apk
*.aab
build/
*.log

# 本地設定
local.properties

# IDE 設定
.idea/
*.iml

# 密鑰檔案
*.jks
*.keystore
```

## 📤 上傳步驟

### 方法一：使用命令列（推薦）

#### 1. 初始化 Git 倉庫

```bash
# 進入專案目錄
cd "c:\Users\cody9\OneDrive\桌面\MobileMonopoly"

# 初始化 Git
git init

# 設定預設分支名稱為 main
git branch -M main
```

#### 2. 配置 Git 使用者資訊（首次使用）

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

#### 3. 添加檔案到 Git

```bash
# 查看哪些檔案會被追蹤
git status

# 添加所有檔案（.gitignore 會自動排除不需要的檔案）
git add .

# 查看即將提交的檔案
git status
```

⚠️ **重要檢查**：確認 `.env` 檔案 **沒有** 出現在即將提交的檔案列表中！

#### 4. 建立初始提交

```bash
git commit -m "Initial commit: Mobile Monopoly Android App"
```

#### 5. 在 GitHub 上建立新倉庫

1. 前往 [GitHub](https://github.com/)
2. 點擊右上角的 `+` 按鈕，選擇 `New repository`
3. 填寫倉庫資訊：
   - **Repository name**: `MobileMonopoly`
   - **Description**: `深坑行動大富翁 - 結合地理定位與文化探索的 Android 遊戲`
   - **Public/Private**: 選擇公開或私有
   - **⚠️ 不要** 勾選 "Initialize this repository with a README"
4. 點擊 `Create repository`

#### 6. 連接遠端倉庫並推送

```bash
# 添加遠端倉庫（替換 YOUR_USERNAME 為你的 GitHub 使用者名稱）
git remote add origin https://github.com/YOUR_USERNAME/MobileMonopoly.git

# 推送到 GitHub
git push -u origin main
```

### 方法二：使用 GitHub Desktop

#### 1. 安裝 GitHub Desktop

如果還沒有安裝，請前往 [GitHub Desktop](https://desktop.github.com/) 下載安裝。

#### 2. 添加本地倉庫

1. 開啟 GitHub Desktop
2. 點擊 `File` → `Add Local Repository`
3. 選擇 `MobileMonopoly` 資料夾
4. 如果提示倉庫不存在，點擊 `Create Repository`

#### 3. 初始提交

1. 在 GitHub Desktop 中查看變更
2. ⚠️ **確認** `.env` 檔案沒有在變更列表中
3. 在左下角的 Summary 欄位輸入：`Initial commit: Mobile Monopoly Android App`
4. 點擊 `Commit to main`

#### 4. 發佈到 GitHub

1. 點擊頂部的 `Publish repository`
2. 確認倉庫名稱和說明
3. 選擇是否保持私有
4. 點擊 `Publish Repository`

## 🔒 上傳後的驗證

### 1. 檢查 GitHub 上的檔案

前往你的 GitHub 倉庫，確認：

- ✅ `.env` 檔案 **沒有** 出現在倉庫中
- ✅ `.env.example` 檔案存在
- ✅ `.gitignore` 檔案存在
- ✅ `README.md` 正確顯示
- ✅ `SECURITY.md` 存在

### 2. 搜尋是否有洩漏的機密

在 GitHub 倉庫中搜尋敏感資訊：

```
搜尋框輸入: plhZsmsNkoQtcgE7p3h2zbx4c9Y3lPnd
```

如果有搜尋結果，**立即**：
1. 刪除該倉庫或將其設為私有
2. 更改所有洩漏的憑證
3. 參考下方的「緊急處理」步驟

## 🚨 緊急處理：如果不小心上傳了機密資訊

### 選項 1：從歷史記錄中移除（推薦）

```bash
# 安裝 BFG Repo-Cleaner（需要 Java）
# 下載自：https://rtyley.github.io/bfg-repo-cleaner/

# 使用 BFG 移除檔案
java -jar bfg.jar --delete-files .env

# 清理並推送
git reflog expire --expire=now --all
git gc --prune=now --aggressive
git push --force
```

### 選項 2：重置倉庫（簡單但會失去歷史）

```bash
# 刪除 .git 目錄
rm -rf .git

# 重新初始化（按照上述步驟重新開始）
git init
```

### 選項 3：刪除倉庫並重新建立

1. 在 GitHub 上刪除倉庫：
   - Settings → Danger Zone → Delete this repository
2. 修正本地程式碼
3. 重新建立倉庫並上傳

**⚠️ 重要**：無論使用哪種方法，都要：
- 立即更改所有洩漏的密碼和憑證
- 通知團隊成員

## 📝 日常工作流程

### 提交變更

```bash
# 查看變更
git status

# 添加變更
git add .

# 提交變更
git commit -m "描述你的變更"

# 推送到 GitHub
git push
```

### 拉取最新變更

```bash
# 拉取其他團隊成員的變更
git pull
```

## 👥 團隊協作

### 新成員加入流程

1. **克隆倉庫**
   ```bash
   git clone https://github.com/YOUR_USERNAME/MobileMonopoly.git
   cd MobileMonopoly
   ```

2. **設定環境變數**
   ```bash
   # 複製範本
   cp .env.example .env
   
   # 編輯 .env 並填入憑證（由團隊負責人提供）
   ```

3. **在 Android Studio 中開啟並同步**

### 分支策略（建議）

```bash
# 建立功能分支
git checkout -b feature/your-feature-name

# 完成後合併回 main
git checkout main
git merge feature/your-feature-name
git push
```

## 🔍 常見問題

### Q: 如何檢查我的 .env 檔案是否被追蹤？

```bash
git ls-files | grep .env
```

如果有輸出，表示 `.env` 被追蹤了，需要移除：

```bash
git rm --cached .env
git commit -m "Remove .env from version control"
git push
```

### Q: 如何更新 .gitignore 並套用到已追蹤的檔案？

```bash
# 更新 .gitignore 後
git rm -r --cached .
git add .
git commit -m "Update .gitignore"
git push
```

### Q: 推送時需要輸入帳號密碼？

建議使用 Personal Access Token (PAT)：

1. GitHub Settings → Developer settings → Personal access tokens
2. Generate new token (classic)
3. 選擇 `repo` 權限
4. 複製生成的 token
5. 推送時使用 token 作為密碼

## 📚 更多資源

- [GitHub 官方文件](https://docs.github.com/)
- [Git 教學](https://git-scm.com/book/zh-tw/v2)
- [GitHub Desktop 使用指南](https://docs.github.com/en/desktop)

## ✅ 最終檢查清單

在推送程式碼前，請確認：

- [ ] `.env` 檔案已加入 `.gitignore`
- [ ] `.env.example` 檔案已建立並不包含真實憑證
- [ ] 所有明文憑證已從程式碼中移除
- [ ] `README.md` 已更新並包含設定說明
- [ ] `SECURITY.md` 已建立
- [ ] 已測試 `git status` 確認 `.env` 不在追蹤列表中
- [ ] 已在 GitHub 上建立倉庫
- [ ] 團隊成員知道如何設定環境變數

---

**祝你順利上傳專案到 GitHub！** 🎉

如有任何問題，請參考 `SECURITY.md` 或聯繫專案維護者。
