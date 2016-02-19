package th.co.svi.sukuy.manager;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by MIS_Student5 on 10/2/2559.
 */
public class ConnectionDB {
    String ip = "12.1.2.18";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "project-test";
    String un = "sa";
    String password = "svi2014DB";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);

        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
            Toast.makeText(Contextor.getInstance().getContext(), "ไม่สามารถเชื่อมต่อ Server ได้",
                    Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(Contextor.getInstance().getContext(), "ไม่สามารถเชื่อมต่อ Server ได้",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(Contextor.getInstance().getContext(), "ไม่สามารถเชื่อมต่อ Server ได้",
                    Toast.LENGTH_SHORT).show();
        }
        if (conn == null) {
            Toast.makeText(Contextor.getInstance().getContext(), "ไม่สามารถเชื่อมต่อ Server ได้",
                    Toast.LENGTH_SHORT).show();
        }
        return conn;
    }

}

