/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import models.Eproduct;
import models.EproductsPproducts;
import models.Fproduct;
import models.Pproduct;
import org.javalite.activejdbc.Base;
import utils.Pair;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDEproduct extends UnicastRemoteObject implements interfaces.InterfaceEproduct {

    /**
     * Constructor
     *
     * @throws RemoteException
     */
    public CRUDEproduct() throws RemoteException {
        super();
    }

    @Override
    public Map<String, Object> create(String name, List<Pair> pProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Eproduct ret = Eproduct.createIt("name", name);
        Iterator it = pProducts.iterator();
        while (it.hasNext()) { //creo la relacion Pproduct Eproduct
            Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
            String amount = prod.second().toString().replace(',', '.');
            EproductsPproducts.create("eproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount).saveIt();
        }
        Base.commitTransaction();
         
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, List<Pair> pProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Eproduct ret = Eproduct.findById(id);
        if (ret != null) {
            ret.setString("name", name);
            Iterator it = EproductsPproducts.find("eproduct_id = ?", id).iterator();
            while (it.hasNext()) { //elimino relaciones pre existenes
                ((EproductsPproducts) it.next()).delete();
            }
            it = pProducts.iterator();
            while (it.hasNext()) { //creo relacion entre primarios y elaborados nuevamente
                Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
                String amount = prod.second().toString().replace(',', '.');
                EproductsPproducts.create("eproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount).saveIt();
            }
            ret.saveIt();
            Base.commitTransaction();
             
            return ret.toMap();
        } else {
            Base.commitTransaction();
             
            return null;
        }
    }

    @Override
    public boolean delete(int id) throws RemoteException {
        Utils.abrirBase();
        Eproduct product = Eproduct.findById(id);
        boolean res = true;
        if (product != null) {
            Base.openTransaction();
            product.setInteger("removed", 1);
            res = res && product.saveIt();
            for (Fproduct fProdcut : product.getAll(Fproduct.class)) {
                fProdcut.set("removed", 1);
                res = res && fProdcut.saveIt();
            }
            Base.commitTransaction();
        }
         
        return res;
    }

    @Override
    public Map<String, Object> getEproduct(int id) throws RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = null;
        Eproduct eProd = Eproduct.findById(id);
        if (eProd != null) {
            ret = eProd.toMap();
        }
         
        return ret;
    }

    @Override
    public List<Map> getEproducts() throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Eproduct.where("removed = ?", 0).toMaps();
         
        return ret;
    }

    @Override
    public List<Map> getPproducts(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Eproduct eProd = Eproduct.findById(id);
        List<Map> ret = null;
        if (eProd != null) {
            ret = eProd.getAll(Pproduct.class).toMaps();
        }
         
        return ret;
    }

    @Override
    public List<Map> getEproducts(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Eproduct.where("removed = ? and (id = ? or name = ?)", 0, name, name).toMaps();
         
        return ret;
    }

    @Override
    public List<Map> getEproductPproduts(int idEproduct) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Eproduct eProd = Eproduct.findById(idEproduct);
        List<Map> ret = null;
        if (eProd != null) {
            ret = eProd.getAll(EproductsPproducts.class).toMaps();
        }
         
        return ret;

    }
}
