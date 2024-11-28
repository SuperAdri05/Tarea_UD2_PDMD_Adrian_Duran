package com.example.tarea_ud2.Resources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tarea_ud2.Models.User;
import com.example.tarea_ud2.Models.Utils;

import java.util.ArrayList;
import java.util.jar.JarFile;

public class DBAccess extends SQLiteOpenHelper {
    //Database name
    private static final String DB_NAME = "db_damUsers";

    //Table name
    private static final String DB_TABLE_NAME = "db_users";

    //Database version must be >= 1
    private static final int DB_VERSION = 2;

    //Columns
    private static final String NAME_COLUMN = "userName";

    private static final String EMAIL_COLUMN = "email";

    private static final String AGE_COLUMN = "age";

    private static final String IMAGE_COLUMN = "imageResource";

    //Application Context
    private Context mContext;

    public DBAccess(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE =  "CREATE TABLE " +DB_TABLE_NAME+ "("
                +NAME_COLUMN+ " TEXT,"
                + EMAIL_COLUMN + " TEXT,"
                + AGE_COLUMN + " INTEGER,"
                + IMAGE_COLUMN + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        Log("Tablas creadas");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(sqLiteDatabase);
        }


    public void getVersionDB() {
        Log(Integer.toString(this.getReadableDatabase().getVersion()));
    }


    public long insert(String name, String email, int age, int imageResourceId) {
        long result = -1;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(NAME_COLUMN, name);
            values.put(EMAIL_COLUMN, email);
            values.put(AGE_COLUMN, age);
            values.put(IMAGE_COLUMN, imageResourceId);
            result = db.insert(DB_TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("DB", "Error al insertar datos: " + e.getMessage());
        }
        return result;
    }


    public String getFirstName() {
        String result = null;
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor c = db.query(DB_TABLE_NAME, new String[]{NAME_COLUMN}, null, null, null, null, null)) {
            if (c.moveToFirst()) {
                result = c.getString(0);
            }
        } catch (Exception e) {
            Log.e("DB", "Error al obtener el nombre: " + e.getMessage());
        }
        return result;
    }

    public void getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + DB_TABLE_NAME;

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(query, null)) {

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_COLUMN));
                    int age = cursor.getInt(cursor.getColumnIndexOrThrow(AGE_COLUMN));
                    int imageResource = cursor.getInt(cursor.getColumnIndexOrThrow(IMAGE_COLUMN));

                    User newUser = new User(name, email, age, imageResource);
                    Utils.userList.add(newUser);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log("Error al cargar usuarios: " + e.getMessage());
        }

    }


    public void Log(String msg){
        Log.d("DB", msg);
    }
}
