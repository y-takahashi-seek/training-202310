# 概要

本プロジェクトは研修用のサンプルプロジェクトである。スケジューラアプリのバックエンドAPI開発プロジェクトを想定し、APIを作成していく。

# パッケージ構成

CleanArchitectureをベースに簡略化したパッケージ構成とする

```
┬── controller  リクエストを受け取り、レスポンスを返却するControllerをまとめたクラスを配置する（今回はプロジェクトに1クラスのみの想定）
│   ├─ request      リクエストの形式を定義するRecordクラスを配置する
│   └─ response     レスポンスの形式を定義するRecordクラスを配置する
├── domain          スケジューラアプリに関する固有のロジック（ドメインロジック）を配置する。研修開始時点では汎用的なサービスクラスを一つのみ定義している
└── infrastructure  （未作成）データベースや外部接続処理に関する処理を配置する。
```

# 留意事項

本プロジェクトのサンプル実装は研修用のため非常に簡略化していることを留意すること。  
エラーハンドリング、セキュリティ面などの考慮が多数足りていないため、実務での実装においてそのまま転用することはできない。  