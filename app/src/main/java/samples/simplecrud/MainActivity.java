package samples.simplecrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import samples.simplecrud.model.Student;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec_students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PresenterStudent.init_db(this);

        rec_students = (RecyclerView) findViewById(R.id.rec_students);
        fill_students();
        EventBus.getDefault().register(this);
    }

    public void fill_students(){
        PresenterStudent presenterStudent = new PresenterStudent(this);
        ArrayList<Student> estudiantes = presenterStudent.getAll();

        StudentAdapter ada_students = new StudentAdapter(this, estudiantes);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_students.setLayoutManager(mLayoutManager);
        rec_students.setAdapter(ada_students);
    }

    public void new_student(View btn){
        Intent intento = new Intent( this, StudentActivity.class);
        startActivity(intento);
    }

    @Subscribe
    public void onEvent(EventChangeStudent event){
        fill_students();
    }

}
