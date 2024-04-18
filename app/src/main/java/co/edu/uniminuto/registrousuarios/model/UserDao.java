package co.edu.uniminuto.registrousuarios.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Currency;

import co.edu.uniminuto.registrousuarios.entity.User;

public class UserDao {
    private ManagerDataBase managerDataBase;
    Context context;
    View view;
    private User user;

    public UserDao(Context context, View view) {
        this.context = context;
        this.view = view;
        managerDataBase = new ManagerDataBase(this.context);

    }
    //Metodo creación de usuarios
    public void insertUser(User user){
        try {
            SQLiteDatabase db = managerDataBase.getWritableDatabase();
            if(db != null){
                ContentValues values = new ContentValues();
                values.put("use_document", user.getDocument());
                values.put("use_user",user.getUser());
                values.put("use_names",user.getNames()  );
                values.put("use_lastNames",user.getLastNames());
                values.put("use_password",user.getPassword());
                values.put("use_status","1");
                long cod = db.insert("users",null,values);
                Snackbar.make(this.view,"Se ha registrado el usuario:" + cod,Snackbar.LENGTH_LONG).show();
            }else{
                Snackbar.make(this.view,"No se ha registrado el usuario",Snackbar.LENGTH_LONG).show();
            }

        }catch (SQLException e){
            Log.i("BD",""+e);
        }

    }
    public ArrayList<User> getUserList(){
        ArrayList<User> listUsers = new ArrayList<>();
        try{
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "select * from users where use_status = 1;";
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    User user1 = new User();
                    user1.setDocument(cursor.getInt(0));
                    user1.setUser(cursor.getString(1));
                    user1.setNames(cursor.getString(2));
                    user1.setLastNames(cursor.getString(3));
                    user1.setPassword(cursor.getString(4));
                    listUsers.add(user1);

                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();

        }catch (SQLException e){
            Log.i("BD",""+e);
        }
        return listUsers;
    }
    // actualización de datos.
    public void updateUser(User user){
        try {
            SQLiteDatabase db = managerDataBase.getWritableDatabase();
            if(db != null){
                ContentValues values = new ContentValues();
                values.put("use_document", user.getDocument());
                values.put("use_user",user.getUser());
                values.put("use_names",user.getNames()  );
                values.put("use_lastNames",user.getLastNames());
                values.put("use_password",user.getPassword());
                String whereClause = "use_document = ?";
                String[] whereArgs = { String.valueOf(user.getDocument()) };
                int rowsAffected = db.update("users", values, whereClause, whereArgs);
                if (rowsAffected > 0) {
                    Snackbar.make(this.view,"Se ha actualizado el usuario",Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(this.view,"No se ha encontrado el usuario para actualizar",Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(this.view,"No se ha actualizado el usuario",Snackbar.LENGTH_LONG).show();
            }

        }catch (SQLException e){
            Log.i("BD",""+e);
        }
    }

    //metodo para dar de baja al usuario
    public void deleteUser(int document){
        try {
            SQLiteDatabase db = managerDataBase.getWritableDatabase();
            if(db != null){
                ContentValues values = new ContentValues();
                values.put("use_status", 0);
                String whereClause = "use_document = ?";
                String[] whereArgs = { String.valueOf(user.getDocument()) };
                int rowsAffected = db.update("users", values, whereClause, whereArgs);
                if (rowsAffected > 0) {
                    Snackbar.make(this.view,"Se ha dado de baja el usuario",Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(this.view,"No se ha encontrado el usuario para dar de baja",Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(this.view,"No se ha dado de baja el usuario",Snackbar.LENGTH_LONG).show();
            }

        }catch (SQLException e){
            Log.i("BD",""+e);
        }
    }
    //Metodo para buscar usuario
    public User getUserByDocument(int document) {
        try {
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "select * from users where use_document = ? and use_status = 1;";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(document)});
            if (cursor.moveToFirst()) {
                User user = new User();
                user.setDocument(cursor.getInt(0));
                user.setUser(cursor.getString(1));
                user.setNames(cursor.getString(2));
                user.setLastNames(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                cursor.close();
                db.close();
                return user;
            } else {
                cursor.close();
                db.close();
                return null;
            }
        } catch (SQLException e) {
            Log.i("BD", "" + e);
            return null;
        }
    }
}
