package DB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private Context context;
        private Activity activity;
        private ArrayList patient_id, patient_name, patient_age, patient_gender, patient_condition;

        public CustomAdapter(Activity activity, Context context, ArrayList patient_id, ArrayList patient_name, ArrayList patient_age,
                             ArrayList patient_gender, ArrayList patient_condition){
            this.activity = activity;
            this.context = context;
            this.patient_id = patient_id;
            this.patient_name = patient_name;
            this.patient_age = patient_age;
            this.patient_gender = patient_gender;
            this.patient_condition = patient_condition;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_row, parent, false);
            return new MyViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.patient_id_txt.setText(String.valueOf(patient_id.get(position)));
            holder.patient_name_txt.setText(String.valueOf(patient_name.get(position)));
            holder.patient_age_txt.setText(String.valueOf(patient_age.get(position)));
            holder.patient_gender_txt.setText(String.valueOf(patient_gender.get(position)));
            holder.patient_condition_txt.setText(String.valueOf(patient_condition.get(position)));
            //Recyclerview onClickListener
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", String.valueOf(patient_id.get(position)));
                    intent.putExtra("name", String.valueOf(patient_name.get(position)));
                    intent.putExtra("age", String.valueOf(patient_age.get(position)));
                    intent.putExtra("gender", String.valueOf(patient_gender.get(position)));
                    intent.putExtra("condition", String.valueOf(patient_condition.get(position)));
                    activity.startActivityForResult(intent, 1);
                }
            });


        }

        @Override
        public int getItemCount() {
            return patient_id.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView patient_id_txt, patient_name_txt, patient_age_txt, patient_gender_txt, patient_condition_txt;
            LinearLayout mainLayout;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                patient_id_txt = itemView.findViewById(R.id.book_id_txt);
                patient_name_txt = itemView.findViewById(R.id.name_txt);
                patient_age_txt = itemView.findViewById(R.id.age_txt);
                patient_gender_txt = itemView.findViewById(R.id.gender_txt);
                patient_condition_txt = itemView.findViewById(R.id.tvCondition);
                mainLayout = itemView.findViewById(R.id.mainLayout);
                //Animate Recyclerview
                // Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
                //mainLayout.setAnimation(translate_anim);
            }

        }

    }

