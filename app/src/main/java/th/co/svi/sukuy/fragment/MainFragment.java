package th.co.svi.sukuy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.activity.AddJobActivity;
import th.co.svi.sukuy.activity.MainActivity;
import th.co.svi.sukuy.adapter.JobMainListAdapter;
import th.co.svi.sukuy.manager.ConnectionDB;
import th.co.svi.sukuy.view.JobMainListItem;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {
    GridView listView;
    TextView txtErr;
    JobMainListAdapter listAdapter;
    PullRefreshLayout layout;
    ConnectionDB connectionClass;
    ArrayList<HashMap<String, String>> MyArrList;
    Map<String, String> map;
    FloatingActionButton fbtAdd;
    CoordinatorLayout rootLayout;


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
        connectionClass = new ConnectionDB();
        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                txtErr.setText("ไม่สามารถเชื่อมต่อ Server ได้");
                txtErr.setVisibility(View.VISIBLE);
//                Toast.makeText(getContext(), "ไม่สามารถเชื่อมต่อ Server ได้",
//                        Toast.LENGTH_SHORT).show();
            } else {
                txtErr.setVisibility(View.GONE);
                String query = "select * from order_product";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                MyArrList = new ArrayList<HashMap<String, String>>();
                if (rs != null && rs.next()) {
                    do {
                        map = new HashMap<String, String>();
                        map.put("id", rs.getString("id_order"));
                        map.put("date", rs.getString("order_date"));
                        map.put("finish", rs.getString("finishgood"));
                        map.put("name", rs.getString("name"));
                        map.put("use", "");
                        MyArrList.add((HashMap<String, String>) map);
                    } while (rs.next());
                    rs.close();
                }
                listAdapter = new JobMainListAdapter(getContext(), MyArrList);
                listView.setAdapter(listAdapter);
            }
        } catch (SQLException e) {
            txtErr.setText("ไม่สามารถเชื่อมต่อ Server ได้");
            txtErr.setVisibility(View.VISIBLE);
        }


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        showList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              /*  JobMainListItem item;
                if (view != null) {
                    item = (JobMainListItem) view;
                } else {
                    item = new JobMainListItem(getContext());

                }
                item.setText(getContext(),"ภาสกร (หลง)","https://fbcdn-sphotos-b-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/10153907_875848735773154_5418882709047636551_n.jpg?oh=88a0c558b957e9e70ff0d9c0af5680ed&oe=572DB7DF&__gda__=1463091945_f68b9e654ed9119519eedc3a8565fc54");
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle("แจ้งเตือน");
                builder.setMessage(MyArrList.get(i).get("title").toString()+" ถูกเปลี่ยนเป็น ภาสกร (หลง)");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });//second parameter used for onclicklistener
                builder.show();*/
            }
        });
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showList();
                layout.setRefreshing(false);
            }
        });
        fbtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddJobActivity.class);
                startActivityForResult(i, 3);
            }
        });

        return rootView;


    }


    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        txtErr = (TextView) rootView.findViewById(R.id.textErr);
        rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.rootLayout);
        fbtAdd = (FloatingActionButton) rootView.findViewById(R.id.fabAdd);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3)
            showList();
    }
}
