package co.edu.uniminuto.registrousuarios.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ManagerDataBase extends SQLiteOpenHelper{

    private static final String DATA_BASE ="dbUsers";
    private static final int VERSION = 1;
    private static final String TABLE_USERS= "users";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_USERS +" (use_document INTEGER PRIMARY KEY NOT NULL, " +
            "use_user varchar(50) NOT NULL, use_names varchar(150) NOT NULL, user_lastNames varchar(150) NOT NULL, use_password varchar(25) NOT NULL," +
            "use_status  varchar(1) NOT NULL);";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_USERS;
    public ManagerDataBase(@Nullable Context context) {
        super(context, DATA_BASE, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
