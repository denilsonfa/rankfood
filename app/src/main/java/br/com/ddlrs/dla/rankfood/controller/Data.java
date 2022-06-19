package br.com.ddlrs.dla.rankfood.controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Data implements Parcelable {
    private ArrayList<User> dataUser = new ArrayList<User>();
    private ArrayList<Ranking> dataRanking = new ArrayList<Ranking>();
    public boolean guestEnable = false;
    public Integer log = null;


    public Data(){ initializeValues(); }

    private User TesteUser(String nome, String email, String senha){
        User user = new User();
        user.createUser(nome,email,senha);
        return user;
    }

    private Ranking TesteRanking(String name, String description, Boolean visibility, int OwnerUserId){
        Ranking ranking = new Ranking(OwnerUserId);
        ranking.setName(name);
        ranking.setDescription(description);
        ranking.setVisibility(visibility);
        ranking.setItemOfRanking("item1");
        ranking.setItemOfRanking("item2");
        ranking.setItemOfRanking("item3");
        ranking.setItemOfRanking("item4");
        ranking.vote(0);ranking.vote(0);ranking.vote(0);
        ranking.vote(1);ranking.vote(1);
        ranking.vote(3);ranking.vote(3);ranking.vote(3);ranking.vote(3);ranking.vote(3);
        return ranking;
    }

    private void initializeValues(){
        dataUser.add(TesteUser("OLA","me@Sgam.com","123"));
        dataUser.add(TesteUser("leandro","me@sas.com","123"));
        dataUser.add(TesteUser("DSASSSS","me@oal.com","123"));

        dataRanking.add(TesteRanking("Pizzas", "Lista De Pizzas", true, 1));
        dataRanking.add(TesteRanking("Panquecas", "Lista De Panquecas", false, 2));
        dataRanking.add(TesteRanking("Lasanha", "Lista De casadas", true, 1));
    }

    protected Data(Parcel in) {
        dataUser = in.createTypedArrayList(User.CREATOR);
        dataRanking = in.createTypedArrayList(Ranking.CREATOR);
        guestEnable = in.readByte() != 0;
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
        dest.writeByte((byte) (guestEnable ? 1 : 0));
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

    public void setStatusOfinstance(Integer log, boolean guestEnable){
        this.log = log;
        this.guestEnable = guestEnable;
    }

    public void Update(Data updatedData){
        dataUser.clear();
        dataRanking.clear();
        guestEnable = updatedData.guestEnable;
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
    public int getQuantityVotes(int i)  {return dataRanking.get(i).quantityVotes();}

    public String serialize(){
        String serialize;
        serialize = "{" +
                "\"guestEnable\":" + "\"" + (guestEnable ? "true" : "false") + "\"," +
                "\"log\":" + "\"" + log + "\"," +
                "\"dataUser\": [";
        for (int i = 0; i < dataUser.size(); i++){
            User data = dataUser.get(i);
            serialize += data.serialize();
            serialize += (dataUser.size() - 1) == i ? "" : ",";
        }
        serialize += "],";

        serialize += "\"dataRanking\": [";

        for (int i = 0; i < dataRanking.size(); i++){
            Ranking data = dataRanking.get(i);
            serialize += data.serialize();
            serialize += (dataRanking.size() - 1) == i ? "" : ",";
        }
        serialize += "]";

        serialize += "}";

        return serialize;
    }

}

