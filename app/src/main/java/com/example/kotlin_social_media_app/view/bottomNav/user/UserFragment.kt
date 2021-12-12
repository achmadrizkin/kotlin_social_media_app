package com.example.kotlin_social_media_app.view.bottomNav.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ExploreAdapter
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.view.auth.SignInActivity
import com.example.kotlin_social_media_app.view.bottomNav.edit_profile_user.EditProfileActivity
import com.example.kotlin_social_media_app.view.bottomNav.following.FollowingActivity
import com.example.kotlin_social_media_app.view.bottomNav.user_post_details.UserPostActivity
import com.example.kotlin_social_media_app.view_model.UserActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class UserFragment : Fragment(), ExploreAdapter.OnItemClickListener {
    lateinit var recyclerViewUserLayout : RecyclerView
    lateinit var exploreAdapter: ExploreAdapter
    private lateinit var mAuth: FirebaseAuth

    //
    private lateinit var ivImageUrl: ImageView
    private lateinit var ivEmpty: ImageView

    private lateinit var tvUserNameHeader: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvEmpty: TextView
    private lateinit var imguserUrl: String

    private lateinit var buttonEditProfile: Button

    private lateinit var tvPostCount: TextView
    private lateinit var tvFollowersCount: TextView
    private lateinit var tvFollowingCount: TextView

    private lateinit var name_user: String
    private lateinit var image_url: String
    private lateinit var email_user: String
    private lateinit var following: String
    private lateinit var followers: String
    private lateinit var post: String
    private lateinit var id: String


    var disposables: CompositeDisposable? = null

    private lateinit var ivExit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        recyclerViewUserLayout = view.findViewById(R.id.searchViewUser)
        ivExit = view.findViewById(R.id.ivExit)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        ivImageUrl = view.findViewById(R.id.ivImageUrl)
        tvUserNameHeader = view.findViewById(R.id.tvUserNameHeader)
        tvUserName = view.findViewById(R.id.tvUserName)
        tvPostCount = view.findViewById(R.id.tvPostCount)
        tvFollowersCount = view.findViewById(R.id.tvFollowersCount)
        tvFollowingCount = view.findViewById(R.id.tvFollowingCount)
        ivEmpty = view.findViewById(R.id.ivEmpty)
        tvEmpty = view.findViewById(R.id.tvEmpty)
        buttonEditProfile = view.findViewById(R.id.buttonEditProfile)

        initExploreRecyclerView(view)
        getUserByEmail(currentUser?.email!!)
        getExploreByEmailApiData(currentUser.email!!)

        ivExit.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }

        tvFollowingCount.setOnClickListener {
            val intent = Intent(activity, FollowingActivity::class.java)

            //
            intent.putExtra("email", currentUser?.email!!)

            startActivity(intent)
        }

        buttonEditProfile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)

            intent.putExtra("image_url", image_url)
            intent.putExtra("name_user", name_user)
            intent.putExtra("email_user", email_user)
            intent.putExtra("following", following)
            intent.putExtra("followers", followers)
            intent.putExtra("post", post)
            intent.putExtra("id", id)

            startActivity(intent)
        }

        return view
    }

    private fun initExploreRecyclerView(view: View) {
        recyclerViewUserLayout = view.findViewById<RecyclerView>(R.id.searchViewUser)
        recyclerViewUserLayout.apply {
            layoutManager = GridLayoutManager(activity, 3)

            exploreAdapter = ExploreAdapter(this@UserFragment)
            adapter = exploreAdapter
        }
    }

    private fun getUserByEmail(email: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getUserByEmailObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.data[0].name_user.isEmpty() || it.data[0].name_user == "" || it.data[0].image_url.isEmpty() || it.data[0].image_url == "") {
                    tvUserName.setText("Anonymous")
                    tvUserNameHeader.setText("Anonymous")
                } else {
                    tvUserName.setText(it.data[0].name_user)
                    tvUserNameHeader.setText(it.data[0].name_user)

                    // image
                    Glide.with(ivImageUrl).load(it.data[0].image_url).circleCrop().into(ivImageUrl)
                    imguserUrl = it.data[0].image_url

                    //
                    image_url = it.data[0].image_url
                    name_user = it.data[0].name_user
                    id = it.data[0].id
                    email_user = it.data[0].email_user
                    following = it.data[0].following.toString()
                    followers = it.data[0].followers.toString()
                    post = it.data[0].post.toString()
                }

                //
                tvPostCount.setText(it.data[0].post.toString())
                tvFollowersCount.setText(it.data[0].followers.toString())
                tvFollowingCount.setText(it.data[0].following.toString())
            } else {
                recyclerViewUserLayout.visibility = View.GONE
            }
        })
        viewModel.getUserListByEmailOfData(email)
    }

    private fun getExploreByEmailApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getExploreObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                exploreAdapter.setExploreList(it.data)
                exploreAdapter.notifyDataSetChanged()
            } else {
                recyclerViewUserLayout.visibility = View.GONE

                ivEmpty.visibility = View.VISIBLE
                tvEmpty.visibility = View.VISIBLE
            }
        })
        viewModel.getExploreListOfData(input)
    }

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }

    override fun onItemClickListenerExplore(explore: Explore, position: Int) {
        val i = Intent(activity, UserPostActivity::class.java)

        i.putExtra("image_url", explore.image_url)
        i.putExtra("name_user", explore.name_user)
        i.putExtra("image_post", explore.image_post)
        i.putExtra("like_post", explore.like_post)
        i.putExtra("description_post", explore.description_post)

        startActivity(i)
    }
}