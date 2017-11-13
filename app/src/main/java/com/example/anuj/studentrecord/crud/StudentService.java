package com.example.anuj.studentrecord.crud;

import android.util.Log;
import com.example.anuj.studentrecord.StudentRecord;
import com.example.anuj.studentrecord.model.DaoSession;
import com.example.anuj.studentrecord.model.Student;
import com.example.anuj.studentrecord.model.StudentDao;

import java.util.List;

public class StudentService {
    private StudentDao studentDao;

    public StudentService(){
        DaoSession daoSession= StudentRecord.getInstance().getDaoSession();
        this.studentDao=daoSession.getStudentDao();
    }

    public boolean create(Student student){
        try{
            studentDao.insert(student);
            return true;
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
            return false;
        }


    }

    public Student readStudentData(long studentId){
        return studentDao.load(studentId);
    }

    public int getCount(){
        return (int)studentDao.count();
    }

    public List<Student> read(){
        return  studentDao.loadAll();
    }

    public boolean update(Student student){
        try{
            studentDao.update(student);
            return true;
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
            return false;
        }
    }

    public boolean delete(long studentId){
        try{
            studentDao.deleteByKey(studentId);
            return true;
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
            return false;
        }
    }

    public List<Student> searchByName(String name){
        return studentDao.queryBuilder().where(StudentDao.Properties.FirstName.like(name+"%")).orderAsc().list();
    }
}
