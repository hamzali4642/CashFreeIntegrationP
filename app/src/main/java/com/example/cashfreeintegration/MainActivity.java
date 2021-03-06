package com.example.cashfreeintegration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.BuildConfig;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cashfree.pg.CFPaymentService;
import com.cashfree.pg.ui.gpay.GooglePayStatusListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_BANK_CODE;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_CVV;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_HOLDER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_MM;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_NUMBER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_YYYY;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_OPTION;
import static com.cashfree.pg.CFPaymentService.PARAM_UPI_VPA;
import static com.cashfree.pg.CFPaymentService.PARAM_WALLET_CODE;

public class MainActivity extends AppCompatActivity {

    String token = "TOKEN_DATA";
    TokenResponse model;

    String orderId;


    enum SeamlessMode {
        CARD, WALLET, NET_BANKING, UPI_COLLECT, PAY_PAL
    }

    SeamlessMode currentMode = SeamlessMode.CARD;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int min = 0;
        int max = 900;

        //Generate random int value from 50 to 100
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

        orderId= "orderid"+random_int;



        try {
            getToken();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getToken() throws JSONException {
        JSONObject jsonBody = new JSONObject();
        String URL = Constants.GEN_TEST_MODE_TOKEN;
//        jsonBody.put("orderId", "Order0001");
        jsonBody.put("orderId", orderId);
        jsonBody.put("orderCurrency", "INR");
        jsonBody.put("orderAmount", "1");


        JsonObjectRequest tokenRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
            Log.d("tokenRes", "abc" + response.toString());

            String status, message, cftoken;


            try {
                status = response.getString("status");
                message = response.getString("message");
                cftoken = response.getString("cftoken");
                model = new TokenResponse(status, message, cftoken);

                token = model.getCftoken();
                Log.d("CFTOKEN", "getToken: "+token);

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }, error -> {

            error.printStackTrace();
            //TODO: handle failure
        }


        ) {


            @Override
            public Map<String, String> getHeaders() {

                Map<String, String> params = new HashMap<>();

                // TODO: 22/06/2021 REQUIRED SECRET_KEY AND APP_ID FOR FURTHER TESTING...
                params.put("Content-Type", "application/json");
                params.put("x-client-id",Constants.TEST_APP_ID );
                params.put("x-client-secret", Constants.TEST_SECRET_KEY);
                return params;
            }
        };


        Volley.newRequestQueue(this).
                add(tokenRequest).setRetryPolicy(
                (new DefaultRetryPolicy(0, -1, 0))
        );

    }
    private void submitDetails() throws JSONException, NoSuchAlgorithmException, InvalidKeyException {
        JSONObject jsonBody = new JSONObject();
        String URL = Constants.GEN_TEST_MODE_TOKEN;
//        jsonBody.put("orderId", "Order0001");


        Map<String, String> postData = new HashMap<>();

//        postData.put("appId", appId);
//        postData.put("orderId", ORDERID);
//        postData.put("orderAmount", ORDERAMOUNT);
//        postData.put("orderNote", ORDERNOTE);
//        postData.put("customerName", CUSTOMER_NAME);
//        postData.put("customerEmail", CUSTOMER_EMAIL);
//        postData.put("customerPhone", CUSTOMER_PHONE);
             // card_number             // Sixteen digit card number. No spaces or hyphens accepted.
            // card_expiryMonth        //Expiration month for the card, in MM format.
           //card_expiryYear          // Expiration year for the card, in YYYY format.
          //card_cvv                 //CVV number of the card
         //card_holder              // Name of the card holder
        //paymentOption   //'card' for Debit/Credit Cards
        postData.put("paymentOption", "card");
        postData.put("paymentCode", "3333");


        JsonObjectRequest tokenRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
            Log.d("tokenRes", "abc" + response.toString());

            String status, message, cftoken;


            try {
                status = response.getString("status");
                message = response.getString("message");
                cftoken = response.getString("cftoken");
                model = new TokenResponse(status, message, cftoken);

                token = model.getCftoken();
                Log.d("CFTOKEN", "getToken: "+token);

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }, error -> {

            error.printStackTrace();
            //TODO: handle failure
        }


        ) {


            @Override
            public Map<String, String> getHeaders() {

                Map<String, String> params = new HashMap<>();

                // TODO: 22/06/2021 REQUIRED SECRET_KEY AND APP_ID FOR FURTHER TESTING...
                params.put("Content-Type", "application/json");
                params.put("x-client-id",Constants.TEST_APP_ID );
                params.put("x-client-secret", Constants.TEST_SECRET_KEY);
                return params;
            }
        };


