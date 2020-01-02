package com.example.android.dancequizapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /*
     * The score will be in percentage.
     * There 6 questions. Hence, for one question user could get max about 16.6% (100/6)
     * However, some questions have multiple answers, so we will calculate the score for such
     * questions as correct answers count divided by 16.6%
     * */
    final int NUMBER_OF_BALLROOM_DANCES = 10;
    final int NUMBER_OF_WINS = 14;

    float score = 0;

    TextView question1SliderValue;
    TextView question4SliderValue;

    private SeekBar.OnSeekBarChangeListener question1SeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            question1SliderValue.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            question1SliderValue.setText(String.valueOf(seekBar.getProgress()));
        }
    };

    private EditText.OnEditorActionListener question5EditTextActionListener = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                boolean tookFocus = v.requestFocus(View.FOCUS_DOWN, null);
                Log.i("MainActivity.java", "OnEditorActionListener: " + tookFocus);

                return true;
            }
            return false;
        }
    };

    private SeekBar.OnSeekBarChangeListener question4SeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            question4SliderValue.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            question4SliderValue.setText(String.valueOf(seekBar.getProgress()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar question1SeekBar = findViewById(R.id.question1_seek_bar);
        question1SeekBar.setOnSeekBarChangeListener(question1SeekBarChangeListener);

        question1SliderValue = findViewById(R.id.question1_slider_value);
        question1SliderValue.setText("2");

        final SeekBar question4SeekBar = findViewById(R.id.question4_seek_bar);
        question4SeekBar.setOnSeekBarChangeListener(question4SeekBarChangeListener);

        question4SliderValue = findViewById(R.id.question4_slider_value);
        question4SliderValue.setText("2");

        EditText question5EditText = findViewById(R.id.question5_editText);
        question5EditText.setOnEditorActionListener(question5EditTextActionListener);
    }

    public void checkAnswers(View view) {

        /* Checking answer 1 */
        score += checkQuestion1();
        Log.i("MainActivity.java", "answer 1 score: " + score);

        /* Checking answer 2 */
        score += checkQuestion2();
        Log.i("MainActivity.java", "answer 2 score: " + score);

        /* Check answer 3 */
        score += checkQuestion3();
        Log.i("MainActivity.java", "answer 3 score: " + score);

        /* Check answer 4 */
        score += checkQuestion4();
        Log.i("MainActivity.java", "answer 4 score: " + score);

        /* Check answer 5 */
        score += checkQuestion5();
        Log.i("MainActivity.java", "answer 5 score: " + score);

        /* Check answer 6 */
        score += checkQuestion6();
        Log.i("MainActivity.java", "answer 6 score: " + score);

        score = (score / 6) * 100;
        Log.i("MainActivity.java", "Total: " + score);

        String toastMessage = getString(R.string.final_score, score);
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

        score = 0;
    }

    private float checkQuestion1() {
        float answer1_score = 0;

        int answerForQuestion1 = Integer.parseInt(question1SliderValue.getText().toString());
        Log.i("MainActivity.java", "checkQuestion1: " + answerForQuestion1);
        if (answerForQuestion1 == NUMBER_OF_BALLROOM_DANCES) {
            answer1_score++;
        }

        return answer1_score;
    }

    private float checkQuestion2() {
        float answer2_score = 0;

        CheckBox checkBox = findViewById(R.id.question2_salsa_checkbox);
        if (checkBox.isChecked()) {
            answer2_score--;
        }

        checkBox = findViewById(R.id.question2_lindy_hop_checkbox);
        if (checkBox.isChecked()) {
            answer2_score--;
        }

        checkBox = findViewById(R.id.question2_rumba_checkbox);
        if (checkBox.isChecked()) {
            answer2_score++;
        }

        checkBox = findViewById(R.id.question2_samba_checkbox);
        if (checkBox.isChecked()) {
            answer2_score++;
        }

        checkBox = findViewById(R.id.question2_foxtrot_checkbox);
        if (checkBox.isChecked()) {
            answer2_score++;
        }

        checkBox = findViewById(R.id.question2_slow_waltz_checkbox);
        if (checkBox.isChecked()) {
            answer2_score++;
        }

        if (answer2_score > 0) {
            answer2_score /= 4;
        } else {
            answer2_score = 0;
        }

        Log.i("MainActivity.java", "checkQuestion2: " + answer2_score);

        return answer2_score;
    }

    private float checkQuestion3() {
        float answer3_score = 0;

        RadioGroup radioGroup = findViewById(R.id.question3_radio_group);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.question3_paso_radio) {
            answer3_score++;
        }
        Log.i("MainActivity.java", "checkQuestion3: " + answer3_score);

        return answer3_score;
    }

    private float checkQuestion4() {
        float answer4_score = 0;

        int answerForQuestion4 = Integer.parseInt(question4SliderValue.getText().toString());
        Log.i("MainActivity.java", "checkQuestion4: " + answerForQuestion4);
        if (answerForQuestion4 == NUMBER_OF_WINS) {
            answer4_score++;
        }

        return answer4_score;
    }

    private float checkQuestion5() {
        float answer5_score = 0;

        EditText editText = findViewById(R.id.question5_editText);
        String answerForQuestion5 = editText.getText().toString();

        Log.i("MainActivity.java", "checkQuestion1: " + answerForQuestion5);
        answerForQuestion5 = answerForQuestion5.toLowerCase();
        if (answerForQuestion5.equals("blackpool")) {
            answer5_score++;
        }

        return answer5_score;
    }

    private float checkQuestion6() {
        float answer6_score = 0;

        CheckBox checkBox = findViewById(R.id.question6_ido_checkbox);
        if (checkBox.isChecked()) {
            answer6_score++;
        }

        checkBox = findViewById(R.id.question6_ipsf_checkbox);
        if (checkBox.isChecked()) {
            answer6_score++;
        }

        checkBox = findViewById(R.id.question6_wadf_checkbox);
        if (checkBox.isChecked()) {
            answer6_score++;
        }

        checkBox = findViewById(R.id.question6_wdc_checkbox);
        if (checkBox.isChecked()) {
            answer6_score++;
        }

        checkBox = findViewById(R.id.question6_wdsf_checkbox);
        if (checkBox.isChecked()) {
            answer6_score++;
        }

        checkBox = findViewById(R.id.question6_ipsf_checkbox);
        if (checkBox.isChecked()) {
            answer6_score++;
        }

        answer6_score /= 6;


        Log.i("MainActivity.java", "checkQuestion6: " + answer6_score);

        return answer6_score;
    }

}
