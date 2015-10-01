/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.userReport;

/**
 *
 * @author alangonzalez
 */
public class UserData {
    
    Object diaEntra;
    Object horaEntra;
    Object diaSale;
    Object horaSale;
    Object horas;

    public UserData(Object diaEntra, Object horaEntra, Object diaSale, Object horaSale, Object horas) {
        this.diaEntra = diaEntra;
        this.horaEntra = horaEntra;
        this.diaSale = diaSale;
        this.horaSale = horaSale;
        this.horas = horas;
    }

    public Object getDiaEntra() {
        return diaEntra;
    }

    public void setDiaEntra(Object diaEntra) {
        this.diaEntra = diaEntra;
    }

    public Object getHoraEntra() {
        return horaEntra;
    }

    public void setHoraEntra(Object horaEntra) {
        this.horaEntra = horaEntra;
    }

    public Object getDiaSale() {
        return diaSale;
    }

    public void setDiaSale(Object diaSale) {
        this.diaSale = diaSale;
    }

    public Object getHoraSale() {
        return horaSale;
    }

    public void setHoraSale(Object horaSale) {
        this.horaSale = horaSale;
    }

    public Object getHoras() {
        return horas;
    }

    public void setHoras(Object horas) {
        this.horas = horas;
    }
    
   
}
