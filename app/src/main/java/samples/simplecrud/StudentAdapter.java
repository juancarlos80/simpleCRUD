package samples.simplecrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import samples.simplecrud.model.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private ArrayList<Student> students;
    private Context context;

    public StudentAdapter(Context context, ArrayList students) {
        this.students = students;
        this.context = context;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);

        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final StudentViewHolder student, final int position) {
        student.name.setText( students.get(position).getName() );

        student.but_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edit( students.get(position) );
            }
        });

        student.but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(context.getString(R.string.title_delete))
                        .setMessage(context.getString(R.string.tex_confirm, students.get(position).getName()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(students.get(position));
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    /**
     * Open the activity sending the student for edit
     * @param student
     */
    public void edit(Student student){
        Intent intento = new Intent( context, StudentActivity.class);
        intento.putExtra("student", student);
        context.startActivity(intento);
    }

    /**
     * Delete the student from the data base and notify the the event if was correct in the
     * otherwise show a message
     * @param student
     */
    public void delete(Student student){
        PresenterStudent presenterStudent = new PresenterStudent(context);
        if( presenterStudent.deleteStudent( student) <= 0 ){
            Toast.makeText(context, context.getString(R.string.tex_error_operation), Toast.LENGTH_LONG).show();
        } else {
            EventBus.getDefault().post(new EventChangeStudent(1, "delete"));
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
