package controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Data implements Parcelable {
    private ArrayList<User> dataUser = new ArrayList<User>();
    private ArrayList<Ranking> dataRanking = new ArrayList<Ranking>();
    public boolean activate = false;
    public boolean guest = false;
    public Integer log = null;


    public Data(){ initializeValues(); }

    private void initializeValues(){
        User teste = new User();
        teste.createUser("leandro","me@sas.com","123");
        dataUser.add(teste);

        User teste2 = new User();
        teste2.createUser("OLA","me@Sgam.com","123");
        dataUser.add(teste2);

        User teste3 = new User();
        teste3.createUser("DSASSSS","me@oal.com","123");
        dataUser.add(teste3);
    }

    protected Data(Parcel in) {
        dataUser = in.createTypedArrayList(User.CREATOR);
        dataRanking = in.createTypedArrayList(Ranking.CREATOR);
        activate = in.readByte() != 0;
        guest = in.readByte() != 0;
        if (in.readByte() == 0) {
            log = null;
        } else {
            log = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(dataUser);
        dest.writeTypedList(dataRanking);
        dest.writeByte((byte) (activate ? 1 : 0));
        dest.writeByte((byte) (guest ? 1 : 0));
        if (log == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(log);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };


    public void setStatusOfinstance(Integer log, boolean activate, boolean guest){
        this.log = log;
        this.activate = activate;
        this.guest = guest;
    }

    public void Update(Data updatedData){
        dataUser.clear();
        dataRanking.clear();
        activate = updatedData.activate;
        guest = updatedData.guest;
        log = updatedData.log;

        ArrayList<User> updatedDataUser = updatedData.getDataUser();
        ArrayList<Ranking> updatedDataRanking = updatedData.getDataRanking();

        for (int i = 0; i < updatedDataUser.size(); i++){
            dataUser.add(updatedDataUser.get(i));
        }

        for (int i = 0; i < updatedDataRanking.size(); i++){
            dataRanking.add(updatedDataRanking.get(i));
        }
    }

    public ArrayList<User> getDataUser()    { return dataUser;              }
    public User getDataUser(int i)          { return dataUser.get(i);       }
    public void setDataUser(User dataUser)  { this.dataUser.add(dataUser);  }
    public boolean setDataUser(int i, User user)  {
        if(!dataUser.contains(i)){
            dataUser.set(i, user);
            return true;
        }
        return false;
    }

    public ArrayList<Ranking> getDataRanking() { return dataRanking; }
    public Ranking getDataRanking(int i) { return dataRanking.get(i); }
    public void setDataRanking(Ranking dataRanking)  { this.dataRanking.add(dataRanking);  }
    public boolean setDataRanking(int i, Ranking ranking)  {
        if(!dataRanking.contains(i)){
            dataRanking.set(i, ranking);
            return true;
        }
        return false;
    }

}
