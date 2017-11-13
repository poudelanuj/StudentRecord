package com.example.anuj.studentrecord;

import android.app.Application;
import android.content.Context;
import com.example.anuj.studentrecord.model.DaoMaster;
import com.example.anuj.studentrecord.model.DaoSession;
import org.greenrobot.greendao.database.Database;

public class StudentRecord extends Application{


    private DaoSession daoSession;
    private static StudentRecord studentInstance;


    public static StudentRecord getInstance(){
        return studentInstance;
    }
    public static StudentRecord get(Context context){
        return (StudentRecord) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDaoSession();
        studentInstance=this;

    }

    private void initDaoSession(){
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(this,"student_db.db");
        Database database=devOpenHelper.getWritableDb();
        daoSession=new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession() {
        if(daoSession==null){
            initDaoSession();
        }
        return daoSession;
    }
}

