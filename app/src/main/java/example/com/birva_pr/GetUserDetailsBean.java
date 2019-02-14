package example.com.birva_pr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserDetailsBean {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private UserBean data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBean getUserBean() {
        return data;
    }

    public void setUserBean(UserBean data) {
        this.data = data;
    }
}

