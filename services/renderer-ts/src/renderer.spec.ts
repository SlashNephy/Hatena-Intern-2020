import { render } from "./renderer";

describe("render", () => {
  it("URL の自動リンクができる", async () => {
    const src = "foo https://google.com/ bar";
    const html = await render(src);
    expect(html).toBe('<p>foo <a href="https://google.com/" target="_blank" rel="nofollow">https://google.com/</a> bar</p>');
  });

  it("Markdown の記法を変換できる (見出し記法)", async () => {
    const src = "# hoge";
    const html = await render(src);
    expect(html).toBe('<h1>hoge</h1>');
  });
  it("Markdown の記法を変換できる (リンク記法)", async () => {
    const src = "[Google](https://google.com/)";
    const html = await render(src);
    expect(html).toBe('<p><a href="https://google.com/" target="_blank" rel="nofollow">Google</a></p>');
  });
  it("Markdown の記法を変換できる (リスト記法)", async () => {
    const src = `+ One
+ Two
+ Three`;
    const html = await render(src);
    expect(html).toBe(`<ul>
<li>One</li>
<li>Two</li>
<li>Three</li>
</ul>`);
  });
  it("Markdown の記法を変換できる (コードブロック)", async () => {
    const src = `\`\`\`kotlin
fun main() {
    println("Hello, World!")
}
\`\`\``;
    const html = await render(src);
    expect(html).toBe(`<pre><code class="language-kotlin">fun main() {
    println("Hello, World!")
}
</code></pre>`);
  });
  it("Emoji Shortcode の記法を変換できる", async () => {
    const src = ":+1:";
    const html = await render(src);
    expect(html).toBe('<p>👍</p>');
  });
});
