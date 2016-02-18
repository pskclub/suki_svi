package th.co.svi.sukuy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import th.co.svi.sukuy.view.SelectChoiceListItem;

/**
 * Created by MIS_Student5 on 11/2/2559.
 */
public class SelectListAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> Data = new ArrayList<>();

    public SelectListAdapter(Context context, ArrayList<HashMap<String, String>> Data) {
        this.context = context;
        this.Data = Data;
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SelectChoiceListItem item;
        if (view != null) {
            item = (SelectChoiceListItem) view;
        } else {
            item = new SelectChoiceListItem(viewGroup.getContext());

        }
        item.setText(context,
                Data.get(i).get("name").toString(),
                Data.get(i).get("url").toString());
        return item;
    }
}
