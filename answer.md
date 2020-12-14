# 発展課題 

## URLが20個並んだブログ記事を更新できるようにするためにはどうすればいいいか考えて実装してください  

該当コミット: 
https://github.com/SlashNephy/Hatena-Intern-2020/commit/b6261b0d00559e1826b4a90313163c6b316f7c1c

+ 数十個程度であれば, `renderer-ts` <-> `fetcher` の通信処理を並列化するだけで高速化できると考えた。  
  + `fetcher` (grpc-kotlin) サーバは並列にリクエストを捌ける。
  + `renderer-ts` では直列にリクエストを叩いている部分があったので, その呼び出し部分を改善した。  
+ 参考に 1 URL 取得に平均 700 ms 程度かかっていた。
  + この実装にしたところ 20 URLs 取得しても 3 s 程度で済んだ。
  + (従来の実装では 20 × 700 ms ...!)

従来の実装 (抜粋)
```typescript
for (const node of updates) {
    try {
        const title = await getPageTitle(node.url) ?? node.url;
    }
    // ...
}
```

改善した実装 (抜粋)
```typescript
const promises = updates.map(node => new Promise(async (resolve) => {
    try {
        const title = await getPageTitle(node.url) ?? node.url;
        // ...
    }
    // ...
}

await Promise.all(promises);
```


## 100個、あるいは500個URLがあるときはどうすればいいでしょうか。

+ 数十個程度であれば, 上の実装を使えば改善できた。
+ しかし, gRPC サーバが一度に受け入れられるリクエスト数や, クライアント側で発行するリクエスト数の上限といった現実的な問題もある。
  + そもそもサーバが応答しなくなれば, 元も子もない。
+ `renderer-ts` が記事の更新を非同期に行えばよいと思った。
  + 現在の実装では, ユーザが記事の更新を行うと `renderer-ts` がレンダリングを終えるまで待たされてしまう。
  + `renderer-ts` がユーザからの記事の更新リクエストを受け取ると, バックグラウンドでレンダリングを行う実装にすればユーザのストレスがなくなる。
+ この方法なら, 上の問題もカバーできる。


## 一般に外部のウェブサイトへリクエストを行うサービスを設計する際に注意すべきことを考えましょう。ウェブサイト運営者の立場に立って、困ることを考えましょう。  
### クローラーを実装するときにどのような対応策をとればよいでしょうか。

+ `User-Agent` ヘッダを適切に設定し, サービス運営者が必要に応じて連絡できるように配慮する。  

該当コミット: https://github.com/SlashNephy/Hatena-Intern-2020/blob/auto-title-fetching/services/fetcher/src/main/kotlin/fetcher/FetcherHttpClient.kt#L11

+ `robots.txt` や `<meta name="robots">` タグを考慮する。  

+ リクエスト頻度や時間帯を配慮する。
