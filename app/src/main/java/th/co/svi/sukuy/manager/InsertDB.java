package th.co.svi.sukuy.manager;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by MIS_Student5 on 17/2/2559.
 */
public class InsertDB {
    ConnectionDB ConnectionClass;

    public InsertDB() {
        ConnectionClass = new ConnectionDB();
    }

    public int Product(Context context,String orderName,String formularName) {
        int rs = 0;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "INSERT INTO pic (id_pic,name_pic,link) VALUES (9021,'5555','555555555')";
            Statement stmt = con.createStatement();
            rs = stmt.executeUpdate(query);
        } catch (SQLException e) {
            Toast.makeText(context, "DB มีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }
         return rs;
    }
}
