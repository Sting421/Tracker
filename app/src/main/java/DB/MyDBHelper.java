package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MyDBHelper extends SQLiteOpenHelper{


        private Context context;
        private static final String DATABASE_NAME = "ConDoctor.db";
        private static final int DATABASE_VERSION = 4;

        /*  **********************************************************************
                                 Patient Information
           ********************************************************************** */
        private static final String PATIENT_TABLE = "Patients";
        private static final String Patient_ID = "_id";
        private static final String Patient_Name = "Name";
        private static final String Patient_Age = "Age";
        private static final String Patient_gender = "Gender";
        private static final String Patient_condition = "Condition";
        private static final String Patient_Doctor = "Doctor";

        /*  **********************************************************************
                             Doctors Information Registration
            ********************************************************************** */
        private static final String DOCTORS_DATA = "USERS";
        private static final String DoctorID = "_id";
        private static final String FullName = "fullname";
        private static final String DateOfBirth = "DateOfBirth";
        private static final String Specialization = "Specialization";
        private static final String COLUMN_USERNAME = "username";
        private static final String COLUMN_PASSWORD = "password";

        public MyDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query1 = "CREATE TABLE " + PATIENT_TABLE +
                    " (" + Patient_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Patient_Name + " TEXT, " +
                    Patient_Age + " TEXT, " +
                    Patient_gender + " TEXT, " +
                    Patient_condition + " TEXT, " +
                    Patient_Doctor + " TEXT);";
            db.execSQL(query1);

            String query2 = "CREATE TABLE " + DOCTORS_DATA +
                    " (" + DoctorID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    FullName + " TEXT, " +
                    DateOfBirth + " TEXT, " +
                    Specialization + " TEXT );";

            db.execSQL(query2);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
            onCreate(db);
            db.execSQL("DROP TABLE IF EXISTS " + DOCTORS_DATA);
            onCreate(db);
        }

        void addPatient(String name, String age, String gender, String condition, String username){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(Patient_Name, name);
            cv.put(Patient_Age, age);
            cv.put(Patient_gender, gender);
            cv.put(Patient_condition, condition);
            cv.put(Patient_Doctor, username);
            long result = db.insert(PATIENT_TABLE,null, cv);
            if(result == -1){
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        }
        void addUser(String username, String password, String fullName, String DOB, String specialization){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_USERNAME, username);
            cv.put(COLUMN_PASSWORD, password);

            cv.put(FullName, fullName);
            cv.put(DateOfBirth, DOB);
            cv.put(Specialization, specialization);

            long result = db.insert(DOCTORS_DATA,null, cv);
            if(result == -1){
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

        Cursor readAllData(String username) {
            String query = "SELECT * FROM " + PATIENT_TABLE + " WHERE " + Patient_Doctor + " = ?";
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(query, new String[]{username});
            }
            return cursor;
        }
        Cursor readProfile(String username) {
            String query = "SELECT * FROM " + DOCTORS_DATA + " WHERE " + COLUMN_USERNAME + " = ?";
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(query, new String[]{username});
            }
            return cursor;
        }



        void updateData(String row_id,String name, String age, String gender, String condiiton){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Patient_Age, age);
            cv.put(Patient_gender, gender);
            cv.put(Patient_condition, condiiton);

            long result = db.update(PATIENT_TABLE, cv, "_id=?", new String[]{row_id});
            if(result == -1){
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            }

        }

        void deleteOneRow(String row_id){
            SQLiteDatabase db = this.getWritableDatabase();
            long result = db.delete(PATIENT_TABLE, "_id=?", new String[]{row_id});
            if(result == -1){
                Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
        }
        public int patientCounter(String doctorname) {
            int count = 0;
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + PATIENT_TABLE + " WHERE "+Patient_Doctor+" = ?";
            Cursor cursor = db.rawQuery(query, new String[]{doctorname});

            if (cursor != null) {
                count = cursor.getCount();
                cursor.close();
            }
            db.close();

            return count;
        }


        public void deleteAllData(){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + PATIENT_TABLE);
        }

        boolean userChecker(String username, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT username FROM " + DOCTORS_DATA + " WHERE username = ? and password = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username, password});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            db.close();
            return exists;
        }
        boolean userChecker(String username) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT username FROM " + DOCTORS_DATA + " WHERE username = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            db.close();
            return exists;
        }
        boolean patientChecker(String name, String doctorName) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            boolean exists = false;

            String query = "SELECT Name FROM " + PATIENT_TABLE + " WHERE Name = ? AND Doctor = ?";
            cursor = db.rawQuery(query, new String[]{name, doctorName});
            exists = cursor.getCount() > 0;


            return exists;
        }

}
