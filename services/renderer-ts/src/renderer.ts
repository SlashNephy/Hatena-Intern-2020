import unified from "unified"
import markdown from "remark-parse"
import remark2rehype from "remark-rehype"
import html from "rehype-stringify"
import externalLinks from "remark-external-links";
//@ts-ignore
import emoji from "remark-emoji"
//@ts-ignore
import images from "remark-images"
//@ts-ignore
import mathjax from "remark-mathjax"

/**
 * 受け取った文書を HTML に変換する
 */
export async function render(src: string): Promise<string> {
  // unified で処理
  const processor = unified()
      // Markdown => remark
      .use(markdown)
      // 画像 URL => <img>
      .use(images)
      // <a> タグにデフォルトとして target=_blank, rel=nofollow を付与
      .use(externalLinks, {target: "_blank", rel: "nofollow"})
      // :emoji: 記法
      .use(emoji)
      // MathJax 記法
      .use(mathjax)
      // remark => rehype
      .use(remark2rehype)
      // rehype => HTML
      .use(html);

  return processor.process(src).then((res) => {
    return String(res);
  });
}
