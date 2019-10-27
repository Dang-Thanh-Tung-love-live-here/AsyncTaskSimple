package com.doctor.AsyncTaskSimple;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edt_total_button)
    EditText edtTotalButton;
    @BindView(R.id.btn_draw)
    Button btnDraw;
    @BindView(R.id.llayout_draw_button)
    LinearLayout llayoutDrawButton;
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
    );
    @BindView(R.id.prob_draw)
    ProgressBar probDraw;
    @BindView(R.id.txt_progress)
    TextView txtProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        probDraw.setMax(100);

    }

    @OnClick(R.id.btn_draw)
    public void onClick() {
        int numberButton = Integer.parseInt(edtTotalButton.getText().toString());
        DrawTask drawTask = new DrawTask();
        drawTask.execute(numberButton);

    }

    class DrawTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            //Never Update UI in this method
            int values = integers[0];
            for (int i = 0; i <= values; i++) {
                try {
                    Thread.sleep(200);
                    publishProgress(i, i / values);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btnDraw.setEnabled(true);
            probDraw.setProgress(100);
            txtProgress.setText("100%");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llayoutDrawButton.removeAllViews();
            btnDraw.setEnabled(false);
            probDraw.setProgress(0);
            txtProgress.setText("0%");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int value = values[0];
            int progress = values[1] * 100;
            probDraw.setProgress(progress);
            txtProgress.setText(progress+"%");
            Button button = new Button(MainActivity.this);
            button.setText("" + value);
            button.setLayoutParams(layoutParams);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Button " + value, Toast.LENGTH_SHORT).show();
                }
            });
            llayoutDrawButton.addView(button);

        }
    }
}
