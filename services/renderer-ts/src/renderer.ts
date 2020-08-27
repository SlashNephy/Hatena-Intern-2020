import remark from "remark"
import remark2rehype from "remark-rehype"
import html from "rehype-stringify"
import externalLinks from "remark-external-links";
//@ts-ignore
import emoji from "remark-emoji"
//@ts-ignore
import images from "remark-images"
import mathjax from "rehype-mathjax"
import {getPageTitle} from "./client";
import {Link} from "mdast";
import visit from "unist-util-visit";
import {Transformer} from "unified";
import winston, {debug} from "winston";

/**
 * 受け取った文書を HTML に変換する
 */
export async function render(src: string): Promise<string> {
  // string => remark
  const processor = remark()
      .use(autoTitle)
      // 画像 URL => Image
      .use(images)
      // Link にデフォルトとして target=_blank, rel=nofollow を付与
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

/**
 * Markdown リンクにタイトルを付ける処理をする Unified プラグイン
 */
function autoTitle(): Transformer {
    return function transformer(tree): Promise<void> {
        return new Promise(async (resolve, reject) => {
            const updates: Link[] = [];

            visit(tree, "link", (node: Link) => {
                const text = node.children.find(e => e.type === "text");
                if (text === undefined) {
                    // タイトルが付いていない場合は取得を試みる
                    updates.push(node);
                }
            });

            for (const node of updates) {
                try {
                    const title = await getPageTitle(node.url) ?? node.url;

                    node.children.push({
                        type: "text",
                        value: title
                    })
                } catch (e) {
                    const logger = winston.createLogger({
                        level: "debug",
                        format: winston.format.combine(
                            winston.format.timestamp(),
                            winston.format.simple()
                        ),
                        transports: [new winston.transports.Console()]
                    });
                    logger.error(node.url);
                    logger.error(e);

                    reject(e);
                }
            }

            resolve();
        });
    };
}
