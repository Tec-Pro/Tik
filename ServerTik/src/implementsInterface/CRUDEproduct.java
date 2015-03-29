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
import models.Pproduct;
import models.Subcategory;
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
    public Map<String, Object> create(String name, float stock, String measureUnit, int subcategory_id, List<Pair> pProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        String st = String.valueOf(stock).replace(',', '.');
        Eproduct ret = Eproduct.createIt("name", name, "stock", st, "measure_unit", measureUnit, "unit_price");
        Iterator it = pProducts.iterator();
        while (it.hasNext()) { //creo la relacion Pproduct Eproduct
            Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
            String amount = prod.second().toString().replace(',', '.');
            EproductsPproducts.create("eproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount);
        }
        ret.add(Subcategory.findById(subcategory_id));
        ret.saveIt();
        ret = Eproduct.findById(ret.getId());
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, float stock, String measureUnit, int subcategory_id, List<Pair> pProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Eproduct ret = Eproduct.findById(id);
        if (ret != null) {
            ret.setString("name", name);
            String st = String.valueOf(stock).replace(',', '.');
            ret.set("stock", st);
            ret.setString("measure_unit", measureUnit);
            ret.set("subcategory_id",null);
            Iterator it = EproductsPproducts.find("eproduct_id = ?", id).iterator();
            while (it.hasNext()) { //elimino relaciones pre existenes
                ((EproductsPproducts) it.next()).delete();
            }
            it = pProducts.iterator();
            while (it.hasNext()) { //creo relacion entre primarios y elaborados nuevamente
                Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
                String amount = prod.second().toString().replace(',', '.');
                EproductsPproducts.create("eproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount);
            }
            ret.add(Subcategory.findById(subcategory_id));
            ret.saveIt();
            ret = Eproduct.findById(ret.getId());
            Base.commitTransaction();
            Utils.cerrarBase();
            return ret.toMap();
        } else {
            Base.commitTransaction();
            Utils.cerrarBase();
            return null;
        }
    }

    @Override //FALTA ELIMINAR LAS MOVIDAS DONDE PERTENECE
    public boolean delete(int id) throws RemoteException {
        Utils.abrirBase();
        Eproduct product = Eproduct.findById(id);
        boolean res = false;
        if (product != null) {
            Base.openTransaction();
            res = product.delete();
            Base.commitTransaction();
        }
        Utils.cerrarBase();
        return res;
    }

    @Override
    public Map<String, Object> getEproduct(int id) throws RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Eproduct.findById(id).toMap();
        Utils.cerrarBase();
        return ret;
    }

    @Override
    public List<Map> getEproducts() throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Eproduct.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }

    public List<Map> getPproducts(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Eproduct eProd = Eproduct.findById(id);
        List<Map> ret = eProd.getAll(Pproduct.class).toMaps();
        Utils.cerrarBase();
        return ret;
    }
}
