package th.co.svi.sukuy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.activity.AddJobActivity;
import th.co.svi.sukuy.activity.SelectActivity;
import th.co.svi.sukuy.adapter.JobMainListAdapter;
import th.co.svi.sukuy.adapter.SelectListAdapter;
import th.co.svi.sukuy.manager.SelectDB;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {
    GridView listView;
    TextView txtErr;
    JobMainListAdapter listAdapter;
    PullRefreshLayout layout;
    ArrayList<HashMap<String, String>> MyArrList;
    Map<String, String> map;
    FloatingActionButton fbtAdd;
    CoordinatorLayout rootLayout;
    Thread thread;
    boolean running = false;


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
        if (!running) {
            thread = new Thread(new Runnable() {
                public void run() {
                    if (Looper.myLooper() == null) { // check already Looper is associated or not.
                        Looper.prepare(); // No Looper is defined So define a new one
                    }
                    running = true;
                    SelectDB selOrder = new SelectDB();
                    ResultSet rs = selOrder.ProductAll(getActivity());
                    txtErr.setVisibility(View.GONE);
                    MyArrList = new ArrayList<>();
                    try {
                        if (rs != null && rs.next()) {
                            do {
                                SelectDB CountChoice = new SelectDB();
                                ResultSet rs2 = CountChoice.CountChoiceAsProduct(getActivity(), rs.getString("id_formular"));
                                rs2.next();
                                SelectDB CountUse = new SelectDB();
                                ResultSet rs3 = CountUse.CountUseAsProduct(getActivity(), rs.getString("id_order"));
                                rs3.next();
                                map = new HashMap<>();
                                map.put("id", rs.getString("id_order"));
                                map.put("date", rs.getString("order_date"));
                                map.put("id_formular", rs.getString("id_formular"));
                                map.put("finish", rs.getString("finishgood"));
                                map.put("name", convertFromUTF8(rs.getString("name")));
                                map.put("choice", rs2.getString("NumberChoice"));
                                map.put("use", rs3.getString("NumberUse"));
                                if (!(rs2.getString("NumberChoice").equals(rs3.getString("NumberUse")))) {
                                    MyArrList.add((HashMap<String, String>) map);
                                }

                            } while (rs.next());
                        }
                        thread.sleep(200);

                    } catch (SQLException e) {
                        Toast.makeText(getActivity(), "" + e.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RuntimeException e) {
                        Toast.makeText(getActivity(), "" + e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                    if (Looper.myLooper() != null) { // check already Looper is associated or not.
                        Looper.myLooper().quit();
                    }

                    handler.sendEmptyMessage(1);
                    if (Looper.myLooper() != null) { // check already Looper is associated or not.
                        Looper.loop();
                    }

                }
            });
            thread.start();
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            layout.setRefreshing(false);
            listAdapter = new JobMainListAdapter(getContext(), MyArrList);
            listView.setAdapter(listAdapter);
            running = false;

        }
    };

    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "WINDOWS-874");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        showList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent send = new Intent(getActivity(), SelectActivity.class);
                send.putExtra("id_order", MyArrList.get(i).get("id"));
                send.putExtra("id_formular", MyArrList.get(i).get("id_formular"));
                startActivityForResult(send, 4);
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
        if (requestCode == 3 || requestCode == 4)
            showList();
    }
}
