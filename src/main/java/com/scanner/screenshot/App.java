package com.scanner.screenshot;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.util.concurrent.TimeUnit;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.By;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;

import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import org.apache.commons.cli.*;
public class App {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        String url = "";
        String screenshot = "";
		String source = "";
		List<String> header_list=new ArrayList();
		
		 Options options = new Options();
		
		System.out.println("New"); 
       
        Option HostInput = new Option("u", "url", true, "url");
        HostInput.setRequired(false);
        options.addOption(HostInput);
		
        Option ScreenShotInput = new Option("s", "screenshot", true, "screenshot");
        ScreenShotInput.setRequired(false);
        options.addOption(ScreenShotInput);

        Option SourceOption = new Option("c", "source", true, "source");
        SourceOption.setRequired(false);
        options.addOption(SourceOption);

        Option headerOption = Option.builder("h")
                .longOpt("headers")
                .argName("headers")
                .hasArgs()
                .desc("Set repeatable headers")
                .build();
        options.addOption(headerOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            // Parse the command-line arguments
           CommandLine cmd = parser.parse(options, args);


            // Get the values of the options
            url = cmd.getOptionValue("u");
			source = cmd.getOptionValue("c");
			screenshot = cmd.getOptionValue("s");
            String[] headers = cmd.getOptionValues("h");
            if (headers != null) {
           
                for (String header : headers) {
                   
					header_list.add(header);
                }
            }

            // Add your logic to handle the options as needed
            // ...

        } catch (Exception e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--log-level=3");
        chromeOptions.addArguments("--silent");
		
		 for (String header : header_list)
		 {
			 chromeOptions.addArguments("--header="+header);
			
		 }
		
		
		WebDriver driver = new ChromeDriver(chromeOptions);
		try{
        request(driver,url);
		takeScreenshot(driver, screenshot);
	    getSource(driver, source);

        } catch (Exception e) {
            System.out.println(e);
            driver.quit();
            return;
        }
        return;
    }


   public static void getSource(WebDriver driver,String source)
   {
try 
{
    BufferedWriter writer = new BufferedWriter(new FileWriter(source));
    writer.write(driver.getPageSource());
    
    writer.close();
}
catch(Exception e)
{
}

   } 

    public static void takeScreenshot(WebDriver driver, String screenshot) {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File output=new File(screenshot);
        try {
            FileUtils.copyFile(src, output);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }



    public static void request(WebDriver driver,String url) {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
        Thread.sleep(10000);


    }

}
