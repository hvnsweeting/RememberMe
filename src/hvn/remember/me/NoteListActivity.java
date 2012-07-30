package hvn.remember.me;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NoteListActivity extends Activity {
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list);

		mListView = (ListView) findViewById(R.id.note_list);
		
		ArrayList<NoteData> notes = new ArrayList<NoteData>();
		//notes.add(new NoteData(
		// "We cannot solve our problems with the same thinking we used when we created them - Albert Einstein",
		// 1));

		NoteListAdapter adapter = new NoteListAdapter(NoteListActivity.this,
				R.layout.note_list_item, notes);
		mListView.setAdapter(adapter);
	}
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
