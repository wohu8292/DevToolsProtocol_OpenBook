package cdp_examples;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.devtools.v124.network.model.Request;
import org.openqa.selenium.devtools.v124.network.model.Response;

public class NetworkLogActivity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:/Users/gjdnd/Documents/chromedriver.exe");
		
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		//request
		devTools.addListener(Network.requestWillBeSent(), request ->{
			Request req = request.getRequest();
		});
		
		//response
		devTools.addListener(Network.responseReceived(), response ->{			
			Response res = response.getResponse();
			if(res.getStatus().toString().startsWith("4")) {
				System.out.println(req.getUrl() + "is failing with status code" + res.getStatus());
			}
		});
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo");
		driver.findElement(By.cssSelector(null));
		
	}
}
