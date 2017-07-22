package com.ipfaffen.pomodoro.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Isaias Pfaffenseller
 */
public class DataBaseHelper extends DatabaseOpenHelper {

    private Context context;
    public static final int SCHEMA_VERSION = 1;

    public SQLiteDatabase myDataBase;
    private final String DB_PATH;
    private final String DB_NAME;

    /**
     * @param context
     * @param name
     * @throws IOException
     */
    public DataBaseHelper(Context context, String name) throws IOException {
        super(context, name, null, SCHEMA_VERSION);
        this.context = context;
        DB_NAME = name;
        DB_PATH = String.format("/data/data/%s/databases/", this.context.getApplicationContext().getPackageName());
        boolean dbExist = checkDatabase();
        if(dbExist) {
            openDatabase();
        }
        else {
            System.out.println("Database does not exist.");
            createDatabase();
        }
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if(dbExist) {
           return;
        }
        getReadableDatabase();
        try {
            copyDatabase();
        }
        catch(IOException e) {
            throw new Error("Error copying database.");
        }
    }

    /**
     * @return
     */
    private boolean checkDatabase() {
        boolean checkDb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkDb = dbFile.exists();
        }
        catch(SQLiteException e) {
            System.out.println(e.getMessage());
        }
        return checkDb;
    }

    private void copyDatabase() throws IOException {
        //Open your local db as the input stream.
        InputStream input = context.getAssets().open(DB_NAME);

        //Open the empty db as the output stream.
        OutputStream output = new FileOutputStream(DB_PATH + DB_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while((length = input.read(buffer))>0) {
            output.write(buffer,0,length);
        }
        output.flush();
        output.close();
        input.close();
    }

    public void openDatabase() throws SQLException {
        myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}