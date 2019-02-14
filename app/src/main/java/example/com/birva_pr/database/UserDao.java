package example.com.birva_pr.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import example.com.birva_pr.helpers.AppConstants;
import retrofit2.http.DELETE;

@Dao
public interface UserDao {
    @Query("SELECT * FROM " +AppConstants.USER_TBL + " EXCEPT SELECT * FROM "+AppConstants.USER_TBL +" WHERE email= :email")
    public List<UserDetailsBean> getUsersList(String email);
    //@Query("SELECT * FROM " + AppConstants.USER_TBL)
/*
    @Query("SELECT name FROM " + AppConstants.USER_TBL +" WHERE password= :password")
    UserDetailsBean isUserExist(String password);
*/

    @Query("SELECT EXISTS(SELECT 1 FROM "+AppConstants.USER_TBL+" WHERE email= :email)")
    Boolean isUserExist(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserDetailsBean userDetailsBean);

    @Query("SELECT password FROM " + AppConstants.USER_TBL +" WHERE email= :email")
    String getUserPassword(String email);

    @Update
    void update(UserDetailsBean userDetailsBean);

    @Query("DELETE FROM " +AppConstants.USER_TBL+ " WHERE id = :userId")
    void deleteByUserId(int userId);

    @Delete
    void deleteUser(UserDetailsBean userDetailsBean);
}
