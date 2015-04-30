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
    public static float percent;

    public static void saveProperties(float percent) throws IOException  {
        configProps.setProperty("percent", ParserFloat.floatToString(percent));
        OutputStream outputStream = new FileOutputStream(configFile);
        configProps.store(outputStream, "general configurations");
        outputStream.close();
    }

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

}
