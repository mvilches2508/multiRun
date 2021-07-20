package util;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Mario Vilches
 */
public class File_Propiedades {
final int ARCHIVO_CONFIG=1;
final int ARCHIVO_QUERY=2;

    public Properties getProperties(TipoFile tipoFile){
        
        try {
            Properties propiedades=new Properties();
            switch(tipoFile){
                case config:                   
                    propiedades.load(getClass().getResourceAsStream("/config.properties"));
                    break;
                case query:
                    propiedades.load(getClass().getResourceAsStream("/query.properties"));
                    break;
            }
            
            if(!propiedades.isEmpty())
                return propiedades;
            else
                return null;
        }catch(IOException ex)
        {
            return null;
        }
        

    }


}