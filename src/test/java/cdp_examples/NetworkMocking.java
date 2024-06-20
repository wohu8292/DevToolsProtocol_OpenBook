package cdp_examples;

import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.fetch.Fetch;

public class NetworkMocking {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/gjdnd/Documents/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		// Once the event paused, it will give back the request
		devTools.addListener(Fetch.requestPaused(), request->{
			if(request.getRequest().getUrl().contains("shetty")) {
				String mockedUrl = request.getRequest().getUrl().replace("=shetty", "=BadGuy");
				System.out.println(mockedUrl);
				devTools.send(Fetch.continueRequest(request.getRequestId(), mockedUrl, request.getRequest().getMethod(), request.getRequest().getPostData(),  request.getResponseHeaders()));
			}
		});
	}	
}
