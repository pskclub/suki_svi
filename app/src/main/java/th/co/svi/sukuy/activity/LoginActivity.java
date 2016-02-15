package th.co.svi.sukuy.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.manager.ConnectionDB;

public class LoginActivity extends AppCompatActivity {
    ConnectionDB connectionClass;
    TextView txtUsername;
    TextView txtPassword;
    Button btnLogin;
    String username, userid;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initinstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        txtPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @TargetApi(value = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    txtPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    private void login() {
        if (txtUsername.getText().toString().equals("") || txtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please, input Username and Password.", Toast.LENGTH_SHORT).show();
        } else {
            connectionClass = new ConnectionDB();
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {

                    Toast.makeText(this, "ไม่สามารถเชื่อมต่อ Server ได้",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String query = "select * from employee where name_employee='" + txtUsername.getText().toString().trim() + "' and password_employee='" + txtPassword.getText().toString().trim() + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null && rs.next()) {
                        userid = rs.getString("id_employee");
                        username = rs.getString("name_employee");
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);

                        sp = getSharedPreferences("member", MODE_PRIVATE);
                        sp.edit().putString("userid", userid);
                        sp.edit().putString("username", username);
                        sp.edit().commit();

                        startActivity(i);
                        finish();
                        connectionClass.CONN().close();
                        rs.close();
                    } else {
                        Toast.makeText(this, "Username หรือ Password ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (SQLException e) {
                Toast.makeText(this, "ไม่สามารถเชื่อมต่อ Server ได้",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initinstance() {
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder =
                new AlertDialog.Builder(LoginActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("ยืนยัน");
        builder.setMessage("คุณต้องการอกจาก Suki ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //คลิกใช่ ออกจากโปรแกรม
                finish();
                LoginActivity.super.onBackPressed();
            }
        });//second parameter used for onclicklistener
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
