/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import gui.cashbox.GUICashbox;
import gui.cashbox.GuiOpenTurn;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceTurn;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.InterfaceName;

/**
 *
 * @author jacinto
 */
public class ControllerGuiOpenTurn implements ActionListener{
    GuiOpenTurn guiOpenTurn;
    //GuiResume
    GUICashbox guiCashbox;
    InterfaceTurn turn;

    public ControllerGuiOpenTurn(GuiOpenTurn guiOpenTurn, GUICashbox guiCashbox) throws RemoteException, NotBoundException {
        this.guiOpenTurn = guiOpenTurn;
        this.guiCashbox = guiCashbox;
        turn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        if (turn.isTurnOpen()){
            if (turn.getTurn().equals("M")){
                guiOpenTurn.getBtnMorning().setBackground(Color.green);
                guiOpenTurn.getBtnAfternoon().setBackground(Color.GRAY);
                //funcion saldo
                //funcion recaudado
            } else {
                guiOpenTurn.getBtnMorning().setBackground(Color.GRAY);
                guiOpenTurn.getBtnAfternoon().setBackground(Color.green);
                //funcion saldo
                //funcion recaudado
            }
        }
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiOpenTurn.getBtnMorning()) { 
            try {
                if (turn.getTurn().equals("M")){
                    //hacer calculos 
                    guiCashbox.show();
                } else {
                    //calculsos, deasbilitar
                    guiCashbox.show();               
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnAfternoon()) { 
            
        }
        if (e.getSource() == guiOpenTurn.getBtnResume()) { 
            //ABRIR VENTANA DE ALAN             
        }
    }
    
}
