package th.co.svi.sukuy.manager;

import android.content.Context;
import android.util.Log;
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
            Toast.makeText(context, "" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(context, "การเชื่อมต่อมีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }

    public ResultSet ProductById(Context context, String i) {
        ResultSet rs = null;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "select * from order_product where id_order='" + i + "'";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            Toast.makeText(context, "" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(context, "การเชื่อมต่อมีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }

    public ResultSet FormularDetailById(Context context, String i) {
        ResultSet rs = null;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "select * from formular_detail where id_formular='" + i + "'";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            Toast.makeText(context, "" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(context, "การเชื่อมต่อมีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }

    public ResultSet ChoiceById(Context context, String i) {
        ResultSet rs = null;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "select * from choice where id_choice='" + i + "'";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            Toast.makeText(context, "" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(context, "การเชื่อมต่อมีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }

    public ResultSet CountChoiceAsProduct(Context context, String i) {
        ResultSet rs = null;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "select COUNT(*) AS NumberChoice from formular_detail where id_formular='" + i + "'";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            Toast.makeText(context, "" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(context, "การเชื่อมต่อมีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }

    public ResultSet CountUseAsProduct(Context context, String i) {
        ResultSet rs = null;
        try {
            Connection con = ConnectionClass.CONN();
            String query = "select COUNT(*) AS NumberUse from detail where id_order='" + i + "'";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            Toast.makeText(context, "" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(context, "การเชื่อมต่อมีปัญหา",
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
            Toast.makeText(context, "" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("ERRO", e.getMessage());
            Toast.makeText(context, "การเชื่อมต่อมีปัญหา",
                    Toast.LENGTH_SHORT).show();
        }

        return rs;
    }
}
