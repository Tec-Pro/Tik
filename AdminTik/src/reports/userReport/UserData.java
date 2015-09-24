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
    
    String diaEntra;
    String horaEntra;
    String diaSale;
    String horaSale;
    String horas;

    public UserData(String diaEntra, String horaEntra, String diaSale, String horaSale, String horas) {
        this.diaEntra = diaEntra;
        this.horaEntra = horaEntra;
        this.diaSale = diaSale;
        this.horaSale = horaSale;
        this.horas = horas;
    }

    public String getDiaEntra() {
        return diaEntra;
    }

    public void setDiaEntra(String diaEntra) {
        this.diaEntra = diaEntra;
    }

    public String getHoraEntra() {
        return horaEntra;
    }

    public void setHoraEntra(String horaEntra) {
        this.horaEntra = horaEntra;
    }

    public String getDiaSale() {
        return diaSale;
    }

    public void setDiaSale(String diaSale) {
        this.diaSale = diaSale;
    }

    public String getHoraSale() {
        return horaSale;
    }

    public void setHoraSale(String horaSale) {
        this.horaSale = horaSale;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
    
   
}
