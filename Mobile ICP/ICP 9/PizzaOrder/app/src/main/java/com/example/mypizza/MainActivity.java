package com.example.mypizza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";

    EditText userNameView;
    EditText userEmailView;
    CheckBox cheese;
    CheckBox mushrooms;
    CheckBox peppers;
    CheckBox chicken;
    Button OrderBtn;
    final int PIZZA_PRICE = 5;
    final int CHEESE_PRICE = 2;
    final int MUSHROOMS_PRICE = 1;
    final int PEPPERS_PRICE = 1;
    final int CHICKEN_PRICE = 2;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameView = findViewById(R.id.et_user);
        cheese = findViewById(R.id.cb_cheese);
        mushrooms = findViewById(R.id.cb_mushrooms);
        peppers = findViewById(R.id.cb_peppers);
        chicken =  findViewById(R.id.cb_chicken);
        userEmailView =  findViewById(R.id.et_email);
        OrderBtn = findViewById(R.id.btn_order);

        OrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }

    public void sendEmail() {

        // read user input
        String userName = userNameView.getText().toString();

        // read user email
        String userEmail = userEmailView.getText().toString();
        String[] userEmails = userEmail.split(",");

        // check if cheese is selected
        boolean hasCheese = cheese.isChecked();
        // check if mushroom is selected
        boolean hasMushrooms = mushrooms.isChecked();
        // check if bell peppers is selected
        boolean hasPeppers = peppers.isChecked();
        // check if chicken is selected
        boolean hasChicken = chicken.isChecked();


        // calculate and store the total price
        float totalPrice = calculatePrice(hasCheese, hasMushrooms, hasPeppers, hasChicken);

        // create and store the order summary details
        String orderSummaryMessage = createOrderSummary(userName, hasCheese, hasMushrooms, hasPeppers,hasChicken, totalPrice);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Log.i("Send email", "");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, userEmails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Delivery Details");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
        try {
            startActivity(Intent.createChooser(emailIntent, "Choose any email client"));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *when we click order button.
     */

    public void submitOrder(View view) {

        // user input will be read
        String userName = userNameView.getText().toString();

        // Cheese will be selected
        boolean hasCheese = cheese.isChecked();

        // Mushroom will be selected
        boolean hasMushrooms = mushrooms.isChecked();

        // Bell peppers will be selected
        boolean hasPeppers = peppers.isChecked();

        // Chicken will be selected
        boolean hasChicken = chicken.isChecked();

        // Total price will be counted and displayed
        float totalPrice = calculatePrice(hasCheese, hasMushrooms, hasPeppers, hasChicken);

        // Order summary will be generated
        String orderSummaryMessage = createOrderSummary(userName, hasCheese, hasMushrooms, hasPeppers,hasChicken, totalPrice);

        // Functioning of buttons
        Intent redirect = new Intent(MainActivity.this, OrderSummaryActivity.class);
        redirect.putExtra("MESSAGE", orderSummaryMessage);
        MainActivity.this.startActivity(redirect);
    }


    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userName, boolean hasCheese, boolean hasMushrooms, boolean hasPeppers, boolean hasChicken, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userName) + ",\n" + "\n" +

                getString(R.string.order_details) + "\n" +
                getString(R.string.order_summary_cheese, boolToString(hasCheese)) + "\n" +
                getString(R.string.order_summary_mushrooms, boolToString(hasMushrooms)) + "\n" +
                getString(R.string.order_summary_peppers, boolToString(hasPeppers)) + "\n" +
                getString(R.string.order_summary_chicken, boolToString(hasChicken)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" + "\n" +

                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasCheese, boolean hasMushrooms, boolean hasPeppers, boolean hasChicken) {
        int basePrice = PIZZA_PRICE;
        if (hasCheese) {
            basePrice += CHEESE_PRICE;
        }
        if (hasMushrooms) {
            basePrice += MUSHROOMS_PRICE;
        }
        if (hasPeppers) {
            basePrice += PEPPERS_PRICE;
        }
        if (hasChicken) {
            basePrice += CHICKEN_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * Quantity total will be displayed using this method.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.tv_quantity);
        quantityTextView.setText("" + number);
    }

    /**
     * Quantity of pizzas displayed
     *
     *
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred quantity");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * The quantity of pizza decreases one by one
     *
     *
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one quantity");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}