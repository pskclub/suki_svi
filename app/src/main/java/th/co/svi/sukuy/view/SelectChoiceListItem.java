package th.co.svi.sukuy.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.squareup.picasso.Picasso;

import th.co.svi.sukuy.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SelectChoiceListItem extends BaseCustomViewGroup {
    private TextView txtName,txtSucess;
    private ImageView img;
    private CheckBox checkSel;

    public SelectChoiceListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public SelectChoiceListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public SelectChoiceListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public SelectChoiceListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_select, this);
    }

    private void initInstances() {
        // findViewById here
        txtName = (TextView) findViewById(R.id.txtNameChoice);
        txtSucess = (TextView) findViewById(R.id.txtNameSuccess);
        checkSel = (CheckBox) findViewById(R.id.checkSel);
        img = (ImageView) findViewById(R.id.imageView);

    }

    public void setText(Context context, String txtName, String url) {
        this.txtName.setText(txtName);
        Picasso.with(context).load(url).placeholder(R.drawable.ic_cached_black_48dp)
                .resize(200, 200)
                .centerCrop().into(img);
    }
    public void setDisable() {
        this.txtSucess.setVisibility(View.VISIBLE);
        this.checkSel.setVisibility(View.GONE);
    }

    public void setChecked(boolean check) {
        this.checkSel.setChecked(check);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

}
