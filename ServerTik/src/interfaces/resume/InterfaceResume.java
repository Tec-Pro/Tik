/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.resume;

import java.rmi.Remote;
import java.util.Date;
import java.util.List;
import java.util.Map;
import utils.Triple;


/**
 *
 * @author Alan
 */
public interface InterfaceResume extends Remote{
    
    public Map<String, Object> create(Float income, Float earning, Float expenses, Float final_balance, Date resume_date, List<Triple> listAdmin) throws java.rmi.RemoteException;

    public List<Map> getResume(Date since, Date until);
    
    public List<Map> getAdminResume(int idResume);
}
