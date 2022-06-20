package br.com.ddlrs.dla.rankfood.controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Parcelable {
    private String userEmail;
    private String userName;
    private String userPassword;
    private int profileImage;
    private boolean status = false;

    public User(){ }

    protected User(Parcel in) {
        userEmail = in.readString();
        userName = in.readString();
        userPassword = in.readString();
        profileImage = in.readInt();
        status = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void createUser(String name, String email, String Password, int profileImage){
        userEmail = email;
        userName = name;
        userPassword = Password;
        this.profileImage = profileImage;
    }

    public String UserEmail()       { return userEmail;             }
    public String UserName()        { return userName;              }
    public String UserPassword()    { return userPassword;          }
    public int    ProfileImage()    { return profileImage;          }

    public void UserName(String name)       { this.userName = name;             }
    public void UserPassword(String pass)   { this.userPassword = pass;         }
    public void deactivateUser()            { this.status = false;              }
    public void ProfileImage(int i)         { this.profileImage = profileImage; }
    public boolean UserEmail(String e){
        if (!validadeEmail(e)) return false;
        userEmail = e;
        return true;
    }

    public boolean validatePassword(String pass, String confirmPass){ return pass.equals(confirmPass) ? true : false; }
    public boolean validadeEmail(String e){
        boolean emailIsValid = false;
        if (e != null && e.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(e);
            if (matcher.matches()) {
                emailIsValid = true;
            }
        }

        return emailIsValid;
    }

    public Object[] getAllValues(){
        Object[] values = new Object[4];
        values[0] = userEmail;
        values[1] = userName;
        values[2] = userPassword;
        values[3] = status;
        return values;
    }

    public String serialize(){
        String serialize;
        serialize = "{" +
                "\"userEmail\":" + "\"" + userEmail + "\"," +
                "\"userName\":" + "\"" + userName + "\"," +
                "\"userPassword\":" + "\"" + userPassword + "\"," +
                "\"status\":" + "\"" +  (status == false? "false" : "true") + "\"" +
                "}";
        return serialize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userEmail);
        parcel.writeString(userName);
        parcel.writeString(userPassword);
        parcel.writeInt(profileImage);
        parcel.writeByte((byte) (status ? 1 : 0));
    }
}