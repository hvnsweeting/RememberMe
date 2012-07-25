package hvn.remember.me;

import hvn.remember.me.RememberMe.NoteColumns;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "RememberMe.db";

	public static final String NOTES_TABLE_NAME = "notes";
//	private static final String LISTS_TABLE_NAME = "lists";
//	private static final String LIST_THINGS_TABLE_NAME = "list_things";

	private static final String THINGS_TABLE_CREATE = "CREATE TABLE "
			+ NOTES_TABLE_NAME + " ("
			+ NoteColumns._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ NoteColumns.PRIORITY +" INTEGER, "
			+ NoteColumns.CONTENT  + " TEXT);";

//	private static final String LISTS_TABLE_CREATE = "" + "CREATE TABLE "
//			+ LISTS_TABLE_NAME + " ("
//			+ "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT,"
//			+ " type TEXT" + " );";
//
//	private static final String LIST_THINGS_TABLE_CREATE = "" + "CREATE TABLE "
//			+ LIST_THINGS_TABLE_NAME + " ( " + "listId INTEGER, "
//			+ "thingId INTEGER " + ");";

	private SQLiteDatabase myDatabase;

	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// try {
		// openDataBase();
		// } catch (SQLException e) {
		// }
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(THINGS_TABLE_CREATE);
//		db.execSQL(LISTS_TABLE_CREATE);
//		db.execSQL(LIST_THINGS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS users");
		onCreate(db);
	}

	public void openDataBase() throws SQLException {
		myDatabase = getWritableDatabase();
	}

	public void closeDataBase() {
		if (myDatabase != null)
			myDatabase.close();
	}

}// DatabaseOpenHelper
