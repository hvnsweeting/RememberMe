package hvn.remember.me;

import hvn.remember.me.RememberMe.NoteColumns;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Provides access to a database of notes. Each note has a priority, the note
 * content
 */

public class RememberMeProvider extends ContentProvider {
	private static final String TAG = "RememberMeProvider";
	private static UriMatcher sUriMatcher;
	private static HashMap<String, String> sNotesProjectionMap;
	private DatabaseOpenHelper mDatabaseOpenHelper;
	private static final int NOTES = 1;
	private static final int NOTE_ID = 2;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {

		// Validate the requested uri
		if (sUriMatcher.match(uri) != NOTES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		if (values.containsKey(NoteColumns.PRIORITY) == false) {
			values.put(NoteColumns.PRIORITY, 1);
		}

		if (values.containsKey(NoteColumns.CONTENT) == false) {
			values.put(NoteColumns.CONTENT, "");
		}

		SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
		long rowId = db.insert(DatabaseOpenHelper.NOTES_TABLE_NAME,
				NoteColumns.CONTENT, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(NoteColumns.CONTENT_URI,
					rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		mDatabaseOpenHelper = new DatabaseOpenHelper(getContext());
		return true;
	}// onCreate

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DatabaseOpenHelper.NOTES_TABLE_NAME);

		switch (sUriMatcher.match(uri)) {
		case NOTES:
			qb.setProjectionMap(sNotesProjectionMap);
			break;

		case NOTE_ID:
			qb.setProjectionMap(sNotesProjectionMap);
			qb.appendWhere(NoteColumns._ID + "=" + uri.getPathSegments().get(1));
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// If no sort order is specified use the default
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = NoteColumns.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		// Get the database and run the query
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, orderBy);

		// Tell the cursor what uri to watch, so it knows when its source data
		// changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		return 0;
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(RememberMe.AUTHORITY, "notes", NOTES);
		sUriMatcher.addURI(RememberMe.AUTHORITY, "notes/#", NOTE_ID);

		sNotesProjectionMap = new HashMap<String, String>();
		sNotesProjectionMap.put(NoteColumns._ID, NoteColumns._ID);
		sNotesProjectionMap.put(NoteColumns.PRIORITY, NoteColumns.PRIORITY);
		sNotesProjectionMap.put(NoteColumns.CONTENT, NoteColumns.CONTENT);
	}
}// RememberMeProvider