        Volley.newRequestQueue(this).
                add(tokenRequest).setRetryPolicy(
                (new DefaultRetryPolicy(0, -1, 0))
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                        Log.d("respond", key + " : " + bundle.getString(key));

                        // TODO: 22/06/2021 ON RECEIVE RESPONSE VERIFY SIGNATURE SEE DOC AT: https://dev.cashfree.com/payment-gateway/integrations/mobile-integration/android-sdk#step-6-verify-response  AND THEN CALL DO_PAYMENT METHOD
                    }
                }
        }
    }

    public void onClick(View view) {


        /*
         * stage allows you to switch between sandboxed and production servers
         * for CashFree Payment Gateway. The possible values are
         *
         * 1. TEST: Use the Test server. You can use this service while integrating
         *      and testing the CashFree PG. No real money will be deducted from the
         *      cards and bank accounts you use this stage. This mode is thus ideal
         *      for use during the development. You can use the cards provided here
         *      while in this stage: https://docs.cashfree.com/docs/resources/#test-data
         *
         * 2. PROD: Once you have completed the testing and integration and successfully
         *      integrated the CashFree PG, use this value for stage variable. This will
         *      enable live transactions
         */
        String stage = "TEST";
//        String stage = "PROD";

        //Show the UI for doGPayPayment and phonePePayment only after checking if the apps are ready for payment
        if (view.getId() == R.id.phonePe_exists) {
            Toast.makeText(
                    MainActivity.this,
                    CFPaymentService.getCFPaymentServiceInstance().doesPhonePeExist(MainActivity.this, stage) + "",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (view.getId() == R.id.gpay_ready) {
            CFPaymentService.getCFPaymentServiceInstance().isGPayReadyForPayment(MainActivity.this, new GooglePayStatusListener() {
                @Override
                public void isReady() {
                    Toast.makeText(MainActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void isNotReady() {
                    Toast.makeText(MainActivity.this, "Not Ready", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        /*
         * token can be generated from your backend by calling cashfree servers. Please
         * check the documentation for details on generating the token.
         * READ THIS TO GENERATE TOKEN: https://bit.ly/2RGV3Pp
         */


        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        switch (view.getId()) {

            /***
             * This method handles the payment gateway invocation (web flow).
             *
             * @param context Android context of the calling activity
             * @param params HashMap containing all the parameters required for creating a payment order
             * @param token Provide the token for the transaction
             * @param stage Identifies if test or production service needs to be invoked. Possible values:
             *              PROD for production, TEST for testing.
             * @param color1 Background color of the toolbar
             * @param color2 text color and icon color of toolbar
             * @param hideOrderId If true hides order Id from the toolbar
             */
            case R.id.web: {
                cfPaymentService.doPayment(MainActivity.this, getInputParams(), token, stage, "#784BD2", "#FFFFFF", false);
//                 cfPaymentService.doPayment(MainActivity.this, params, token, stage);
                break;
            }
            /***
             * Same for all payment modes below.
             *
             * @param context Android context of the calling activity
             * @param params HashMap containing all the parameters required for creating a payment order
             * @param token Provide the token for the transaction
             * @param stage Identifies if test or production service needs to be invoked. Possible values:
             *              PROD for production, TEST for testing.
             */
            case R.id.upi: {
//                                cfPaymentService.selectUpiClient("com.google.android.apps.nbu.paisa.user");
                cfPaymentService.upiPayment(MainActivity.this, getInputParams(), token, stage);
                break;
            }
            case R.id.amazon: {
                cfPaymentService.doAmazonPayment(MainActivity.this, getInputParams(), token, stage);
                break;
            }
            case R.id.gpay: {
                cfPaymentService.gPayPayment(MainActivity.this, getInputParams(), token, stage);
                break;
            }
            case R.id.phonePe: {
                cfPaymentService.phonePePayment(MainActivity.this, getInputParams(), token, stage);
                break;
            }
            case R.id.web_seamless: {
                cfPaymentService.doPayment(MainActivity.this, getSeamlessCheckoutParams(), token, stage);
                break;
            }
        }
    }

    private Map<String, String> getInputParams() {

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.
         */
        // TODO: 22/06/2021 REQUIRED MERCHANT APP_ID FOR PASSING PARAMETER TO CASH_FREE SERVER
        String appId = Constants.TEST_APP_ID;
        String orderID = orderId;
//        String orderId = "Order0001";
        String orderAmount = "1";
        String orderNote = "Test Order";
        String customerName = "John Doe";
        String customerPhone = "9900012345";
        String customerEmail = "test@gmail.com";


        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderID);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, "INR");
        return params;
    }

    private Map<String, String> getSeamlessCheckoutParams() {
        Map<String, String> params = getInputParams();
        switch (currentMode) {
            case CARD:
                params.put(PARAM_PAYMENT_OPTION, "card");
                params.put(PARAM_CARD_NUMBER, "4111111111111111");
                params.put(PARAM_CARD_YYYY, "2023");
                params.put(PARAM_CARD_MM, "07");
                params.put(PARAM_CARD_HOLDER, "TEST");
                params.put(PARAM_CARD_CVV, "123");
                break;
            case WALLET:
                params.put(PARAM_PAYMENT_OPTION, "wallet");
                params.put(PARAM_WALLET_CODE, "4007"); // Put one of the wallet codes mentioned here https://dev.cashfree.com/payment-gateway/payments/wallets
                break;
            case NET_BANKING:
                params.put(PARAM_PAYMENT_OPTION, "nb");
                params.put(PARAM_BANK_CODE, "3333"); // Put one of the bank codes mentioned here https://dev.cashfree.com/payment-gateway/payments/netbanking
                break;
            case UPI_COLLECT:
                params.put(PARAM_PAYMENT_OPTION, "upi");
                params.put(PARAM_UPI_VPA, "VALID_VPA");
                break;
            case PAY_PAL:
                params.put(PARAM_PAYMENT_OPTION, "paypal");
                break;
        }
        return params;
    }
}