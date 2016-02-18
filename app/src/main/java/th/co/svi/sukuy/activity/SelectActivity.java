package th.co.svi.sukuy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.fragment.SelectFragment;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, SelectFragment.newInstance())
                    .commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(SelectActivity.this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle("ยืนยัน");
            builder.setMessage("คุณต้องการออกจากระบบ ?");
            builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //คลิกใช่ ออกจากโปรแกรม
                    Intent i = new Intent(SelectActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });//second parameter used for onclicklistener
            builder.setNegativeButton("ไม่ใช่", null);
            builder.show();
        }
        if (item.getItemId() == android.R.id.home) {
            setResult(3);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
