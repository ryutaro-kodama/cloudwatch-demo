# CloudWatch デモアプリケーション
1. このリポジトリをclone
2. Intellijを起動し、「File」 > 「Open」からこのプロジェクトを開く
    - buildが終わるまで少し待つ
3. Windowsの「環境変数を編集」から、"AWS_ACCESS_KEY_ID","AWS_SECRET_ACCESS_KEY"に、発行したアクセスキー・シークレットキーを保存する
4. gradleの「bootRun」タスクを実行
5. "http://localhost:8080/hello"にブラウザからアクセスする
6. CloudWatchを開き、「すべてのメトリクス」>「front」>「ディメンションなしのメトリクス」の「ap.credit.success.count」にチェックを入れると、メトリクスが表示される
    - ![image](https://github.com/user-attachments/assets/c0680bf4-bb4e-42eb-abac-891984347f2a)
