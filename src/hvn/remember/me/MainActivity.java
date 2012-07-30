package hvn.remember.me;

import hvn.remember.me.RememberMe.NoteColumns;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	DatabaseOpenHelper mDatabaseOpenHelper;
	private ListView mListView;
	private EditText mEditText;
	private Button mButton;
	private DatabaseHandler mDBHandler;
	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDatabaseOpenHelper = new DatabaseOpenHelper(getApplicationContext());
		mDBHandler = new DatabaseHandler(getApplicationContext());

		setContentView(R.layout.main);

		mEditText = (EditText) findViewById(R.id.et_input);
		mButton = (Button) findViewById(R.id.btn_add);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String enteredText = mEditText.getText().toString();
				if (enteredText == null || enteredText.equalsIgnoreCase("")) {
					Toast.makeText(getBaseContext(), R.string.enter_some_text,
							Toast.LENGTH_SHORT).show();
				} else {
					mDBHandler.addNote(new NoteData(0, enteredText));
					Toast.makeText(getBaseContext(), R.string.added,
							Toast.LENGTH_SHORT).show();
					// TODO reload, clear et
					mEditText.setText("");

					Cursor c = mDBHandler.getAllNotes();
					adapter.changeCursor(c);
					// adapter.notifyDataSetChanged();

				}
			}
		});

		mListView = (ListView) findViewById(R.id.note_list);

		Cursor c = mDBHandler.getAllNotes();
		startManagingCursor(c);
		String[] columns = new String[] { NoteColumns.CONTENT };

		int to[] = new int[] { android.R.id.text1 };
		adapter = new SimpleCursorAdapter(getBaseContext(),
				android.R.layout.simple_list_item_1, c, columns, to);
		// NoteListAdapter adapter = new NoteListAdapter(MainActivity.this,
		// R.layout.note_list_item, notes);
		mListView.setAdapter(adapter);
	}

	class NoteListAdapter extends ArrayAdapter<NoteData> {
		private List<NoteData> lstData;
		private int resource;
		LayoutInflater mInflater = null;

		public NoteListAdapter(Activity context, int textViewResourceId,
				ArrayList<NoteData> objects) {
			super(context, textViewResourceId, objects);
			this.resource = textViewResourceId;
			this.lstData = objects;
			mInflater = context.getLayoutInflater();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			Wrapper w;
			if (v == null) {
				v = mInflater.inflate(resource, null);
				w = new Wrapper(v);
				v.setTag(w);
			} else {
				w = (Wrapper) v.getTag();
			}

			NoteData n = lstData.get(position);
			w.content.setText(n.content);

			return v;
		}

		private class Wrapper {
			public TextView content;

			public Wrapper(View parent) {

				if (content == null) {
					content = (TextView) parent.findViewById(R.id.nli_content);
				}
			}// wraper
		}// wraper class

	}

}
