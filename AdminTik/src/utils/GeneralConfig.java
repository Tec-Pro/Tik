/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import static utils.Config.ip;

/**
 *
 * @author NicoOrcasitas
 */
public class GeneralConfig {

    private static File configFile = new File("generalConfig.properties");
    private static Properties configProps;

    /**
     *
     */
    public static float percent;

    /**
     *guarda en un archivo de propiedades el porcentaje de ganancias
     * @param percent porcentaje de ganancia
     * @throws IOException
     */
    public static void saveProperties(float percent) throws IOException  {
        configProps.setProperty("percent", ParserFloat.floatToString(percent));
        OutputStream outputStream = new FileOutputStream(configFile);
        configProps.store(outputStream, "general configurations");
        outputStream.close();
    }

    /**
     *carga las propieades del archivo de configuracion
     * @throws IOException
     */
    public static void loadProperties() throws IOException {
        Properties defaultProps = new Properties();
        defaultProps.setProperty("percent", ParserFloat.floatToString((float)100));
        configProps = new Properties(defaultProps);
        // loads properties from file
        InputStream inputStream = new FileInputStream(configFile);
        configProps.load(inputStream);
        percent = ParserFloat.stringToFloat(configProps.getProperty("percent"));
        inputStream.close();

    }

    /**
     *crea un archivo de configuracion por defaul con 100% de ganancias
     * @throws IOException
     */
    public static void createDefaultProperties() throws IOException  {
        configProps.setProperty("percent", ParserFloat.floatToString((float)100));
        OutputStream outputStream = new FileOutputStream(configFile);
        configProps.store(outputStream, "general configurations");
        outputStream.close();
    }
    
}
