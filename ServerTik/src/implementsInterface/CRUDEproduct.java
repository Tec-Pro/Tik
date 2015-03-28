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
import models.Pproduct;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDEproduct extends UnicastRemoteObject implements interfaces.InterfaceEproduct {

    /**
     * Constructor
     * @throws RemoteException
     */
    public CRUDEproduct() throws RemoteException {
        super();
    }
    
    @Override
    public Map<String, Object> create(String name, float stock, String measureUnit, int subcategory_id, List pProductsIds) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Eproduct  ret= Eproduct.createIt("name", name,"stock", stock,"measure_unit", measureUnit,"unit_price");
        Iterator it = pProductsIds.iterator();
        while (it.hasNext()){
            ret.add(Pproduct.findById(it.hasNext()));
        }
        //Buscar hijo subcategoria.
        ret.saveIt();
        ret = Eproduct.findById(ret.getId());
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, float stock, String measureUnit, int subcategory_id, List pProductsIds) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Object> getEproduct(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Map> getEproducts() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
