package samples.simplecrud.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import samples.simplecrud.R;


public class DatabaseHelper extends SQLiteOpenHelper {

	private static DatabaseHelper sInstance;

	public static synchronized DatabaseHelper getInstance(Context context){
		if( sInstance == null ) {
			sInstance = new DatabaseHelper(context.getApplicationContext());
		}
		return sInstance;
	}

	public DatabaseHelper(Context context) {
		super(context, context.getString(R.string.db_file)+"_"+context.getString(R.string.db_version), null, Integer
				.parseInt(context.getString(R.string.db_version)));
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
