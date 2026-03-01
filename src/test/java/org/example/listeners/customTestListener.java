package org.example.listeners;

import org.example.base.BaseTest;
import org.example.utils.RetryAnalyzer;
import org.example.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class customTestListener implements ITestListener, IAnnotationTransformer {

    private static final String RESET = "\033[0m";
    private static final String GREEN = "\033[1;32m";
    private static final String RED = "\033[1;31m";
    private static final String YELLOW = "\033[1;33m";
    private static final String BLUE = "\033[1;34m";

    // Attach Retry Automatically To All Tests
    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

    // Test Passed
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(
                result.getMethod().getDescription()
                        + "  "
                        + GREEN + "PASSED" + RESET
        );
    }

    // Test Failed (Screenshot + Retry Info)
    @Override
    public void onTestFailure(ITestResult result) {

        System.out.println(
                result.getMethod().getDescription()
                        + "  "
                        + RED + "FAILED" + RESET
        );

        // Print failure reason
        if (result.getThrowable() != null) {
            System.out.println(
                    YELLOW + result.getThrowable().getMessage() + RESET
            );
        }

        // Take screenshot
        try {
            Object currentClass = result.getInstance();
            WebDriver driver = ((BaseTest) currentClass).getDriver();

            String screenshotPath =
                    ScreenshotUtil.captureScreenshot(driver, result.getName());

            System.out.println(
                    BLUE + "Screenshot saved at: " + screenshotPath + RESET
            );

        } catch (Exception e) {
            System.out.println(
                    RED + "Unable to capture screenshot: "
                            + e.getMessage() + RESET
            );
        }
    }

    // Test Skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(
                result.getMethod().getDescription()
                        + "  "
                        + YELLOW + "SKIPPED" + RESET
        );
    }
}