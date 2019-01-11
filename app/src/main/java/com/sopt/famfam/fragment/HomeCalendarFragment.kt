package com.sopt.famfam.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.get.GetMissionResponse
import com.sopt.famfam.get.GethistoryResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.fragment_home_alert.*
import kotlinx.android.synthetic.main.fragment_home_calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeCalendarFragment : Fragment(){

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home_calendar, container, false)
        getMissionResponse()
        return view
    }
    private fun getMissionResponse() {
        val getMissioneResponse = networkService.getMissioneResponse("application/json", FamilyData.token)
        getMissioneResponse.enqueue(object : Callback<GetMissionResponse> {
            override fun onFailure(call: Call<GetMissionResponse>, t: Throwable) {
                Log.e("mission fail", t.toString())
            }

            override fun onResponse(call: Call<GetMissionResponse>, response: Response<GetMissionResponse>) {
                if (response.body()!!.message == "미션 조회 성공") {
                    targetuser_nickname.text = response.body()!!.data.target
                    when(response.body()!!.data.mission.suffixType){
                        1 -> targetuser_nickname_suffix.text = "님에게"
                        2 -> targetuser_nickname_suffix.text = "님과"
                    }
                    mission.text = response.body()!!.data.mission.content
                    when(response.body()!!.data.mission.missionType){
                        1 -> mission_image.setImageResource(R.drawable.mission_action)
                        2 -> mission_image.setImageResource(R.drawable.mission_cook)
                        3 -> mission_image.setImageResource(R.drawable.mission_gift)
                        4 -> mission_image.setImageResource(R.drawable.mission_music)
                        5 -> mission_image.setImageResource(R.drawable.mission_book)
                        6 -> mission_image.setImageResource(R.drawable.mission_carmera)
                        7 -> mission_image.setImageResource(R.drawable.mission_letter)
                    }
                } else if (response.body()!!.message == "가족 구성원을 초대해보세요!") {
                    mission_title.visibility = View.GONE
                    targetuser_nickname.visibility = View.GONE
                    targetuser_nickname_suffix.visibility = View.GONE
                    mission.text = response.body()!!.message
                    mission_image.setImageResource(R.drawable.mission_famfamfam)
                } else {
                    // 예외처리
                    mission_title.visibility = View.GONE
                    targetuser_nickname.visibility = View.GONE
                    targetuser_nickname_suffix.visibility = View.GONE
                    mission.text = response.body()!!.message
                    mission_image.setImageResource(R.drawable.mission_famfamfam)
                }
            }
        })
    }
}
