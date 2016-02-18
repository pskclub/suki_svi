package th.co.svi.sukuy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.co.svi.sukuy.R;
import th.co.svi.sukuy.manager.InsertDB;
import th.co.svi.sukuy.manager.SelectDB;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddJobFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    int formularId;
    Button btnSubmit;
    EditText txtName;
    HashMap<String, String> formularMap;

    public AddJobFragment() {
        super();
    }

    public static AddJobFragment newInstance() {
        AddJobFragment fragment = new AddJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_job, container, false);
        initInstances(rootView);
        showList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDB insertOrder = new InsertDB();
                int result = insertOrder.Product(getActivity(), txtName.getText().toString(), formularId);
                if (result == 1) {
                    Toast.makeText(getActivity(), "เพิ่มเสร็จแล้ว", Toast.LENGTH_SHORT).show();
                    getActivity().setResult(3);
                    getActivity().finish();
                }
            }
        });
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        txtName = (EditText) rootView.findViewById(R.id.txtName);
        btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
    }

    private void showList() {

        SelectDB formulatSel = new SelectDB();
        ResultSet rs = formulatSel.FormularAll(getActivity());
        List<String> formularList = new ArrayList<String>();
        formularMap = new HashMap<String, String>();
        try {
            if (rs != null && rs.next()) {
                do {
                    formularMap.put(rs.getString("name_formular"), rs.getString("id_formular"));
                    formularList.add(rs.getString("name_formular"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, formularList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(dataAdapter);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        formularId = Integer.parseInt(formularMap.get(adapterView.getItemAtPosition(i).toString()));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
