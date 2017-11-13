package com.example.anuj.studentrecord.Utility;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.anuj.studentrecord.R;
import com.example.anuj.studentrecord.crud.StudentService;
import com.example.anuj.studentrecord.model.Student;

public class SimpleClickListener implements View.OnClickListener {

    Context context;
    String id;

    @Nullable
    @BindView(R.id.text_Firstname)
    TextView firstName;
    @Nullable @BindView(R.id.text_Lastname)
    TextView lastName;
    @Nullable @BindView(R.id.text_Age)
    TextView age;
    @Nullable @BindView(R.id.text_Address)
    TextView address;
    @Nullable @BindView(R.id.ok)
    Button ok;

    @Override
    public void onClick(View view) {
        context=view.getContext();
        id=view.getTag().toString();

        final Student studentobj=new StudentService().readStudentData(Long.parseLong(id));
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.layout_view_result);
        ButterKnife.bind(this,dialog);
        firstName.setText("F.Name:"+studentobj.getFirstName());
        lastName.setText("L.Name:"+studentobj.getLastName());
        age.setText("Age:"+studentobj.getAge());
        address.setText("Address:"+studentobj.getAddress());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
}
