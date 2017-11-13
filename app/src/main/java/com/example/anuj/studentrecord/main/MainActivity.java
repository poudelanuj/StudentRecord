package com.example.anuj.studentrecord.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import com.example.anuj.studentrecord.R;
import com.example.anuj.studentrecord.Utility.LongClickListener;
import com.example.anuj.studentrecord.Utility.SimpleClickListener;
import com.example.anuj.studentrecord.crud.StudentService;
import com.example.anuj.studentrecord.model.DaoMaster;
import com.example.anuj.studentrecord.model.DaoSession;
import com.example.anuj.studentrecord.model.Student;
import org.greenrobot.greendao.database.Database;

import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.tabHost) TabHost host;
    @BindView(R.id.edit_Firstname) EditText edit_Firstname;
    @BindView(R.id.edit_Lastname) EditText edit_Lastname;
    @BindView(R.id.button_Insert)
    Button insert;
    @BindView(R.id.edit_Age) EditText edit_Age;
    @BindView(R.id.edit_Address) EditText edit_Address;
    @BindView(R.id.searchBar)
    EditText searchBar;
    @BindView(R.id.linearRecords)
    LinearLayout linearLayoutRecords;
    @BindView(R.id.textViewRecordCount)
    TextView textViewRecordCount;



    private DaoSession daoSession;
    //TabHost host;
    TabHost.TabSpec spec;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        host.setup();
        loadTabOne();
        loadTabTwo();

        //initDaoSession();

    }




    public void loadTabOne(){
        spec=host.newTabSpec("Insert");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Insert");
        host.addTab(spec);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student=new Student();
                student.setFirstName(edit_Firstname.getText().toString());
                student.setLastName(edit_Lastname.getText().toString());
                String stu_age=edit_Age.getText().toString();
                student.setAge((int) (!stu_age.isEmpty() ? Integer.parseInt(stu_age) : 0));
                student.setAddress(edit_Address.getText().toString());
                boolean createStatus=new StudentService().create(student);
                if(createStatus){
                    Toast.makeText(MainActivity.this,"Info saved",Toast.LENGTH_LONG).show();
                    readRecords();
                    countRecords();
                }
            }
        });

    }

    public void loadTabTwo(){
        spec=host.newTabSpec("View");
        spec.setContent(R.id.tab2);
        spec.setIndicator("View");
        host.addTab(spec);
        countRecords();
        readRecords();


    }
    @OnTextChanged(value = R.id.searchBar,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable){
        List<Student> students=new StudentService().searchByName(searchBar.getText().toString());
        updateListView(linearLayoutRecords,students);
        int numRecord=students.size();
        textViewRecordCount.setText(numRecord+" records found");


    }


    public void countRecords() {
        int noOfRecords = new StudentService().getCount();
        textViewRecordCount.setText(noOfRecords + " records found.");
    }

    public void readRecords() {

        List<Student> students = new StudentService().read();
        updateListView(linearLayoutRecords, students);

    }


    public void updateListView(LinearLayout linearLayoutRecords,List<Student> students){
        linearLayoutRecords.removeAllViews();
        if(students.size()>0){
            for(Student obj:students){
                long id=obj.getId();
                String fname=obj.getFirstName();
                int age=obj.getAge();
                String textViewContents = fname + "\n" + age;

                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(5, 10, 5, 10);
                textViewStudentItem.setBackgroundColor(GRAY);
                textViewStudentItem.setTextColor(WHITE);
                textViewStudentItem.setTextSize(18);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Long.toString(id));
                textViewStudentItem.setOnClickListener(new SimpleClickListener());
                textViewStudentItem.setOnLongClickListener(new LongClickListener());
                linearLayoutRecords.addView(textViewStudentItem);

                TextView line = new TextView(this);
                line.setBackgroundColor(WHITE);
                line.setTextSize(2);

                linearLayoutRecords.addView(line);

            }
        }else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }
}

