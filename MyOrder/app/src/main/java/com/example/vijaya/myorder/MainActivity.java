package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 6;
    final int pepperonis_price = 2;
    final int sausage_price = 2;
    final int jalapenos_price = 1;
    final int onions_price = 1;
    final int mushrooms_price = 1;
    final int garlic_price = 1;
    int quantity = 1;

    String name = "";
    String order = "";
    String price = "";
    String quant = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if pepperonis is selected
        CheckBox pepperonis = (CheckBox) findViewById(R.id.pepperonis_checked);
        boolean hasPepperonis = pepperonis.isChecked();

        // check if sausage is selected
        CheckBox sausage = (CheckBox) findViewById(R.id.sausage_checked);
        boolean hasSausage = sausage.isChecked();

        // check if jalapenos is selected
        CheckBox jalapenos = (CheckBox) findViewById(R.id.jalapenos_checked);
        boolean hasJalapenos = jalapenos.isChecked();

        // check if mushrooms is selected
        CheckBox mushrooms = (CheckBox) findViewById(R.id.mushrooms_checked);
        boolean hasMushrooms = mushrooms.isChecked();

        // check if onions is selected
        CheckBox onions = (CheckBox) findViewById(R.id.onions_checked);
        boolean hasOnions = onions.isChecked();

        // check if garlic is selected
        CheckBox garlic = (CheckBox) findViewById(R.id.garlic_checked);
        boolean hasGarlic = garlic.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasPepperonis, hasSausage, hasJalapenos, hasOnions, hasMushrooms, hasGarlic);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasPepperonis, hasSausage, hasJalapenos, hasOnions, hasMushrooms, hasGarlic, totalPrice);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
        Intent redirect = new Intent(MainActivity.this, SummaryActivity.class);
        redirect.putExtra("OrderSummaryMessage", orderSummaryMessage);
        redirect.putExtra("order", order);
        redirect.putExtra("price", price);
        redirect.putExtra("quant", quant);
        redirect.putExtra("name", name);

        startActivity(redirect);

    }

    public void sendEmail(String name, String output) {

        // get user input
        EditText userInputNameView = findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if pepperonis is selected
        CheckBox pepperonis = findViewById(R.id.pepperonis_checked);
        boolean hasPepperonis = pepperonis.isChecked();

        // check if sausage is selected
        CheckBox sausage = findViewById(R.id.sausage_checked);
        boolean hasSausage = sausage.isChecked();

        // check if jalapenos is selected
        CheckBox jalapenos = findViewById(R.id.jalapenos_checked);
        boolean hasJalapenos = jalapenos.isChecked();

        // check if mushrooms is selected
        CheckBox mushrooms = findViewById(R.id.mushrooms_checked);
        boolean hasMushrooms = mushrooms.isChecked();

        // check if onions is selected
        CheckBox onions = findViewById(R.id.onions_checked);
        boolean hasOnions = onions.isChecked();

        // check if garlic is selected
        CheckBox garlic = findViewById(R.id.garlic_checked);
        boolean hasGarlic = garlic.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasPepperonis, hasSausage, hasJalapenos, hasOnions, hasMushrooms, hasGarlic);

        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_pepperonis, boolToString(hasPepperonis)) + "\n" +
                getString(R.string.order_summary_sausage, boolToString(hasSausage)) + "\n" +
                getString(R.string.order_summary_jalapenos, boolToString(hasJalapenos)) + "\n" +
                getString(R.string.order_summary_onions, boolToString(hasOnions)) + "\n" +
                getString(R.string.order_summary_mushrooms, boolToString(hasMushrooms)) + "\n" +
                getString(R.string.order_summary_garlic, boolToString(hasGarlic)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, totalPrice) + "\n" +
                getString(R.string.thank_you);

        // TODO: create email text view
        TextView email = (TextView) findViewById(R.id.email);

        Log.i("Send email", "");
        String[] TO = {email.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Pizza Order!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email"));
            finish();
            Log.i("Email sent", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasPepperonis, boolean hasSausage, boolean hasJalapenos, boolean hasOnions, boolean hasMushrooms, boolean hasGarlic,  float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_pepperonis, boolToString(hasPepperonis)) + "\n" +
                getString(R.string.order_summary_sausage, boolToString(hasSausage)) + "\n" +
                getString(R.string.order_summary_jalapenos, boolToString(hasJalapenos)) + "\n" +
                getString(R.string.order_summary_onions, boolToString(hasOnions)) + "\n" +
                getString(R.string.order_summary_mushrooms, boolToString(hasMushrooms)) + "\n" +
                getString(R.string.order_summary_garlic, boolToString(hasGarlic)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasPepperonis, boolean hasSausage, boolean hasJalapenos, boolean hasOnions, boolean hasMushrooms, boolean hasGarlic) {
        int basePrice = PIZZA_PRICE;
        if (hasPepperonis) {
            basePrice += pepperonis_price;
        }
        if (hasSausage) {
            basePrice += sausage_price;
        }
        if (hasJalapenos) {
            basePrice += jalapenos_price;
        }
        if (hasOnions) {
            basePrice += onions_price;
        }
        if (hasMushrooms) {
            basePrice += mushrooms_price;
        }
        if (hasGarlic) {
            basePrice += garlic_price;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of pizzas by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred cups of coffee");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_many_pizzas);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of pizzas by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select at least one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_pizzas);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}