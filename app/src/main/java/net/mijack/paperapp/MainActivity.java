package net.mijack.paperapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static net.mijack.paperapp.Constant.BASE_URL;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private static final int REQUEST_CODE_SCAN = 1;
    TextView textView;
    Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SCAN) {
            String result = data.getStringExtra("result");
            textView.setText(result);
//            发起网络请求

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            showSetting();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_setting, null);
        editText = (EditText) view.findViewById(R.id.editText);
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData cd = cm.getPrimaryClip();
        String string = cd.getItemAt(0).getText().toString();
        if (!TextUtils.isEmpty(string)) {
            editText.setText(string);
        }
        builder.setTitle(getString(R.string.set_base_url))
                .setView(view)
                .setNegativeButton("取消", this)
                .setPositiveButton("确定", this)
                .create()
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (editText != null) {
                String baseUrl = editText.getText().toString();
                PreferenceUtil.saveBaseUrl(this,baseUrl);
            }
        }
    }
}
