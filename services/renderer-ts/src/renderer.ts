import unified from "unified"
import markdown from "remark-parse"
import remark2rehype from "remark-rehype"
import html from "rehype-stringify"

/**
 * 受け取った文書を HTML に変換する
 */
export async function render(src: string): Promise<string> {
  const result = await processMarkdown(src);

  return result;
}

/**
 * Markdown 記法を HTML に変換する
 */
async function processMarkdown(src: string): Promise<string> {
  const processor = unified()
    .use(markdown)
    .use(remark2rehype)
    .use(html);
  
  return processor.process(src).then((res) => {
    return String(res);
  });
}
