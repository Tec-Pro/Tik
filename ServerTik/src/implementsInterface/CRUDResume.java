/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.resume.InterfaceResume;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import models.resume.Adminresume;
import models.resume.Resume;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import utils.Pair;
import utils.Triple;
import utils.Utils;

/**
 *
 * @author Alan
 */
public class CRUDResume extends UnicastRemoteObject implements InterfaceResume{

        /**
     *
     * @throws RemoteException
     */
    public CRUDResume() throws RemoteException {
        super();
    }
    
    @Override
    public Map<String, Object> create(Float income, Float earning, Float expenses, Float final_balance, Date resume_date, List<Triple> listAdmin) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Resume r = Resume.createIt("income", income, "earning", earning, "expenses", expenses, "final_balance", final_balance,"resume_date", resume_date);
        r.saveIt();
        for(Triple t : listAdmin){
            Adminresume ar = Adminresume.createIt("resume_id",r.getId(),"admin",t.getName(),"deposit",t.getDeposit(),"withdrawal",t.getWithdrawal());
            ar.saveIt();
        }
        Base.commitTransaction();
        return r.toMap();
    }
    
}
