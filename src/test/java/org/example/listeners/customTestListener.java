package org.example.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class customTestListener implements ITestListener {

    private static final String RESET = "\033[0m";
    private static final String GREEN = "\033[1;32m";
    private static final String RED = "\033[1;31m";
    private static final String YELLOW = "\033[1;33m";

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(
                result.getMethod().getDescription()
                        + "  "
                        + GREEN + "PASSED" + RESET
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(
                result.getMethod().getDescription()
                        + "  "
                        + RED + "FAILED" + RESET
        );

        if (result.getThrowable() != null) {
            System.out.println(
                    YELLOW + result.getThrowable().getMessage() + RESET
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(
                result.getMethod().getDescription()
                        + "  "
                        + YELLOW + "SKIPPED" + RESET
        );
    }
}