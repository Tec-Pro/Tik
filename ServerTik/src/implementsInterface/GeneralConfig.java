/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceGeneralConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NicoOrcasitas
 */
public class GeneralConfig  extends UnicastRemoteObject implements InterfaceGeneralConfig{

    private static final File configFile = new File("generalConfig.properties");
    private static Properties configProps;
    public static String delayTime;//tiempo maximo de retraso de un pedido

    /**
     * Constructor
     *
     * @throws RemoteException
     */
    public GeneralConfig() throws RemoteException {
        super();
    }
    
    
    /**
     *guarda en un archivo de propiedades el tiempo maximo de retraso de un pedido
     * @param delayT porcentaje de ganancia
     */
    @Override
    public void saveProperties(String delayT)  {
        configProps.setProperty("delayTime", delayT);
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(configFile);
            configProps.store(outputStream, "general configurations");
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(GeneralConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     *carga las propieades del archivo de configuracion
     * 20 min por defecto
     */
    @Override
    public void loadProperties() {
        Properties defaultProps = new Properties();
        defaultProps.setProperty("delayTime", "20");
        configProps = new Properties(defaultProps);
        // loads properties from file
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(configFile);
            configProps.load(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(GeneralConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        delayTime = configProps.getProperty("delayTime");
    }

    /**
     *crea un archivo de configuracion por defaul con 20 min de aceptacion en el retraso de un pedido
     */
    @Override
    public void createDefaultProperties()  {
        configProps.setProperty("delayTime", "20");
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(configFile);
            configProps.store(outputStream, "general configurations");
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(GeneralConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
