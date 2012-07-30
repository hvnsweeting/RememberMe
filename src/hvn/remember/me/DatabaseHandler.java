package hvn.remember.me;

import hvn.remember.me.RememberMe.NoteColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHandler {
	DatabaseOpenHelper dbHelper;

	// = new DatabaseOpenHelper(context)
	public DatabaseHandler(Context context) {
		dbHelper = new DatabaseOpenHelper(context);
	}

	public void addNote(NoteData noteData) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(NoteColumns.CONTENT, noteData.getContent());
		cv.put(NoteColumns.PRIORITY, noteData.getOrder());
		db.insert(DatabaseOpenHelper.NOTES_TABLE_NAME, null, cv);
		db.close();
	}

	public NoteData getNote(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor cursor = db.query(DatabaseOpenHelper.NOTES_TABLE_NAME,
				new String[] { NoteColumns._ID, NoteColumns.PRIORITY,
						NoteColumns.CONTENT }, NoteColumns._ID + "=" + id,
				null, null, null, null, null);
		if (cursor.moveToFirst()) {

			NoteData note = new NoteData(cursor.getInt(1), cursor.getString(2));
			cursor.close();
			return note;
		} else {
			return new NoteData(1, "Null");
		}
	}

	public Cursor getAllNotes() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String query = "SELECT * FROM " + DatabaseOpenHelper.NOTES_TABLE_NAME
				+ " ;";
		Cursor c = db.rawQuery(query, null);

		if (c.moveToFirst()) {
			do {
				NoteData data = new NoteData();
				data.setId(Integer.parseInt(c.getString(0)));
				data.setOrder(Integer.parseInt(c.getString(1)));
				data.setContent(c.getString(2));
				Log.d("data", "" + data);
			} while (c.moveToNext());
		}
		return c;
	}

	public int getNotesCount() {

		String countQuery = "SELECT  * FROM "
				+ DatabaseOpenHelper.NOTES_TABLE_NAME;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int c = cursor.getCount();
		cursor.close();

		return c;
	}

	public int updateNote(NoteData noteData) {
		return 0;
	}

	public void deleteNote(NoteData noteData) {
	}
}
