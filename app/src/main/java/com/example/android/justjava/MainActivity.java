/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    public int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */


    public void submitOrder(View view) {

        EditText nameInput = (EditText) findViewById(R.id.name_input);
        String name = nameInput.getText().toString();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();


        int price = calculatePrice(quantity, hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, hasWhippedCream, hasChocolate, quantity, price);
//        displayMessage(priceMessage);

        String subject = "Just Java Order Summary for: " + name;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        Context context = getApplicationContext();
        CharSequence text = "You cannot order more than 100 cups of coffee.";
        int duration = Toast.LENGTH_SHORT;

        if (quantity == 100) {
            Toast.makeText(context, text, duration).show();
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    public void decrement(View view) {
        Context context = getApplicationContext();
        CharSequence text = "You cannot order less than 1 cup of coffee.";
        int duration = Toast.LENGTH_SHORT;


        if (quantity == 1){
            Toast.makeText(context, text, duration).show();
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     * @return the cost of the order
     */
    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        int price = quantity * 5;
        if (hasWhippedCream == true) {
            price += 1 * quantity;
        };
        if (hasChocolate == true){
            price += 2 * quantity;
        }
        return price;
    }

    /**
     * Creates a summary of the order
     *
     * @param hasWhippedCream Does the coffee have a whipped cream topping?
     * @param hasChocolate Does the coffee have a chocolate topping?
     * @param price the price of the order
     * @return a summary of the order
     */

    private String createOrderSummary(String name, boolean hasWhippedCream, boolean hasChocolate, int quantity, int price) {
        String orderSummary = getString(R.string.order_summary_name, name) + "\n" + getString(R.string.order_summary_whipped_cream) + " " + hasWhippedCream + "\n" + getString(R.string.order_summary_chocolate) + " " + hasChocolate + "\n" + getString(R.string.order_summary_quantity) + " " + quantity + "\n" + getString(R.string.order_summary_price) + " " + price + "\n" + getString(R.string.thank_you);
        return orderSummary;
    }
}