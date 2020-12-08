package info.yinhua.wc1;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppFuhui {
	public static void main(String[] args) {

		WebDriver driver = new HtmlUnitDriver();

		// 福慧菜谱
		String url = "https://mp.weixin.qq.com/s?__biz=MjM5Njc5MjEwMA==&mid=2655578076&idx=2&sn=720b46a6e1834906f613d2ee11ab3506&chksm=bd5f95978a281c81622667d7008c220d5db61e2a27db0cc5040297e6d95e9e5f32e6ef9732dd&scene=21#wechat_redirect";
		String xpath = "//*[@id=\"js_content\"]/section/section[90]/section/section/p/a";

		driver.get(url);
		System.out.println(driver.getTitle());
		System.out.println(url);

		WebElement element = driver.findElement(By.xpath(xpath));
		url = element.getAttribute("href");

		System.out.println("href: " + element.getAttribute("href"));

		// 关闭浏览器
		driver.quit();
	}
}
