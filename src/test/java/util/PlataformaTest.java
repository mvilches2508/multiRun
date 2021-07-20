/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author Mario Vilches
 */
public abstract class PlataformaTest {
    private static Properties PROPIEDADES = new File_Propiedades().getProperties(TipoFile.config);
    
    private String username = PROPIEDADES.getProperty("usernameBws");
    private String accesskey = PROPIEDADES.getProperty("accesskeyBws");  
    private String gridURL = PROPIEDADES.getProperty("gridURLBws");
    
    public PlataformaTest(){
      
      DesiredCapabilities capabilities = new DesiredCapabilities();
    
      capabilities.setCapability("username", username);
      capabilities.setCapability("accessKey", accesskey);
     // capabilities.setCapability("browserName", browser);
     // capabilities.setCapability("version", version);
      //capabilities.setCapability("platform", platform); // If this cap isn't specified, it will just get the any available one
      capabilities.setCapability("name", "Pruebas Paralelas Scotiabank");
      capabilities.setCapability("extendedDebugging", true);
      capabilities.setCapability("capturePerformance", true);
      capabilities.setCapability("build", "Scotiabank");
    }
    
}
