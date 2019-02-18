package example.com.birva_pr.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ImageDetailsBean implements Parcelable {
    private String image;
    private int id;

    public ImageDetailsBean(String image,int id) {
        this.image=image;
        this.id=id;
    }

    public ImageDetailsBean() {
    }

    protected ImageDetailsBean(Parcel in) {
        image = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageDetailsBean> CREATOR = new Creator<ImageDetailsBean>() {
        @Override
        public ImageDetailsBean createFromParcel(Parcel in) {
            return new ImageDetailsBean(in);
        }

        @Override
        public ImageDetailsBean[] newArray(int size) {
            return new ImageDetailsBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public ImageDetailsBean create(String serializedData) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(serializedData, ImageDetailsBean.class);
    }
}
