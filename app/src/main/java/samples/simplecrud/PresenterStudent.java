package samples.simplecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import samples.simplecrud.model.DatabaseHelper;
import samples.simplecrud.model.Student;

/**
 * Created by Juan Carlos Crespo on 5/7/2017.
 */

public class PresenterStudent {

    public Context context;
    private DatabaseHelper DBHelper;
    public SQLiteDatabase db;

    public PresenterStudent(Context context){
        this.context = context;
        DBHelper = DatabaseHelper.getInstance(context);
    }

    /**
     * Init the database from a file if the database is not created yet
     * @param ctx the context application
     * */
    public static void init_db(Context ctx){

        String rutaDestino = "/data/data/"+ ctx.getPackageName()+"/databases/"+ctx.getString(R.string.db_file)+"_"+ctx.getString(R.string.db_version);
        File f1 = new File(rutaDestino);
        if( !f1.exists() ) {

            File dir = new File("/data/data/" + ctx.getPackageName() + "/databases");
            dir.mkdir();

            try {

                FileWriter fw = new FileWriter(rutaDestino);
                fw.close();
                File f = new File(rutaDestino);
                if (f.exists()) {
                    CopyDB(ctx.getAssets().open(ctx.getString(R.string.db_file)), new FileOutputStream(rutaDestino));
                }

            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     *  Copy the scheme database from a file
     *  */
    public static void CopyDB(InputStream ip, OutputStream op) throws IOException {
        byte[] buffer = new byte[1024];
        int largo;
        while ((largo = ip.read(buffer)) > 0 ){
            op.write(buffer, 0, largo);
        }
        ip.close();
        op.close();
    }

    public void open() throws SQLException {
        db = DBHelper.getWritableDatabase();
    }

    public void close(){
        DBHelper.close();
    }

    /**
     *  Retrives all the students from the data base
     *  @retun Array with all students in to the data base
     */
    public ArrayList<Student> getAll(){
        ArrayList<Student> students = new ArrayList<>();

        open();
        Cursor bd_obj =  db.query("student", new String[]{"id", "name", "address","birthdate", "phonenumber", "email" }, null, null, null, null, null);

        if( bd_obj.getCount() > 0 ){
            students = new ArrayList<>(bd_obj.getCount());
            while ( bd_obj.moveToNext() ) {
                Student student = new Student();
                student.setId(bd_obj.getInt(0));
                student.setName( bd_obj.getString(1));
                student.setAddress( bd_obj.getString(2));
                student.setBirthDate( bd_obj.getString(3));
                student.setPhoneNumber( bd_obj.getString(4));
                student.setEmail( bd_obj.getString(5));
                students.add(student);
            }
        }

        bd_obj.close();
        close();
        return students;
    }

    /**
     *  Insert or update the student in the data base, if the student instance have an id, then
     *  update operation
     *  @param student, the student for the operation
     *  @return integer with the id in the case of insert or the number of rows afected in update
     *  case, if some error ocurred return 0
     */
    public int setStudent(Student student){
        int res = 0;
        String action;
        ContentValues datos = new ContentValues();
        datos.put("name", student.getName());
        datos.put("address", student.getAddress());
        datos.put("birthdate", student.getBirthDate());
        datos.put("phonenumber", student.getPhoneNumber());
        datos.put("email", student.getEmail());

        open();
        if( student.getId() == 0 ) {
             action = "insert";
             res = (int) db.insert("student", null, datos);
        } else {
             action = "update";
             res = db.update("student", datos, "id="+student.getId(), null);
        }
        close();

        EventBus.getDefault().post(new EventChangeStudent(res, action));

        return res;
    }

    /**
     * Delete a student from the data base
     * @param student, the student to be deleted
     * @return 1 if the delete was successful or 0 if was a problem
     */
    public int deleteStudent(Student student){
        int res = 0;
        open();
        res = db.delete("student", "id="+student.getId(), null);
        close();

        return res;
    }


}
