package th.co.svi.sukuy.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.io.IOException;
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
    String id_order, id_formular;
    GridView listView;
    PullRefreshLayout layout;
    SelectListAdapter listAdapter;
    ArrayList<HashMap<String, String>> MyArrList;
    Map<String, String> map;
    Thread thread;
    boolean running = false;

    public SelectFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_order = getArguments().getString("id_order");
        id_formular = getArguments().getString("id_formular");
    }

    public static SelectFragment newInstance(String id_order, String id_formular) {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        args.putString("id_order", id_order);
        args.putString("id_formular", id_formular);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);
        initInstances(rootView);
        showList();
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showList();
            }
        });
        return rootView;
    }

    private void initInstances(View rootView) {
        listView = (GridView) rootView.findViewById(R.id.listView);
        layout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        // init instance with rootView.findViewById here

    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            layout.setRefreshing(false);
            listAdapter = new SelectListAdapter(getActivity(), MyArrList);
            listView.setAdapter(listAdapter);
            running = false;
        }
    };

    private void showList() {
        if (!running) {
            if(Looper.myLooper() == null) { // check already Looper is associated or not.
                Looper.prepare(); // No Looper is defined So define a new one
            }
            running = true;
            thread = new Thread(new Runnable() {
                public void run() {
                    SelectDB selOrder = new SelectDB();
                    ResultSet rs = selOrder.ProductById(getActivity(), id_order);
                    MyArrList = new ArrayList<>();
                    try {
                        if (rs != null && rs.next()) {

                            SelectDB Foumular = new SelectDB();
                            ResultSet rs2 = Foumular.FormularDetailById(getActivity(), id_formular);
                            rs2.next();
                            do {
                                SelectDB Choice = new SelectDB();
                                ResultSet rs3 = Choice.ChoiceById(getActivity(), rs2.getString("id_choice"));
                                rs3.next();
                                map = new HashMap<>();
                                map.put("id", rs.getString("id_order"));
                                map.put("formular", rs.getString("id_formular"));
                                map.put("date", rs.getString("order_date"));
                                map.put("finish", rs.getString("finishgood"));
                                map.put("name", convertFromUTF8(rs.getString("name")));
                                map.put("idchoice", rs2.getString("id_choice"));
                                map.put("namechoice", rs3.getString("name_choice"));
                                map.put("stock", rs3.getString("stock_choice"));
                                map.put("push", rs3.getString("push_choice"));
                                map.put("pic", rs3.getString("pic_choice"));
                                MyArrList.add((HashMap<String, String>) map);
                            } while (rs2.next());
                        }
                        thread.sleep(200);

                    } catch (SQLException e) {
                        Toast.makeText(getActivity(), "" + e.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }catch (RuntimeException e){
                        Toast.makeText(getActivity(), "" + e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                    if(Looper.myLooper() != null) { // check already Looper is associated or not.
                        Looper.myLooper().quit();
                    }

                    handler.sendEmptyMessage(1);
                    if(Looper.myLooper() != null) { // check already Looper is associated or not.
                        Looper.loop();
                    }

                }
            });
            thread.start();

        }
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
