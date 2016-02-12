package th.co.svi.sukuy.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;


import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.adapter.JobMainListAdapter;
import th.co.svi.sukuy.manager.ConnectionDB;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {
    GridView listView;
    JobMainListAdapter listAdapter;
    PullRefreshLayout layout;
    ConnectionDB connectionClass;
    ArrayList<HashMap<String, String>> MyArrList;
    Map<String, String> map;


    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void showList() {
        if (isConnection()) {
//        if (isConnectedToServer("12.1.2.18", 1000)) {
            connectionClass = new ConnectionDB();
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    Toast.makeText(MainFragment.this.getActivity(), "Err", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "select * from pic";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    MyArrList = new ArrayList<HashMap<String, String>>();
                    if (rs != null && rs.next()) {
                        do {
                            map = new HashMap<String, String>();
                            map.put("title", rs.getString("name_pic"));
                            map.put("link", rs.getString("link"));
                            MyArrList.add((HashMap<String, String>) map);
                        } while (rs.next());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            listAdapter = new JobMainListAdapter(getContext(), MyArrList);
            listView.setAdapter(listAdapter);
    /*    } else {
            Toast.makeText(getContext(), "ไม่สามารถเชื่อมต่อ Server ได้",
                    Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public boolean isConnection() {
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi != null && mWifi.isConnected()) {
            Toast.makeText(getContext(), "เชื่อมต่อ Server ได้",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(getContext(), "ไม่สามารถเชื่อมต่อ Server ได้",
                Toast.LENGTH_SHORT).show();
        return false;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        showList();
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showList();
                layout.setRefreshing(false);
            }
        });


        return rootView;


    }


    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        layout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        listView = (GridView) rootView.findViewById(R.id.listView);


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
