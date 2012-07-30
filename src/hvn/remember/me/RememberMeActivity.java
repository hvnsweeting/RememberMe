package hvn.remember.me;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RememberMeActivity extends Activity {
    private EditText mEditText;
	private Button mButton;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mEditText = (EditText) findViewById(R.id.et_input);
		mButton = (Button) findViewById(R.id.btn_add);
		Log.d("mbtn", mButton.toString());
		
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getBaseContext(), "added", Toast.LENGTH_SHORT).show();
				Log.d("hehe", "in button click");
			}
		});
    }
}