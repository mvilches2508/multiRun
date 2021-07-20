package com.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.File_Propiedades;
import util.TipoFile;
/**
 *
 * @author Mario Vilches
 */
public class PassParametersFromxml {
   private Properties PROPIEDADES = new File_Propiedades().getProperties(TipoFile.config);
   public RemoteWebDriver driver = null;
   private String baseUrl;
    
    private WebElement userRut,pass,bancaEnLinea, botonEnviar, cerrarSesion;
   
   
    //@Parameters({"browser","version","platform","os_version"})
    //@BeforeTest()
    public void setup(String browser,String version,String platform, String os_version) {
    String username = PROPIEDADES.getProperty("usernameBws");
     String accesskey = PROPIEDADES.getProperty("accesskeyBws");  
     String gridURL = PROPIEDADES.getProperty("gridURLBws");
     
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("build", "1.1 Inova");
      capabilities.setCapability("username", username);
      capabilities.setCapability("accessKey", accesskey);
      capabilities.setCapability("browser", browser);
      capabilities.setCapability("browser_version", version);
      capabilities.setCapability("os", platform); 
      capabilities.setCapability("os_version", os_version); 
      
      capabilities.setCapability("name", "Pruebas Paralelas Scotiabank");
      
      try {
          driver = new RemoteWebDriver(new URL(gridURL), capabilities);
      } catch (MalformedURLException e) {
          System.out.println("Invalid grid URL");
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
    }
    private void esperaGaussiana(int min, int max) throws InterruptedException{
        Random random = new Random();
        double gauss= random.nextGaussian();
        while(gauss <= 0){
            gauss= random.nextGaussian();
        }
        System.out.println("valor gaussiano: "+gauss);
            double val = gauss * min + max;
             int millisDelay = (int) Math.round(val);                
             Thread.sleep(millisDelay);
    }
    private void pantallazo(WebDriver webdriver, String fileWithPath) throws IOException{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
                File DestFile=new File(fileWithPath);
                FileUtils.copyFile(SrcFile, DestFile);
    }
    private String MarcaDeTiempo(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime()+"";
    }
    @Parameters({"browser","version","platform"})
    @BeforeTest()
    public void setupSauce(String browser,String version,String platform) {
      String gridURL = PROPIEDADES.getProperty("gridURLSauce");
     Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
                ChromeOptions options = new ChromeOptions();                
                options.setExperimentalOption("prefs", prefs);
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
                options.addArguments("disable-infobars");
                /*
                Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36
                Mozilla/5.0 (X11; CrOS x86_64 10066.0.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36
                */
                options.addArguments("user-agent= Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");
                options.addArguments("--incognito");
                //options.addArguments("--blink-settings=imagesEnabled=false");
                options.addArguments("--disable-blink-features=AutomationControlled");                
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--allow-insecure-localhost");
                //options.addArguments("--headless");
                options.addArguments("--window-size=1366,768");
                options.addArguments("--start-maximized");
       MutableCapabilities browserOptions =new MutableCapabilities();        
        browserOptions.setCapability("build", "build-Inovabiz");
        browserOptions.setCapability("platformName", platform);
        browserOptions.setCapability("browserVersion", version);
        browserOptions.setCapability("build", "1.1 Inova");
       // browserOptions.setCapability("capturePerformance",true);
       // browserOptions.setCapability("extendedDebugging", true);
        
        
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY,  options);
        caps.setCapability("sauce:options", browserOptions);
      
      try {
          driver = new RemoteWebDriver(new URL(gridURL), caps);
      } catch (MalformedURLException e) {
          System.out.println("Invalid grid URL");
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
    }
    @Test
    public void testEjecucion(){
          try {            
            //https://www.bancoestado.cl
            String baseUrl = "https://www.bancoestado.cl/imagenes/comun2008/banca-en-linea-personas.html";
            
             
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            driver.get(baseUrl);  
            //esperaGaussiana(987, 1985);            
            //mueveMouse();
           // System.out.println("******* apretando boton login **********");
           // bancaEnLinea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Banca en Línea')]")));
           // bancaEnLinea.click();
            esperaGaussiana(987, 1985);
           // mueveMouse();
            driver.switchTo().frame(driver.findElementByXPath("//*[@id=\"caja\"]/iframe"));
            // mueveMouse();
           userRut= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
           pass= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
           userRut.click();
             esperaGaussiana(1,200);      
            userRut.sendKeys("1");
             esperaGaussiana(1,100);
            userRut.sendKeys("7");
             esperaGaussiana(1,100);
            userRut.sendKeys("3");
             esperaGaussiana(1,385);
            userRut.sendKeys("0");
             esperaGaussiana(1,134);
            userRut.sendKeys("4");
             esperaGaussiana(1,100);
            userRut.sendKeys("7");
             esperaGaussiana(1,201);
           userRut.sendKeys("0");
             esperaGaussiana(1,100);
            userRut.sendKeys("9");
             esperaGaussiana(1,345);
            userRut.sendKeys("6");
             esperaGaussiana(2085,3987);
            pass.click();
            esperaGaussiana(550,1200);
            pass.sendKeys("h");
            esperaGaussiana(1,100);
            pass.sendKeys("i");
            esperaGaussiana(1,250);
            pass.sendKeys("p");
            esperaGaussiana(1,100);
            pass.sendKeys("2");
            esperaGaussiana(1,80);
            pass.sendKeys("7");    
            esperaGaussiana(1,80);
            pass.sendKeys("6");
            esperaGaussiana(550,1200);
           // mueveMouse();
            botonEnviar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enviar")));
            botonEnviar.click();            
            esperaGaussiana(8005,15890);
            pantallazo(driver, "src/main/resources/foto"+MarcaDeTiempo()+".jpg");
            cerrarSesion = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#header > div > div.header_session > table > tbody > tr:nth-child(2) > td:nth-child(2) > a")));
            cerrarSesion.click();
            
            
           
        } catch (Exception ex) {            
            System.out.println("error:"+ex);
            driver.quit();
           // driver.close();
        }finally{
            driver.quit();
        }
       
    }
    @AfterTest
    public void cierre(){
       Assert.assertTrue(true);
        driver.quit();  
    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        result.isSuccess();
    }
     private WebElement getElemento(RemoteWebDriver webDriver, String tipo, String dato){
        WebElement elemento = null; //Localizador
        WebDriverWait wait = null;
        try {
            switch (tipo) {
                case "ID":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(dato)));
                    break;
                case "NAME":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(dato)));
                    break;
                case "XPATH":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dato)));
                    break;                
            }
        } catch (TimeoutException ex) {
            System.out.println("Error: se ha producido una demora mayor a la esperada (30seg)" + " " + dato);
            //driver.close();
            return null;
        }
        return elemento;
    }
    
}
