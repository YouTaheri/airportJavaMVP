package com.yousef.khedmatazma.safebodatest.network;

import android.content.Context;
import android.support.annotation.NonNull;
import com.yousef.khedmatazma.safebodatest.R;
import com.yousef.khedmatazma.safebodatest.utils.CustomProgressDialog;
import com.yousef.khedmatazma.safebodatest.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ServerRequest<T> {
    ServerRequest(final Context mContext, Call<T> call) {
        if (Utils.isNetworkConnected(mContext)) {
            final CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.show();
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                    try {
                        if (pd.isShowing())
                           pd.dismiss();
                        if (response.isSuccessful()) {
                            onCompletion(response);
                        } else {
                                Utils.showMessageDialog(mContext, mContext.getString(R.string.empty_schedule), null);
                        }
                    } catch (Exception e) {
                    }
                }
                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                    try {
                        if (pd.isShowing())
                            pd.dismiss();
                        Utils.showLog("Exp : " + t.getMessage());
                            Utils.showMessageDialog(mContext, mContext.getString(R.string.empty_schedule), null);
                    } catch (Exception e) {
                    }
                }
            });
        } else {
                Utils.showMessageDialog(mContext, mContext.getString(R.string.no_internet_message), null);
        }
    }
    public abstract void onCompletion(Response<T> response);
}
