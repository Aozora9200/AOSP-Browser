# AOSP-Browser
10年前のAndroid に搭載されていたブラウザを復元するプロジェクト

[platform_packages_apps_browser](https://github.com/aosp-mirror/platform_packages_apps_browser)
の最終版 `master` (41c050d, 2015年) を、Android Studio / Gradle でビルドできるように
移植したものです。

- パッケージ名 (applicationId): `com.aozora.aospbrowser`
  (Java のパッケージは元の `com.android.browser` のまま)
- minSdk 21 / targetSdk 28 / compileSdk 35

## ビルド

Android Studio でこのフォルダを開いて実行するか、コマンドラインで:

```bash
./gradlew assembleDebug
```

出力先: `app/build/outputs/apk/debug/app-debug.apk`
(`assembleRelease` も debug 鍵で署名されるので、そのままインストールできます)

## 主な変更点

- **非公開 API の置き換え**: `android.provider.BrowserContract`、`Browser` の非公開部分、
  `WebAddress`、`SeekBarPreference`、`YesNoPreference`、`SyncStateContentProviderHelper`
  などを `platformsupport` パッケージにコピー・移植しました。`libcore`、`com.android.internal.*`、
  `View` の非公開フィールド (`mScrollX` など)、削除された WebView のメソッドは、公開 API に書き換えています。
- **プロバイダ authority と権限の名前を変更**: `com.android.browser` から
  `com.aozora.aospbrowser` 系に変えたので、システムのブラウザとは衝突しません。
  そのおかげで、upstream が無効化していた「履歴」タブも使えるようになりました。
- **Google アカウント連携は無効化**: 起動時の自動ログイン (ClientLogin / IssueAuthToken) と
  WebView の auto-login バーは、サーバーが既に終了しているうえ、`com.google` アカウントの
  トークンは Google 署名アプリにしか発行されないため、使えません。ブックマークはローカルのみです。
- **動作しないため無効化した機能**:
  - 保存したページ (WebViewClassic が必要)
  - シークレットタブ (Chromium WebView が非対応。Cookie などが通常タブと共有されるため、プライベートにはなりません)
  - Android Beam
  - AppCache
  - システムの Web 検索アプリへの委譲 (同梱の検索エンジン定義を使います)
- `product="nosdcard"` の文字列と、`@*android:` の非公開リソース参照を削除・置き換えしました。
