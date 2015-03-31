/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.Eproduct;
import models.Fproduct;
import models.Pproduct;
import models.Subcategory;
import org.javalite.activejdbc.Base;
import utils.Utils;

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

    @Override
    public Map<String, Object> create(String name, float stock, String measureUnit, float unitPrice, int subcategory_id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        String st = String.valueOf(stock).replace(',', '.');
        String unitP = String.valueOf(unitPrice).replace(',', '.');
        Pproduct ret = Pproduct.createIt("name", name, "stock", st, "measure_unit", measureUnit, "unit_price", unitPrice, "subcategory_id", subcategory_id);
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, float stock, String measureUnit, float unitPrice, int subcategory_id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        Map<String, Object> res = null;
        if (product != null) {
            Base.openTransaction();
            String st = String.valueOf(stock).replace(',', '.');
            String unitP = String.valueOf(unitPrice).replace(',', '.');
            product.setString("name", name);
            product.setFloat("stock", st);
            product.setString("measure_unit", measureUnit);
            product.setFloat("unit_price", unitP);
            product.set("subcategory_id", subcategory_id);
            product.saveIt();
            res = product.toMap();
            Base.commitTransaction();
        }
        Utils.cerrarBase();
        return res;
    }

    @Override
    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        boolean res = true;
        if (product != null) {
            Base.openTransaction();
            product.setInteger("removed", 1);
            res = res && product.saveIt();
            CRUDEproduct crudE = new CRUDEproduct();
            for (Eproduct eProdcut : product.getAll(Eproduct.class)) {
                eProdcut.setInteger("removed", 1);
                res = res && eProdcut.saveIt();
                for (Fproduct fProdcut : eProdcut.getAll(Fproduct.class)) {
                    fProdcut.set("removed", 1);
                    res = res && fProdcut.saveIt();
                }
            }
            for (Fproduct fProdcut : product.getAll(Fproduct.class)) {
                fProdcut.set("removed", 1);
                res = res && fProdcut.saveIt();
            }
            Base.commitTransaction();
        }
        Utils.cerrarBase();
        return res;
    }

    @Override
    public Map<String, Object> getPproduct(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = null;
        Pproduct product = Pproduct.findById(id);
        if (product != null) {
            ret = product.toMap();
        }
        Utils.cerrarBase();
        return ret;
    }

    @Override
    public List<Map> getPproducts() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Pproduct.where("removed = ?", 0).toMaps();
        Utils.cerrarBase();
        return ret;
    }
}
