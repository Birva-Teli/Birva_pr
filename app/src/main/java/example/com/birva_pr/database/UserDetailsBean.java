package example.com.birva_pr.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import example.com.birva_pr.helpers.AppConstants;

@Entity(tableName = AppConstants.USER_TBL)
public class UserDetailsBean implements Parcelable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "gender")
    private Integer gender;

    @ColumnInfo(name = "mobNo")
    private String mobNo;

    @ColumnInfo(name = "gyrg")
    private String gyrg;
    //setter methods

    public UserDetailsBean() {

    }
    protected UserDetailsBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        if (in.readByte() == 0) {
            gender = null;
        } else {
            gender = in.readInt();
        }
        mobNo = in.readString();
        gyrg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        if (gender == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(gender);
        }
        dest.writeString(mobNo);
        dest.writeString(gyrg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDetailsBean> CREATOR = new Creator<UserDetailsBean>() {
        @Override
        public UserDetailsBean createFromParcel(Parcel in) {
            return new UserDetailsBean(in);
        }

        @Override
        public UserDetailsBean[] newArray(int size) {
            return new UserDetailsBean[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    //getter methods

    public int getId() {
        return this.id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Integer getGender() {
        return gender;
    }
    public String getMobNo() {
        return mobNo;
    }

    public String getGyrg() {
        return gyrg;
    }

    public void setGyrg(String gyrg) {
        this.gyrg = gyrg;
    }

    @Override
    public String toString() {
        return "UserDetailsBean{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", mobNo='" + mobNo + '\'' +
                '}';
    }
}
