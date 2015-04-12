package utils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * This program demonstrates using java.util.Properties class to read and write
 * settings for Java application.
 * @author www.codejava.net
 *
 */
public class Config extends JDialog {
	private File configFile = new File("config.properties");
	private Properties configProps;
        private int returnStatus = RET_CANCEL;
            /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
        public static String ip;
	
//	private JLabel labelHost = new JLabel("Host name: ");
//	private JLabel labelPort = new JLabel("Port number: ");
//	private JLabel labelUser = new JLabel("Username: ");
//	private JLabel labelPass = new JLabel("Password: ");
	
        	private JLabel labelIp = new JLabel("IP del servidor: ");

                
//	private JTextField textHost = new JTextField(20);
//	private JTextField textPort = new JTextField(20);
//	private JTextField textUser = new JTextField(20);
//	private JTextField textPass = new JTextField(20);
	
        private JTextField textIp = new JTextField(20);
        
	private JButton buttonSave = new JButton("Guardar");
	
	public Config(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
                        this.setTitle("Configuracion de ip del servidor");
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 1, 10);
		constraints.anchor = GridBagConstraints.WEST;
                 InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put("cancelar", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
                addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
    
		
//		add(labelHost, constraints);
//		
//		constraints.gridx = 1;
//		add(textHost, constraints);
//		
//		constraints.gridy = 1;
//		constraints.gridx = 0;
//		add(labelPort, constraints);
//		
//		constraints.gridx = 1;
//		add(textPort, constraints);
//
//		constraints.gridy = 2;
//		constraints.gridx = 0;
//		add(labelUser, constraints);
//		
//		constraints.gridx = 1;
//		add(textUser, constraints);
//
//		constraints.gridy = 3;
//		constraints.gridx = 0;
//		add(labelPass, constraints);
//		
//		constraints.gridx = 1;
//		add(textPass, constraints);
		
//		constraints.gridy = 4;
//		constraints.gridx = 0;
//		constraints.gridwidth = 2;
                
                
                		add(labelIp, constraints);
		
		constraints.gridx = 1;
		add(textIp, constraints);
                constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		add(buttonSave, constraints);
		
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					saveProperties();
					JOptionPane.showMessageDialog(Config.this, 
							"Properties were saved successfully!");
                                         doClose(RET_OK);
                                         
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(Config.this, 
							"Error saving properties file: " + ex.getMessage());		
				}
			}
		});
		
		        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		pack();
		setLocationRelativeTo(null);
		//setVisible(true);
		
		try {
			loadProperties();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "The config.properties file does not exist, default properties loaded.");
		}
//		textHost.setText(configProps.getProperty("host"));
//		textPort.setText(configProps.getProperty("port"));
//		textUser.setText(configProps.getProperty("user"));
//		textPass.setText(configProps.getProperty("pass"));
                
                textIp.setText(configProps.getProperty("ip"));
	}

            /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {                             
        doClose(RET_CANCEL);
    } 
    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }
    
        private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
	public void loadProperties() throws IOException {
		Properties defaultProps = new Properties();
		// sets default properties
//		defaultProps.setProperty("host", "www.codejava.net");
//		defaultProps.setProperty("port", "3306");
//		defaultProps.setProperty("user", "root");
//		defaultProps.setProperty("pass", "secret");
                
                		defaultProps.setProperty("ip", "192.168.0.1");

//		
		configProps = new Properties(defaultProps);
		
		// loads properties from file
		InputStream inputStream = new FileInputStream(configFile);
		configProps.load(inputStream);
                ip=configProps.getProperty("ip");
		inputStream.close();
	}
	
	private void saveProperties() throws IOException {
//		configProps.setProperty("host", textHost.getText());
//		configProps.setProperty("port", textPort.getText());
//		configProps.setProperty("user", textUser.getText());
//		configProps.setProperty("pass", textPass.getText());
            configProps.setProperty("ip", textIp.getText());
		OutputStream outputStream = new FileOutputStream(configFile);
		configProps.store(outputStream, "host setttings");
		outputStream.close();
	}
	
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Config dialog = new Config(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}