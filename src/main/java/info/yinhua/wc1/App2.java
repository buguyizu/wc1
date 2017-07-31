package info.yinhua.wc1;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Hello world!
 *
 */
public class App2
{
	public static void main(String[] args) {
		// 设置 chrome 的路径
		System.setProperty("webdriver.chrome.driver", Const.chrome);
		// 创建一个 ChromeDriver 的接口，用于连接 Chrome
		@SuppressWarnings("deprecation")
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(Const.chromedriver))
				.usingAnyFreePort().build();
		try {
			service.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建一个 Chrome 的浏览器实例
		WebDriver driver = new RemoteWebDriver(service.getUrl(),
				DesiredCapabilities.chrome());
	
		// 让浏览器访问 Baidu
		driver.get("http://www.baidu.com");
		// 用下面代码也可以实现
		// driver.navigate().to("http://www.baidu.com");
	
		// 获取 网页的 title
		System.out.println("1 Page title is: " + driver.getTitle());
	
		// 通过 id 找到 input 的 DOM
		WebElement element = driver.findElement(By.id("kw"));
	
		// 输入关键字
		element.sendKeys("zTree");
	
		// 提交 input 所在的 form
		element.submit();
	
		// 通过判断 title 内容等待搜索页面加载完毕，间隔10秒
		(new WebDriverWait(driver, 3))
		.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().endsWith("ztree");
			}
		});
	
		// 显示搜索结果页面的 title
		System.out.println("2 Page title is: " + driver.getTitle());
	
		// 关闭浏览器
		driver.quit();
		// 关闭 ChromeDriver 接口
		service.stop();
    }
}
