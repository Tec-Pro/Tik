/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import controllers.ControllerMain;
import static controllers.ControllerMain.guiMain;
import gui.cashbox.GUICashbox;
import gui.cashbox.GuiOpenTurn;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceOrder;
import interfaces.InterfaceTurn;
import interfaces.cashbox.expenses.InterfaceExpenses;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author jacinto
 */
public class ControllerGuiOpenTurn implements ActionListener {

    GuiOpenTurn guiOpenTurn;
    //GuiResume
    GUICashbox guiCashbox;
    InterfaceTurn turn;
    InterfaceOrder order;
    InterfaceExpenses crudExpenses;

    public ControllerGuiOpenTurn(GuiOpenTurn guiOpenTurn, GUICashbox guiCashbox) throws RemoteException, NotBoundException {
        this.guiOpenTurn = guiOpenTurn;
        this.guiCashbox = guiCashbox;
        turn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        order = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudExpenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        turn();
        guiOpenTurn.setActionListener(this);
    }
    
    /**
     * Funcion para actualizar los txt, lbl y color del boton segun el turno
     * @throws RemoteException 
     */

    public void turn() throws RemoteException {
        if (turn.isTurnOpen()) {
            if (turn.getTurn().equals("M")) {
                guiOpenTurn.getBtnMorning().setBackground(Color.green);
                guiOpenTurn.getBtnAfternoon().setBackground(Color.GRAY);                
                guiOpenTurn.getLblMGain().setText(ParserFloat.floatToString(order.totalEarn()));
                //funcion saldo
              //funcion recaudado
            } else {
                guiOpenTurn.getBtnMorning().setBackground(Color.GRAY);
                guiOpenTurn.getBtnAfternoon().setBackground(Color.green);
                guiOpenTurn.getLblAGain().setText(ParserFloat.floatToString(order.totalEarn()));
                //funcion saldo
                //funcion recaudado
            }
        } else {
            guiOpenTurn.getBtnMorning().setBackground(Color.GRAY);
            guiOpenTurn.getBtnAfternoon().setBackground(Color.GRAY);
            //funcion saldo
            //funcion recaudado
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiOpenTurn.getBtnMorning()) {
            try {
                if (turn.getTurn().equals("M")) {
                    //hacer calculos 
                } else {
                    //calculsos, deasbilitar         
                }
                try {
                    guiCashbox.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                guiCashbox.setVisible(true);
                guiCashbox.toFront();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnAfternoon()) {
            try {
                if (turn.getTurn().equals("T")) {
                    //hacer calculos 
                } else {
                    //calculsos, deasbilitar            
                }
                try {
                    guiCashbox.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                guiCashbox.setVisible(true);
                guiCashbox.toFront();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnResume()) {
            //ABRIR VENTANA DE ALAN             
        }
    }
}
