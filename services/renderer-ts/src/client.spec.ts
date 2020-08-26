import {getPageTitle} from "./client";

describe("client", () => {
    const mock = jest.fn(getPageTitle).mockResolvedValue("GitHub");

    it("URL のタイトルを取得できる", async () => {
        const url = "https://github.com/";
        const title = await mock(url);

        expect(title).toBe("GitHub");
    });
});
