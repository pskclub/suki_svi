package th.co.svi.sukuy.manager;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

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
        orderName = convertToUTF8(orderName);
        try {
            Connection con = ConnectionClass.CONN();
            String query = "INSERT INTO order_product (name,id_formular,finishgood,order_date) VALUES ('" + orderName + "'," + formularId + ",'0',GETDATE())";
            Statement stmt = con.createStatement();
            rs = stmt.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            Toast.makeText(context, "DB มีปัญหา" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(Contextor.getInstance().getContext(), "การเชื่อมต่อมีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }
        return rs;
    }

    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("WINDOWS-874"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}
