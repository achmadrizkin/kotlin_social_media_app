package com.example.kotlin_social_media_app.view.bottomNav.shop

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ExploreAdapter
import com.example.kotlin_social_media_app.adapter.ShopAdapter
import com.example.kotlin_social_media_app.model.product.Product
import com.example.kotlin_social_media_app.view.bottomNav.post_details.PostDetailsActivity
import com.example.kotlin_social_media_app.view.bottomNav.product_details.ProductDetailsActivity
import com.example.kotlin_social_media_app.view_model.SearchActivityViewModel
import com.example.kotlin_social_media_app.view_model.ShopActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class ShopFragment : Fragment(), ShopAdapter.OnItemClickListener {
    private lateinit var shopAdapter: ShopAdapter
    private lateinit var shopAdapter2: ShopAdapter

    //
    var disposables: CompositeDisposable? = null

    //
    private lateinit var inputProductName: EditText
    private lateinit var ivNoResult: ImageView
    private lateinit var tvNoResult: TextView

    //
    private lateinit var rcyclerViewShopLayout : RecyclerView
    private lateinit var searchViewShopLayout: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        rcyclerViewShopLayout = view.findViewById(R.id.rcyclerViewShopLayout)
        searchViewShopLayout = view.findViewById(R.id.searchViewShopLayout)
        inputProductName = view.findViewById(R.id.inputProductName)
        ivNoResult = view.findViewById(R.id.ivNoResult)
        tvNoResult = view.findViewById(R.id.tvNoResult)

        //
        initAllProductListRecyclerView()
        initSearchProductByNameRecyclerView()

        getAllProductListApiData()
        initSearchProduct()

        return view
    }

    private fun initAllProductListRecyclerView() {
        rcyclerViewShopLayout.apply {
            layoutManager = GridLayoutManager(activity, 2)

            shopAdapter = ShopAdapter(this@ShopFragment)
            adapter = shopAdapter
        }
    }

    private fun initSearchProductByNameRecyclerView() {
        searchViewShopLayout.apply {
            layoutManager = GridLayoutManager(activity, 2)

            shopAdapter2 = ShopAdapter(this@ShopFragment)
            adapter = shopAdapter2
        }
    }

    private fun initSearchProduct() {
        inputProductName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
                loadApiData(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun getAllProductListApiData() {
        val viewModel = ViewModelProvider(this).get(ShopActivityViewModel::class.java)
        viewModel.getAllProductListObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                shopAdapter.setShopList(it.data)
                shopAdapter.notifyDataSetChanged()

                //
                ivNoResult.visibility = View.GONE
                tvNoResult.visibility = View.GONE
            }
        })
        viewModel.getAllProductListOfData()
    }

    fun loadApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(ShopActivityViewModel::class.java)
        viewModel.searchProductByNameObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                searchViewShopLayout.visibility = View.VISIBLE
                rcyclerViewShopLayout.visibility = View.GONE

                //
                ivNoResult.visibility = View.GONE
                tvNoResult.visibility = View.GONE

                //
                shopAdapter2.setShopList(it.data)
                shopAdapter2.notifyDataSetChanged()
            } else {
                searchViewShopLayout.visibility = View.GONE
                rcyclerViewShopLayout.visibility = View.GONE

                //
                ivNoResult.visibility = View.VISIBLE
                tvNoResult.visibility = View.VISIBLE
            }

            if (input.isEmpty() || input == "") {
                searchViewShopLayout.visibility = View.GONE
                rcyclerViewShopLayout.visibility = View.VISIBLE

                //
                ivNoResult.visibility = View.GONE
                tvNoResult.visibility = View.GONE
            }
        })
        viewModel.searchProductByNameOfData(input)
    }

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopFragment()
    }

    override fun onItemClickListenerProduct(product: Product) {
        val i = Intent(activity, ProductDetailsActivity::class.java)
        startActivity(i)
    }
}