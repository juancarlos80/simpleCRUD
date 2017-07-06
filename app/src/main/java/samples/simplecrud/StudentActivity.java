package samples.simplecrud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import samples.simplecrud.model.Student;


public class StudentActivity extends AppCompatActivity {

    Student student;

    TextView title;
    EditText edi_name;
    EditText edi_address;
    EditText edi_birthdate;
    EditText edi_phone;
    EditText edi_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        title = (TextView) findViewById(R.id.tex_title_main);
        edi_name = (EditText) findViewById(R.id.edi_name);
        edi_address = (EditText) findViewById(R.id.edi_address);
        edi_birthdate = (EditText) findViewById(R.id.edi_birthdate);
        edi_phone = (EditText) findViewById(R.id.edi_phonenumber);
        edi_email = (EditText) findViewById(R.id.edi_email);

        if( getIntent().getSerializableExtra("student") != null ){
            title.setText( getString(R.string.title_edit));
            student = (Student) getIntent().getSerializableExtra("student");
            edi_name.setText( student.getName() );
            edi_address.setText( student.getAddress() );
            edi_birthdate.setText( student.getBirthDate() );
            edi_phone.setText( student.getPhoneNumber() );
            edi_email.setText( student.getEmail() );
        }
    }

    public void save(View btn){
        if( student == null ){
            student = new Student();
        }

        student.setName( edi_name.getText().toString() );
        student.setAddress( edi_address.getText().toString() );
        student.setBirthDate( edi_birthdate.getText().toString() );
        student.setPhoneNumber( edi_phone.getText().toString() );
        student.setEmail( edi_email.getText().toString() );

        PresenterStudent presenterStudent = new PresenterStudent(this);
        if( presenterStudent.setStudent(student) > 0 ){
            ir_main(null);
        } else {
            Toast.makeText(this, getString(R.string.tex_error_operation), Toast.LENGTH_LONG).show();
        }
    }

    public void ir_main(View btn){
        Intent intent = new Intent( this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

}
