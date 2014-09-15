package us.broani.quicktip;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class CalculateTip extends Activity {

    private static final String TAG = CalculateTip.class.getName();
    private static final double DEFAULT_TIP_PERCENTAGE = .2;

    private TextView tipView;
    private TextView totalView;
    private EditText subTotalEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_tip);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculate_tip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onLoad(View view) {
//        bindUI(view);
    }

    public void onSubmit(View view) {
        bindUI();
        Check form = getForm();
        fire(form);
        fromForm(form);
    }

    private void bindUI() {
        tipView = (TextView) findViewById(R.id.tipView);
        totalView = (TextView) findViewById(R.id.totalView);
        subTotalEdit = (EditText) findViewById(R.id.subTotalEdit);
    }

    private Check getForm() {
        Check check = new Check();
        check.subTotal = Double.parseDouble(subTotalEdit.getText().toString());
        check.tipPercentage = DEFAULT_TIP_PERCENTAGE; // replace with value from UI eventually
        return check;
    }

    private void fire(Check check) {
        if(check != null) {
            Log.d(TAG, "Processing check");

            check.calculatedTip = check.subTotal * check.tipPercentage;
            check.calculatedTotal = check.calculatedTip + check.subTotal;
        }
    }

    private void fromForm(Check check) {
        tipView.setText(formatDollars(check.calculatedTip, true));
        totalView.setText(formatDollars(check.calculatedTotal, true));
        subTotalEdit.setText(formatDollars(check.subTotal, false));
    }

    private String formatDollars(double dollars, boolean withSymbol) {
        DecimalFormat df = new DecimalFormat("0.00");
        String twoDecimals = df.format(dollars);
        return withSymbol ? "$ " + twoDecimals : twoDecimals;
    }






    private class Check {
        private double calculatedTip;
        private double calculatedTotal;
        private double subTotal;
        private double tipPercentage;

        private double taxPercentage;
        private double calculatedTax;
    }
}
