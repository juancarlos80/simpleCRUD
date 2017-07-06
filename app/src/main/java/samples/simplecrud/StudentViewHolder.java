package samples.simplecrud;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import samples.simplecrud.R;

/**
 * Created by Juan Carlos on 04/07/2017.
 */
public class StudentViewHolder extends RecyclerView.ViewHolder {
    protected TextView name;
    protected Button but_edit;
    protected Button but_delete;

    public StudentViewHolder(View v){
        super(v);
        name = (TextView) v.findViewById(R.id.tex_name);
        but_edit = (Button) v.findViewById(R.id.but_edit);
        but_delete = (Button) v.findViewById(R.id.but_delete);
    }
}
