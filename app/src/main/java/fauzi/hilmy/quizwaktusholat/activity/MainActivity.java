package fauzi.hilmy.quizwaktusholat.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fauzi.hilmy.quizwaktusholat.R;
import fauzi.hilmy.quizwaktusholat.api.ApiClient;
import fauzi.hilmy.quizwaktusholat.api.ApiInterface;
import fauzi.hilmy.quizwaktusholat.model.ItemsItem;
import fauzi.hilmy.quizwaktusholat.model.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txtCountryCode)
    TextView txtCountryCode;
    @BindView(R.id.txtState)
    TextView txtState;
    @BindView(R.id.txtCountry)
    TextView txtCountry;
//    @BindView(R.id.txtDate)
//    TextView txtDate;
//    @BindView(R.id.txtSubuh)
//    TextView txtSubuh;
//    @BindView(R.id.txtShuruq)
//    TextView txtShuruq;
//    @BindView(R.id.txtZuhur)
//    TextView txtZuhur;
//    @BindView(R.id.txtAshar)
//    TextView txtAshar;
//    @BindView(R.id.txtMaghrib)
//    TextView txtMaghrib;
//    @BindView(R.id.txtIsya)
//    TextView txtIsya;

    @BindView(R.id.layout_main)
    LinearLayout layoutMain;
    @BindView(R.id.progress)
    ProgressBar progress;
    List<ItemsItem> itemsItems;
    @BindView(R.id.recycler)
    RecyclerView recyclerShalat;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadData();

    }

    private void loadData() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseData> call = apiInterface.readJadwalShalat();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {

                if (response.body().getStatusCode() == 1) {
                    txtCountryCode.setText(response.body().getCountryCode());
                    txtCountry.setText(response.body().getCountry());
                    txtState.setText(response.body().getCity());
                    Log.d("Data ", " respon" + response.body().getItems());
                    Log.d("respon data ", "" + new Gson().toJson(itemsItems));

                    itemsItems = response.body().getItems();
//                    txtSubuh.setText(itemsItems.get(0).getFajr());
//                    txtZuhur.setText(itemsItems.get(0).getDhuhr());
//                    txtAshar.setText(itemsItems.get(0).getAsr());
//                    txtMaghrib.setText(itemsItems.get(0).getMaghrib());
//                    txtIsya.setText(itemsItems.get(0).getIsha());
//                    txtShuruq.setText(itemsItems.get(0).getShurooq());
//                    try {
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
//                        Date date = dateFormat.parse(itemsItems.get(0).getDateFor());
//                        String date_formatted = dateFormat.format(date);
//                        txtDate.setText("Date: " + date_formatted);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }

                    adapter = new CustomAdapter(recyclerShalat, MainActivity.this, itemsItems);
                    recyclerShalat.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerShalat.setAdapter(adapter);
                    recyclerShalat.setHasFixedSize(true);
                } else {
                    Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e("Error ", "" + t.getMessage());

            }
        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        RecyclerView recyclerView;
        Context context;
        List<ItemsItem> itemsItems;

        public CustomAdapter(RecyclerView recyclerView, Context context, List<ItemsItem> itemsItems) {
            this.recyclerView = recyclerView;
            this.context = context;
            this.itemsItems = itemsItems;
        }

        @NonNull
        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
            holder.txtSubuh.setText(itemsItems.get(position).getFajr());
            holder.txtShuruq.setText(itemsItems.get(position).getShurooq());
            holder.txtZuhur.setText(itemsItems.get(position).getDhuhr());
            holder.txtAshar.setText(itemsItems.get(position).getAsr());
            holder.txtMaghrib.setText(itemsItems.get(position).getMaghrib());
            holder.txtIsya.setText(itemsItems.get(position).getIsha());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
            try {
                Date date = dateFormat.parse(itemsItems.get(position).getDateFor());
                SimpleDateFormat newdateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                String date_release = newdateFormat.format(date);
                holder.txtDate.setText(date_release);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return itemsItems.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txtDate, txtSubuh, txtZuhur, txtShuruq, txtAshar, txtMaghrib, txtIsya;

            public MyViewHolder(View itemView) {
                super(itemView);
                txtDate = itemView.findViewById(R.id.txtDate1);
                txtSubuh = itemView.findViewById(R.id.txtSubuh1);
                txtZuhur = itemView.findViewById(R.id.txtZuhur1);
                txtShuruq = itemView.findViewById(R.id.txtShuruq1);
                txtAshar = itemView.findViewById(R.id.txtAshar1);
                txtMaghrib = itemView.findViewById(R.id.txtMaghrib1);
                txtIsya = itemView.findViewById(R.id.txtIsya1);
            }
        }
    }
}
