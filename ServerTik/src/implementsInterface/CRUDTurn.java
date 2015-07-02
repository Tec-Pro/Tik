/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceTurn;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import models.Inning;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDTurn extends UnicastRemoteObject implements interfaces.InterfaceTurn {

    public CRUDTurn() throws RemoteException {
        super();
    }

    @Override
    public boolean isTurnOpen() throws RemoteException {
        Utils.abrirBase();
        String t =  Inning.findById(1).getString("turn");
        return !t.equals("N");
    }

    @Override
    public String getTurn() throws RemoteException {
    Utils.abrirBase();
    return Inning.findById(1).getString("turn");
    }

    @Override
    public boolean changeTurn(String t) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean ret = Inning.findById(1).setString("turn", t).saveIt();
        Base.commitTransaction();
        return ret;
    }
}
