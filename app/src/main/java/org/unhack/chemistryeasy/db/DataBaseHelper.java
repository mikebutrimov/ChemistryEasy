/*
 * Copyright ChemistryEasy Project (https://vk.com/chemistryeasyru)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.unhack.chemistryeasy.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
    public boolean isValid = false;

    static SQLiteDatabase mSqlLiteDatabase;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
        boolean databaseExist = this.checkDataBase();
        if(databaseExist){
            isValid = true;
        }else {
            this.getWritableDatabase();
            try {
                this.copyDataBase();
                isValid = true;
            } catch (IOException e) {
                e.printStackTrace();
                isValid = false;
            }
        }
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
        mSqlLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(mSqlLiteDatabase != null)
            mSqlLiteDatabase.close();
        super.close();
    }

    public String getUserNameFromDB(){
        String query = "SELECT Name FROM Elements";
        Cursor cursor = mSqlLiteDatabase.rawQuery(query, null);
        String userName = null;
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{
                    userName = cursor.getString(0);
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        return userName;
    }


    /**
     *
     * @return ArrayList<String[]> of all elements from db.
     */
    public ArrayList<String[]> getAllElementsFromDB(){
        ArrayList<String[]> allRecordsAsList = new ArrayList<>();
        try {
            Cursor cursor = mSqlLiteDatabase.rawQuery("select * from Elements", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int columns = cursor.getColumnCount();
                    String[] mElementArray = new String[columns];
                    for (int i = 0; i < columns; i++) {
                        mElementArray[i] = cursor.getString(i);
                    }
                    allRecordsAsList.add(mElementArray);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        catch (Exception e){
            //suppress exception with cursor
            e.printStackTrace();
        }
        if (allRecordsAsList.isEmpty()) return null;
        return  allRecordsAsList;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}