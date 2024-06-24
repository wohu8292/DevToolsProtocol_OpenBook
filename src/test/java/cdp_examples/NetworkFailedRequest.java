package cdp_examples;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.fetch.Fetch;
import org.openqa.selenium.devtools.v124.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v124.network.model.ErrorReason;

public class NetworkFailedRequest {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/gjdnd/Documents/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		//Enables issuing of requestPaused events. A request will be paused 
		//until client calls one of failRequest, fulfillRequest or continueRequest/continueWithAuth.
		Optional<List<RequestPattern>> patterns = Optional.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*"),Optional.empty(),Optional.empty())));
		devTools.send(Fetch.enable(patterns, Optional.empty()));
		
		//addListener(event, handler)
		devTools.addListener(Fetch.requestPaused(), request->{
			devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
		});
	}
}
