package th.co.svi.sukuy.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.adapter.JobMainListAdapter;
import th.co.svi.sukuy.adapter.SelectListAdapter;
import th.co.svi.sukuy.manager.SelectDB;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SelectFragment extends Fragment {
    GridView listView;
    SelectListAdapter listAdapter;
    ArrayList<HashMap<String, String>> MyArrList;
    Map<String, String> map;
    public SelectFragment() {
        super();
    }

    public static SelectFragment newInstance() {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);
        initInstances(rootView);
        showList();
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here

    }
    private void showList() {
        SelectDB selOrder = new SelectDB();
        ResultSet rs = selOrder.ProductAll(getActivity());
        MyArrList = new ArrayList<HashMap<String, String>>();
        try {
            if (rs != null && rs.next()) {
                do {
                    SelectDB CountChoice = new SelectDB();
                    ResultSet rs2 = CountChoice.CountChoiceAsProduct(getActivity(), rs.getString("id_formular"));
                    rs2.next();
                    SelectDB CountUse = new SelectDB();
                    ResultSet rs3 = CountChoice.CountUseAsProduct(getActivity(), rs.getString("id_order"));
                    rs3.next();
                    map = new HashMap<String, String>();
                    map.put("id", rs.getString("id_order"));
                    map.put("date", rs.getString("order_date"));
                    map.put("finish", rs.getString("finishgood"));
                    map.put("name", convertFromUTF8(rs.getString("name")));
                    map.put("choice", rs2.getString("NumberChoice"));
                    map.put("use", rs3.getString("NumberUse"));
                    MyArrList.add((HashMap<String, String>) map);
                } while (rs.next());
            }

        } catch (SQLException e) {
            Toast.makeText(getActivity(), "5555" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
        listAdapter = new SelectListAdapter(getContext(), MyArrList);
        listView.setAdapter(listAdapter);
    }
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "WINDOWS-874");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
