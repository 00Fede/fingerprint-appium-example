package com.accenture.fingerprinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.android.AndroidDriver;

public class Fingerprinter {

	DesiredCapabilities myCapabilities = new DesiredCapabilities();
	public static AppiumDriver<WebElement> driver;

	@BeforeMethod
	public void setUpAppium() throws MalformedURLException, InterruptedException {

		String packagename = "com.example.fingerprint";
//		String packagename = "com.android.calculator2";
		String URL = "http://127.0.0.1:4723/wd/hub";
		String activityname = "com.example.fingerprint.MainActivity";
//		String activityname = "com.android.calculator2.Calculator";		
		//myCapabilities.setCapability("deviceName", "device");
		myCapabilities.setCapability("deviceName", "Android Emulator");
		//myCapabilities.setCapability("udid", "ZY222TQCN4");
		myCapabilities.setCapability("avd", "Nexus_5_API_23");
		myCapabilities.setCapability("platformVersion", "6.0");
		myCapabilities.setCapability("platformName", "Android");
		myCapabilities.setCapability("appPackage", packagename);
		myCapabilities.setCapability("appActivity", activityname);
		myCapabilities.setCapability("newCommandTimeout", 100);
//		myCapabilities.setCapability("deviceReadyTimeout", 100);
//		myCapabilities.setCapability("avdReadyTimeout", 100);
//		
//		myCapabilities.setCapability("appWaitDuration", 100000);
		
//		capabilities.setCapability("fingerprintInstrument", true);
//		capabilities.setCapability("unlockType", "fingerprint");
//		capabilities.setCapability("unlockKey", "648042044");
//		
		driver = new AndroidDriver<WebElement>(new URL(URL), myCapabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	@Test
	public void mytest() throws InterruptedException {
		Assert.assertNotNull(driver.getContext());
//		
//		((AndroidDriver<WebElement>)driver).unlockDevice(); Para probar las capabilities de unlocktype and unlockkey
		try {
			/**
			 * Filtra el log para obtener los últimos registros de fringerprint una vez se activado el sensor
			 */
			Thread.sleep(10000);
//			Process process=Runtime.getRuntime().exec("adb logcat -t 3 fpc_fingerprint_hal:I fingerprintd:D *:S");
			
			
			WebDriverWait wait = new WebDriverWait(driver, 50);
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.example.fingerprint:id/fingerprint_icon")));
			
			Runtime.getRuntime().exec("adb -e emu finger touch 1");
			
			
			HashMap<String, Integer> h = new HashMap<>();
			h.put("fingerprintId", 2);
			
			Response r = driver.execute("fingerprint",h);
			System.out.println("Estado " + r.getState()
					+ " Session ID: "+ r.getSessionId());
			
			
			
			Thread.sleep(10000);
			
			HashMap<String, Integer> h2 = new HashMap<>();
			
			h2.put("seconds", 3);
			Response rlock = driver.execute("lock", h2);
			
			System.out.println("Respuesta de lock="+rlock.getState());
			
			
			
			
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.ImageView")));
			
			Thread.sleep(50000);
//			BufferedReader buReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			String last="", current;
//			int i=0;
//			while ((current = buReader.readLine()) != null) {
//
//				if (i%2==0){
//					last = current;
//				}
//				i++;
//			}
//			

		} catch (Exception e) {
			System.out.println(e);
			
			e.printStackTrace();
		}

	}
	
}
