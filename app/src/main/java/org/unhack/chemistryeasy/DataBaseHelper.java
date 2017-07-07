package org.unhack.chemistryeasy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
    //Data Base path
    private static String DB_PATH = "/data/data/org.unhack.chemistryeasy/assets";
    // Data Base Name.
    private static final String DATABASE_NAME = "periodic.sqlite";
    // Data Base Version.
    private static final int DATABASE_VERSION = 1;
    // Table Names of Data Base.
    static final String TABLE_Name = "Elements";

    public Context context;

    static SQLiteDatabase sqliteDataBase;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Create Empty DB
     * */
    public void createDataBase() throws IOException{
        boolean databaseExist = checkDataBase();
        if(databaseExist){
        }else {
            this.getWritableDatabase();
            copyDataBase();
        }
    }

    /**
     * Check if the database already exist. BOOl.
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    /**
     * Copies database from local assets-folder to the just created empty database
     * */
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * This method opens the data base connection.
     */
    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    public String getUserNameFromDB(){
        String query = "SELECT Name FROM Elements";
        Cursor cursor = sqliteDataBase.rawQuery(query, null);
        String userName = null;
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{
                    userName = cursor.getString(0);
                }while (cursor.moveToNext());
            }
        }
        return userName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}