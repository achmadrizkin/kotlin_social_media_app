package com.example.kotlin_social_media_app.view.bottomNav.product_details

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.constant.Constant.BASE_URL_MIDTRANS
import com.example.kotlin_social_media_app.constant.Constant.CLIENT_KEY_MIDTRANS
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class ProductDetailsActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var tvNameProduct: TextView
    private lateinit var tvDescriptionProduct: TextView
    private lateinit var tvPriceProduct: TextView

    private lateinit var btnMidtrans: Button
    private lateinit var btnRazorpay: Button

    //
    private lateinit var ivImageProduct: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        tvNameProduct = findViewById(R.id.tvNameProduct)
        tvDescriptionProduct = findViewById(R.id.tvDescriptionProduct)
        ivImageProduct = findViewById(R.id.ivImageProduct)
        tvPriceProduct = findViewById(R.id.tvPriceProduct)

        btnMidtrans = findViewById(R.id.btnMidtrans)
        btnRazorpay = findViewById(R.id.btnRazorpay)

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val image_url = intent.getStringExtra("image_url")
        val price = intent.getStringExtra("price")

        tvNameProduct.text = name
        tvDescriptionProduct.text = description
        tvPriceProduct.text = "Rp. " + price
        Glide.with(ivImageProduct).load(image_url).into(ivImageProduct)

        //
        SdkUIFlowBuilder.init().setClientKey(CLIENT_KEY_MIDTRANS).setContext(applicationContext)
            .setTransactionFinishedCallback(
                TransactionFinishedCallback {
                    if (TransactionResult.STATUS_SUCCESS == "success") {
                        Toast.makeText(this, "Success transaction", Toast.LENGTH_LONG).show()
                        //TODO: ADD DATABASE IN HERE

                    } else if (TransactionResult.STATUS_PENDING == "pending") {
                        Toast.makeText(this, "Pending transaction", Toast.LENGTH_LONG).show()
                    } else if (TransactionResult.STATUS_FAILED == "failed") {
                        Toast.makeText(
                            this,
                            "Failed Transaction",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(this, "Failure transaction", Toast.LENGTH_LONG).show()
                    }
                }).setMerchantBaseUrl(BASE_URL_MIDTRANS).enableLog(true).setColorTheme(
                CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")
            ).setLanguage("id").buildSDK()


        //
        btnMidtrans.setOnClickListener {
            Toast.makeText(this, "Open transaction", Toast.LENGTH_LONG).show()

            val transactionRequest = TransactionRequest(
                "Beep-Midtrans" + System.currentTimeMillis().toString(),
                price!!.toDouble()
            )
            val detail = com.midtrans.sdk.corekit.models.ItemDetails(
                name,
                price!!.toDouble(),
                1,
                name
            )
            val itemDetails = ArrayList<com.midtrans.sdk.corekit.models.ItemDetails>()

            itemDetails.add(detail)
            uiKitDetails(transactionRequest)
            transactionRequest.itemDetails = itemDetails
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)

            TransactionResult.STATUS_SUCCESS
        }

        btnRazorpay.setOnClickListener {
            startPayment(name!!, description!!, image_url!!, price!!)
        }
    }

    fun uiKitDetails(transactionRequest: TransactionRequest) {

        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = "Supriyanto"
        customerDetails.phone = "082345678999"
        customerDetails.firstName = "Supri"
        customerDetails.lastName = "Yanto"
        customerDetails.email = "supriyanto6543@gmail.com"
        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Baturan, Gantiwarno"
        shippingAddress.city = "Klaten"
        shippingAddress.postalCode = "51193"
        customerDetails.shippingAddress = shippingAddress
        val billingAddress = BillingAddress()
        billingAddress.address = "Baturan, Gantiwarno"
        billingAddress.city = "Klaten"
        billingAddress.postalCode = "51193"

        customerDetails.billingAddress = billingAddress
        transactionRequest.customerDetails = customerDetails
    }

    // RAZORPAY PAYMENT
    private fun startPayment(
        name_product: String,
        description: String,
        image_url: String,
        price: String
    ) {
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", name_product)
            options.put("description", description)
            //You can omit the image option to fetch the image from dashboard
            options.put("image", image_url)
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");

            //order_id will come from backend
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount", price.toInt())//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("email", "himanshudhimanhr@gmail.com")
            prefill.put("contact", "7009029910")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Error in payment: " + p1.toString(), Toast.LENGTH_LONG).show()
    }
}