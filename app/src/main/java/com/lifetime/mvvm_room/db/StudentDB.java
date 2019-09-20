package com.lifetime.mvvm_room.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lifetime.mvvm_room.dao.StudentDao;
import com.lifetime.mvvm_room.model.Student;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDB extends RoomDatabase {
    private static final String DB_NAME = "STUDENT_DB";
    private static StudentDB instance;

    public static synchronized StudentDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),StudentDB.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDBAsyncTask(instance).execute();
        }
    };

    public abstract StudentDao studentDao();

    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        private StudentDao studentDao;

        public PopulateDBAsyncTask(StudentDB instance){
            this.studentDao = instance.studentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.insert(new Student("Lam Ngoc Hai","Thanh pho Ho Chi Minh","Cong nghe thong tin","11/09/1998",false,"male"));
            studentDao.insert(new Student("Pham Tuan Lam","Thanh pho Lao Cai","Giao thong van tai","08/08/1995",false,"male"));
            studentDao.insert(new Student("Truong Ngoc Anh","Thanh pho Ho Chi Minh","Boc banh tra tien","11/09/2000",false,"female"));
            studentDao.insert(new Student("Hoang Anh Tu","Thanh pho Ha Noi","Le luoi liem tem","11/04/1993",false,"male"));

            return null;
        }
    }
}
