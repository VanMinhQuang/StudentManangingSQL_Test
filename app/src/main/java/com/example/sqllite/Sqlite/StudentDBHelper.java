package com.example.sqllite.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sqllite.Model.Faculty;
import com.example.sqllite.Model.Student;
import com.example.sqllite.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class StudentDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "studentDB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_STUDENT_NAME = "Student";
    public static final String COL_Student_ID = "StudentId";
    public static final String COL_Student_NAME = "StudentName";
    public static final String COL_Student_SCORE = "AverageScore";
    public static final String COL_Student_FACULTY = "FalcultyId";
    public static final String TABLE_FALCULTY_NAME = "Faculty";
    public static final String COL_FALCULTY_ID = "FacultyId";
    public static final String COL_FALCULTY_NAME = "falcultyName";
    public boolean isInsert= false;

    public StudentDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_STUDENT_NAME + " ("
                + COL_Student_ID + " INTEGER UNIQUE PRIMARY KEY  ,   "
                + COL_Student_NAME + " TEXT,"
                + COL_Student_SCORE + " DOUBLE,"
                + COL_Student_FACULTY + " INTEGER)";
        sqLiteDatabase.execSQL(query);
        String query2 = "CREATE TABLE " + TABLE_FALCULTY_NAME + " (" + COL_FALCULTY_ID + " INTEGER PRIMARY KEY, "
                + COL_FALCULTY_NAME + " TEXT)";
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropDB = "DROP TABLE IF EXISTS " + TABLE_STUDENT_NAME;
        sqLiteDatabase.execSQL(dropDB);

        String dropDB2 = "DROP TABLE IF EXISTS " + TABLE_FALCULTY_NAME;
        sqLiteDatabase.execSQL(dropDB2);
        onCreate(sqLiteDatabase);
    }


    public List<Faculty> GetAllFalculty() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Faculty> listFalculty = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FALCULTY_NAME;
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            Faculty temp = new Faculty();
            temp.FacultyId = c.getInt(0);
            temp.FacultyName = c.getString(1);
            listFalculty.add(temp);
        }
        return listFalculty;
    }

    public ArrayList<String> GetAllNameFalculty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> listFalculty = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FALCULTY_NAME;
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            String temp;
            temp = c.getString(1);
            listFalculty.add(temp);
        }
        return listFalculty;
    }

    public List<Student> GetAllStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Student> listStudent = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_STUDENT_NAME;
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            Student temp = new Student();
            temp.maSV = c.getString(0);
            temp.tenSV = c.getString(1);
            temp.diemTB = c.getFloat(2);
            temp.Khoa = c.getString(3);
            listStudent.add(temp);
        }
        return listStudent;
    }

    public boolean Insert(Faculty f) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String query = String.format("INSERT INTO %s VALUES(%d,'%s')",
                    TABLE_FALCULTY_NAME, f.FacultyId, f.FacultyName);
            db.execSQL(query);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public void Update(Faculty f) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s = %d",
                TABLE_FALCULTY_NAME, COL_FALCULTY_NAME, f.FacultyName,
                COL_FALCULTY_ID, f.FacultyId);
        db.execSQL(query);
    }
    public void Delete(Faculty f) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("DELETE FROM %s WHERE %s = %d",
                TABLE_FALCULTY_NAME, COL_FALCULTY_ID, f.FacultyId);
        db.execSQL(query);
    }

    public void DeleteStudent(Student f) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("DELETE FROM %s WHERE %s = '%s'",
                TABLE_STUDENT_NAME, COL_Student_ID, f.maSV);
        db.execSQL(query);
    }

    public boolean InsertStudent(Student f) {
        try{

            SQLiteDatabase db = this.getWritableDatabase();
            String query = String.format("INSERT INTO %s VALUES('%s','%s',%f,'%s')",
                    TABLE_STUDENT_NAME, f.maSV, f.tenSV, f.diemTB, f.Khoa);
            db.execSQL(query);
            return true;
        }catch(SQLiteConstraintException e){
            return false;
        }

    }

    public void UpdateStudent(Student f) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE %s SET %s = '%s', %s = %f ,%s = '%s' WHERE %s = '%s'",
                TABLE_STUDENT_NAME, COL_Student_NAME, f.tenSV, COL_Student_SCORE, f.diemTB, COL_Student_FACULTY, f.Khoa,
                COL_Student_ID, f.maSV);
        db.execSQL(query);
    }


}
