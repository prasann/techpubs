package com.techpubs;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TabHost;
import android.widget.Toast;

public class CatalogActivity extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    AutoCompleteTextView textView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost mTabHost = getTabHost();

        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Aircraft").setContent(R.id.aircraftTab));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Engine Model").setContent(R.id.engineModelTab));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, AIRCRAFT);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.aircraftText);
        textView.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ENGINE);
        textView1 = (AutoCompleteTextView)
                findViewById(R.id.engineModelText);
        textView1.setOnItemClickListener(new itemSelect());

        textView1.setAdapter(adapter1);

        mTabHost.setCurrentTab(0);
    }

    public class itemSelect implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(CatalogActivity.this, "You have selected "+textView1.getText().toString(), 10).show();
        }
    }

    private static final String[] AIRCRAFT = new String[]{
            "Bombardier", "JetAirways", "KingFisher", "AleniumAirways", "Sogoto"
    };

    private static final String[] ENGINE = new String[]{
            "PW100", "PW100-A", "PW100-B", "PW110-A", "PW120-C"
    };
}
