/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import models.Pproduct;
import org.javalite.activejdbc.Base;
import utils.Utils;
import models.FproductsEproducts;
import models.FproductsPproducts;

/**
 *
 * @author jacinto
 */
public class CRUDPproduct extends UnicastRemoteObject implements interfaces.InterfacePproduct {

    /**
     * Constructor
     *
     * @throws RemoteException
     */
    public CRUDPproduct() throws RemoteException {
        super();
    }

    public Map<String, Object> create(String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        String st = String.valueOf(stock).replace(',', '.');
        String unitP = String.valueOf(unitPrice).replace(',', '.');
        Map<String, Object> ret = Pproduct.createIt("name", name, "stock", st, "measure_unit", measureUnit, "unit_price", unitPrice).toMap();
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret;
    }

    public Map<String, Object> modify(int id, String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        Map<String, Object> res = null;
        if (product != null) {
            String st = String.valueOf(stock).replace(',', '.');
            String unitP = String.valueOf(unitPrice).replace(',', '.');
            product.setString("name", name);
            product.setFloat("stock", st);
            product.setString("measure_unit", measureUnit);
            product.setFloat("unit_price", unitP);
            Base.openTransaction();
            product.saveIt();
            res = product.toMap();
            Base.commitTransaction();
        }
        Utils.cerrarBase();
        return res;
    }

    //FALTA ELIMINAR LAS MOVIDAS DONDE PERTENECE
    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        boolean res = false;
        if (product != null) {
            Base.openTransaction();
            product.setInteger("removed", 1);
            res = product.saveIt();           
            //FALTA ELIMINAR RELACIONES.
            Base.commitTransaction();
        }
        Utils.cerrarBase();
        return res;
    }

    public Map<String, Object> getPproduct(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Pproduct.findById(id).toMap();
        Utils.cerrarBase();
        return ret;
    }

    public List<Map> getPproducts() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Pproduct.where("removed = ?", 0 ).toMaps();
        Utils.cerrarBase();
        return ret;
    }
}
