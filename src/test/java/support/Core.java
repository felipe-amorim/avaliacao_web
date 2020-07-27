package support;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Core {

    static ExtentReports extent;
    static String reportLoc = System.getProperty("user.dir") + "/src/test/resources/report/";
    static ExtentTest bddSection;

    private static WebDriver navegador;
    private static ExtentTest featureSection;
    private static ExtentTest scenarioSection;
    private static String featureName;
    private static String scenarioName;
    private static String stepName;
    private static String screenShotLocation = "";

    protected static void setWebDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--incognito");
        navegador = new ChromeDriver(options);
        navegador.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    protected static void send(String id, String text) {
        if (id.startsWith("/") || id.startsWith("(")) {
            navegador.findElement(By.xpath(id)).sendKeys(text);
        } else
            navegador.findElement(By.id(id)).sendKeys(text);

        evidence("Enviando o texto '" + text + "' para o elemento '" + id + "'");
    }

    protected static void click(String id) {
        if (id.startsWith("/") || id.startsWith("(")) {
            navegador.findElement(By.xpath(id)).click();
        } else
            navegador.findElement(By.id(id)).click();

        evidence("Clicando no elemento '" + id + "'");
    }

    protected static void navigate(String url) {
        navegador.navigate().to(url);
    }

    protected static void killWebDriver() {
        navegador.quit();
    }

    static void setFeature(String feature) {
        featureName = feature;
        featureSection = extent.createTest(feature);
        extent.flush();
    }

    static void setScenario(String scenario) {
        scenarioName = scenario;
        scenarioSection = featureSection.createNode(scenario);
        extent.flush();
    }

    static void setBdd(String bdd) {
        stepName = bdd;
        bddSection = scenarioSection.createNode(bdd);
        extent.flush();
    }

    public static void evidence(String message){
        print();
        try {
            bddSection.pass(message).addScreenCaptureFromPath(screenShotLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extent.flush();
    }

    protected String returnText(String id) {
        String retorno;
        if (id.startsWith("/") || id.startsWith("(")) {
            retorno = navegador.findElement(By.xpath(id)).getText();
        } else
            retorno = navegador.findElement(By.id(id)).getText();

        return retorno;
    }

    void createReport() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportLoc + "/report.html");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Relatório de Testes");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Relatório de Testes");
        htmlReporter.config().setTimeStampFormat("yyyy/MM/dd - HH:mm:ss.SSS");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Sistema operacional", System.getProperty("os.name"));
        extent.setSystemInfo("Arquitetura do OS", System.getProperty("os.arch"));
        extent.setSystemInfo("Versão do OS", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Usuário de execução", System.getProperty("user.name"));
        extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
        extent.flush();
    }



    public static void print() {
        featureName = featureName.replaceAll(" ", "_").replaceAll("\"", "");
        scenarioName = scenarioName.replaceAll(" ", "_").replaceAll("\"", "");
        stepName = stepName.replaceAll(" ", "_").replaceAll("\"", "");
        File screenshot = null;
        String screenshotPath = reportLoc + "/" + featureName + "/" + scenarioName + "/";
        int filesCount = 1;
        File pathEvidenciasReport = new File(screenshotPath);
        pathEvidenciasReport.mkdirs();
        while (true) {
            File f = new File(screenshotPath + stepName + "_" + filesCount + ".png");
            if (!f.exists()) {
                break;
            }
            filesCount++;
        }
        screenshot = new File(screenshotPath + stepName + "_" + filesCount + ".png");
        try {
            screenShotLocation = reportLoc + "/" + featureName + "/" + scenarioName + "/" + stepName + "_" + filesCount + ".png";
            FileUtils.moveFile(((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE), screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
