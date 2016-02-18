package th.co.svi.sukuy.manager;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by MIS_Student5 on 17/2/2559.
 */
public class InsertDB {
    ConnectionDB ConnectionClass;

    public InsertDB() {
        ConnectionClass = new ConnectionDB();
    }

    public int Product(Context context, String orderName, int formularId) {
        int rs = 0;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "INSERT INTO order_product (name,id_formular,finishgood,order_date) VALUES ('" + orderName + "'," + formularId + ",'0',GETDATE())";
            Statement stmt = con.createStatement();
            rs = stmt.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            Toast.makeText(context, "DB มีปัญหา" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        return rs;
    }
}
