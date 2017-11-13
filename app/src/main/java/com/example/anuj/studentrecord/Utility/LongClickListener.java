package com.example.anuj.studentrecord.Utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import com.example.anuj.studentrecord.R;
import com.example.anuj.studentrecord.crud.StudentService;
import com.example.anuj.studentrecord.main.MainActivity;
import com.example.anuj.studentrecord.model.Student;


public class LongClickListener implements View.OnLongClickListener {
    Context context;
    String id;


    @Nullable @BindView(R.id.edit_Firstname)
    EditText edit_Firstname;
    @Nullable @BindView(R.id.edit_Lastname)
    EditText edit_Lastname;
    @Nullable @BindView(R.id.edit_Address)
    EditText edit_Address;
    @Nullable @BindView(R.id.edit_Age)
    EditText edit_Age;
    @Nullable @BindView(R.id.button_save)
    Button button_save;


    @Override
    public boolean onLongClick(View view) {
        context=view.getContext();
        id=view.getTag().toString();
        final CharSequence[] items={"Edit","Delete"};

        new AlertDialog.Builder(context).setTitle("Student Id: "+id).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    editRecord(Long.parseLong(id));
                }else if(i==1){
                    deleteRecord(Long.parseLong(id));
                }
            }
        }).show();
        return false;
    }


    public void editRecord(long studentId){
        final Student student=new StudentService().readStudentData(studentId);
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.edit);
        ButterKnife.bind(this,dialog);
        edit_Firstname.setText(student.getFirstName());
        edit_Lastname.setText(student.getLastName());
        edit_Address.setText(student.getAddress());
        edit_Age.setText(Integer.toString(student.getAge()));

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname=edit_Firstname.getText().toString();
                String lname=edit_Lastname.getText().toString();
                String age=edit_Age.getText().toString();
                String address=edit_Address.getText().toString();
                Student objStudent=new Student();
                objStudent.setId(student.getId());
                objStudent.setFirstName(fname);
                objStudent.setLastName(lname);
                objStudent.setAddress(address);
                objStudent.setAge((int) (!age.isEmpty() ? Integer.parseInt(age):0));
                boolean updateStatus=new StudentService().update(objStudent);
                if(updateStatus){
                    Toast.makeText(context,"Edit Successful",Toast.LENGTH_LONG).show();
                    ((MainActivity) context).readRecords();
                    ((MainActivity)context).countRecords();
                    dialog.dismiss();

                }else{
                    Toast.makeText(context,"Edit Failed",Toast.LENGTH_LONG).show();

                }

            }

        });


    }

    public void deleteRecord(long studentId){
        boolean deleteSuccessful = new StudentService().delete(studentId);
        if (deleteSuccessful){
            Toast.makeText(context, "Student record was deleted.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Unable to delete student record.", Toast.LENGTH_SHORT).show();
        }

        ((MainActivity) context).countRecords();
        ((MainActivity) context).readRecords();

    }
}
