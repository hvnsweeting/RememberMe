package hvn.remember.me;

import hvn.remember.me.RememberMe.NoteColumns;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	DatabaseOpenHelper mDatabaseOpenHelper;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDatabaseOpenHelper = new DatabaseOpenHelper(getApplicationContext());
		setContentView(R.layout.main);
		mDatabaseOpenHelper.openDataBase();
		mDatabaseOpenHelper.closeDataBase();

		Uri mUri = getContentResolver().insert(NoteColumns.CONTENT_URI, null);
		Log.d("hvn.remember.me", "MainActivity:onCreate" + mUri);

		mListView = (ListView) findViewById(R.id.note_list);
		ArrayList<NoteData> notes = new ArrayList<NoteData>();
		notes.add(new NoteData("I'm HVN", 0));
		notes.add(new NoteData(
				"We cannot solve our problems with the same thinking we used when we created them - Albert Einstein",
				1));
		notes.add(new NoteData(
				"The purpose of our lives is to be happy. - Dalai Lama", 2));

		NoteListAdapter adapter = new NoteListAdapter(MainActivity.this,
				R.layout.note_list_item, notes);
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
