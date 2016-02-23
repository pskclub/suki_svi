package th.co.svi.sukuy.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.adapter.SelectListAdapter;
import th.co.svi.sukuy.manager.InsertDB;
import th.co.svi.sukuy.manager.Member;
import th.co.svi.sukuy.manager.SelectDB;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SelectFragment extends Fragment {
    String id_order, id_formular;
    GridView listView;
    PullRefreshLayout layout;
    CheckBox checkSel;
    CardView cardView;
    SelectListAdapter listAdapter;
    ArrayList<HashMap<String, String>> MyArrList;
    Map<String, String> map;
    Thread thread;
    boolean running = false;
    int checkNumber = 0;

    public SelectFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (MyArrList.get(i).get("checkBefore").toString().equals("false")) {

                    if (MyArrList.get(i).get("check").toString().equals("true")) {
                        MyArrList.get(i).put("check", "false");
                        checkNumber = checkNumber - 1;
                    } else {
                        if (checkNumber < 5) {
                            MyArrList.get(i).put("check", "true");
                            checkNumber = checkNumber + 1;
                        } else {
                            Toast.makeText(getActivity(), "คุณได้เลือกส่วนประกอบครบ 5 อย่างแล้ว", Toast.LENGTH_SHORT).show();
                        }
                    }
                    listAdapter = new SelectListAdapter(getActivity(), MyArrList);
                    listView.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();


                }
            }
        });
        return rootView;
    }

    private void initInstances(View rootView) {
        cardView = (CardView) rootView.findViewById(R.id.card_view);
        listView = (GridView) rootView.findViewById(R.id.listView);
        layout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        checkSel = (CheckBox) rootView.findViewById(R.id.checkSel);
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
            if (Looper.myLooper() == null) { // check already Looper is associated or not.
                Looper.prepare(); // No Looper is defined So define a new one
            }
            running = true;
            thread = new Thread(new Runnable() {
                public void run() {
                    SelectDB Foumular = new SelectDB();
                    ResultSet rs = Foumular.ProductById(getActivity(), id_order);
                    MyArrList = new ArrayList<>();
                    try {
                        if (rs != null && rs.next()) {
                            ResultSet rs2 = Foumular.FormularDetailById(getActivity(), id_formular);
                            rs2.next();
                            do {
                                ResultSet rs3 = Foumular.ChoiceById(getActivity(), rs2.getString("id_choice"));
                                rs3.next();
                                ResultSet rs4 = Foumular.CheckUseChoiceByDd(getActivity(), rs2.getString("id_choice"), id_order);
                                rs4.next();
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
                                if (rs4.getString("NumberUse").equals("0")) {
                                    map.put("check", "false");
                                    map.put("checkBefore", "false");

                                } else {
                                    map.put("check", "false");
                                    map.put("checkBefore", "true");
                                }
                                MyArrList.add((HashMap<String, String>) map);
                            } while (rs2.next());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_finish) {
            if (checkNumber == 0) {
                Toast.makeText(getActivity(), "กรุณาเลือกส่วนประกอบ", Toast.LENGTH_SHORT).show();
            } else {
                InsertDB inserOrder = new InsertDB();
                for (HashMap<String, String> list : MyArrList) {
                    if (list.get("check").equals("true")) {
//                        Toast.makeText(getActivity(), id_order + "|" + Member.getInstance().getIdEmployee() + " | " + list.get("idchoice"), Toast.LENGTH_SHORT).show();
                        inserOrder.Detail(id_order, Member.getInstance().getIdEmployee(), list.get("idchoice"));
                    }

                }
                getActivity().setResult(4);
                getActivity().finish();
                Toast.makeText(getActivity(), "เสร็จสิ้น", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);

    }
}
