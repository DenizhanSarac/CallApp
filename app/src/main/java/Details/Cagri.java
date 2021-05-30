package Details;

public class Cagri {
    private String Mesaj;
    private boolean Durum=false;
    private int U_id;

    public Cagri(String mesaj, boolean durum, int u_id) {
        Mesaj = mesaj;
        Durum = durum;
        U_id = u_id;
    }
    public Cagri(String mesaj,boolean durum)
    {
        Mesaj = mesaj;
        this.Durum = durum;
    }
    public Cagri(String mesaj,int u_id){
        Mesaj=mesaj;
        U_id=u_id;
    }
    public Cagri(){}

    public String getMesaj() {
        return Mesaj;
    }

    public void setMesaj(String mesaj) {
        Mesaj = mesaj;
    }

    public boolean getDurum() {
        return Durum;
    }

    public void setDurum(boolean durum) {
        Durum = durum;
    }

    public int getU_id() {
        return U_id;
    }

    public void setU_id(int u_id) {
        U_id = u_id;
    }


}
