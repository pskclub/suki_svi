package th.co.svi.sukuy.manager;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by MIS_Student5 on 17/2/2559.
 */
public class SelectDB {
    ConnectionDB ConnectionClass;

    public SelectDB() {
        ConnectionClass = new ConnectionDB();
    }

    public ResultSet ProductAll(Context context) {
        ResultSet rs = null;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "select * from order_product order by id_order DESC";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            Toast.makeText(context, "DB มีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }

    public ResultSet FormularAll(Context context) {
        ResultSet rs = null;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "SELECT * FROM formular_master";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            Toast.makeText(context, "DB มีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }
}
