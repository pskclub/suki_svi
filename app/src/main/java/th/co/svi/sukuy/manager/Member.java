package th.co.svi.sukuy.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class Member {
    private String idEmployee = null;
    private String nameEmployee = null;

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }


    private static Member instance;

    public static Member getInstance() {
        if (instance == null)
            instance = new Member();
        return instance;
    }

    private Context mContext;

    private Member() {
        mContext = Contextor.getInstance().getContext();
    }

}
