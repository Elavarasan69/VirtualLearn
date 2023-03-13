package com.robosoft.virtuallearnproject
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.robosoft.virtuallearnproject.adapter.NotificationAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentNotificationBinding
import com.robosoft.virtuallearnproject.dataclass.test.NotificationData
import com.robosoft.virtuallearnproject.network.notification.NotificationApiService
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationFragment : Fragment() {
    val msgArray = ArrayList<NotificationData>()

    lateinit var NotificationFragmentBinding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        NotificationFragmentBinding = FragmentNotificationBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        NotificationFragmentBinding.notificationBackBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        NotificationFragmentBinding.root.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                activity?.supportFragmentManager?.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                true
            } else {
                false
            }
        }

        val apiService = NotificationApiService()
        val access_token = SharedPreferenceManager(requireContext()).getAccessToken()!!
        GlobalScope.launch {
            apiService.getNotification(access_token){
                if(isAdded) {
                    val recyclerview = NotificationFragmentBinding.recyclerView
                    recyclerview.layoutManager = LinearLayoutManager(context)
                    val adapter = it?.message?.notifications?.let { it1 ->
                        NotificationAdapter(
                            activity?.applicationContext!!,
                            it1
                        )
                    }

                    recyclerview.adapter = adapter
                }
            }
        }
        return NotificationFragmentBinding.root
    }

}