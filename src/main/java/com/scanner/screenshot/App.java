package com.scanner.screenshot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        // Suppress Selenium logs
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-popup-blocking",
                "--ignore-certificate-errors", "--window-size=1920,1080", "--log-level=3", "--silent");

        WebDriver driver = new ChromeDriver(chromeOptions);
        Options options = createOptions();

        CommandLine cmd = null;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("utility-name", options);
            System.exit(1);
        }

        String url = cmd.getOptionValue("url");
        if (url == null) {
            new HelpFormatter().printHelp("utility-name", options);
            System.exit(1);
        }

        try {
            request(driver, url);

            if (cmd.hasOption("screenshot")) {
                takeScreenshot(driver, cmd.getOptionValue("screenshot"));
            }

            if (cmd.hasOption("source")) {
                getSource(driver, cmd.getOptionValue("source"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("u").longOpt("url").hasArg().desc("URL to capture").build());
        options.addOption(Option.builder("s").longOpt("screenshot").hasArg().desc("File to save screenshot").build());
        options.addOption(Option.builder("d").longOpt("source").hasArg().desc("File to save HTML source").build());
        return options;
    }

    public static void getSource(WebDriver driver, String source) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(source))) {
            writer.write(driver.getPageSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenshot(WebDriver driver, String screenshot) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File output = new File(screenshot);
        try {
            FileUtils.copyFile(src, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void request(WebDriver driver, String url) {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            driver.get(url);
            Thread.sleep(10000); // This is to wait for page to load, which might not be the best approach
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
        }
    }
}