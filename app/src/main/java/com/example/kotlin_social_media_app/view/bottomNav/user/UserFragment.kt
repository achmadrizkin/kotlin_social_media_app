package com.example.kotlin_social_media_app.view.bottomNav.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ExploreAdapter
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.view.auth.SignInActivity
import com.example.kotlin_social_media_app.view.bottomNav.add_post.AddPostActivity
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
    private lateinit var ivAddPost: ImageView

    private lateinit var tvUserNameHeader: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvEmpty: TextView
    private lateinit var imguserUrl: String

    private lateinit var buttonEditProfile: Button

    private lateinit var tvPostCount: TextView
    private lateinit var tvFollowersCount: TextView
    private lateinit var tvFollowingCount: TextView

    var name_user: String = "Anonymous"
    var image_url: String = "null"
    var email_user: String = "null"
    var following: String = "0"
    var followers: String = "0"
    var post: String = "0"
    private lateinit var idUser: String


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

        email_user = currentUser?.email!!

        ivImageUrl = view.findViewById(R.id.ivImageUrl)
        tvUserNameHeader = view.findViewById(R.id.tvUserNameHeader)
        tvUserName = view.findViewById(R.id.tvUserName)
        tvPostCount = view.findViewById(R.id.tvPostCount)
        tvFollowersCount = view.findViewById(R.id.tvFollowersCount)
        tvFollowingCount = view.findViewById(R.id.tvFollowingCount)
        ivEmpty = view.findViewById(R.id.ivEmpty)
        tvEmpty = view.findViewById(R.id.tvEmpty)
        buttonEditProfile = view.findViewById(R.id.buttonEditProfile)
        ivAddPost = view.findViewById(R.id.ivAddPost)

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
            intent.putExtra("id", idUser)

            startActivity(intent)
        }

        ivAddPost.setOnClickListener {
            val intent = Intent(activity, AddPostActivity::class.java)

            intent.putExtra("image_url", image_url)
            intent.putExtra("name_user", name_user)
            intent.putExtra("email_user", email_user)

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
                tvUserName.setText(it.data[0].name_user)
                tvUserNameHeader.setText(it.data[0].name_user)

                // image
                if (it.data[0].image_url.isNotEmpty()) {
                    Glide.with(ivImageUrl).load(it.data[0].image_url).circleCrop().into(ivImageUrl)
                }
                imguserUrl = it.data[0].image_url

                //
                image_url = it.data[0].image_url
                name_user = it.data[0].name_user
                email_user = it.data[0].email_user
                following = it.data[0].following.toString()
                followers = it.data[0].followers.toString()
                idUser = it.data[0].id.toString()
                post = it.data[0].post.toString()


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

        i.putExtra("position", position.toString())

        startActivity(i)
    }
}