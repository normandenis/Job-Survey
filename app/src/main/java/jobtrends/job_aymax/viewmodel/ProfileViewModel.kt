package jobtrends.job_aymax.viewmodel

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.ProfileViewBinding

class ProfileViewModel : AppCompatActivity() {
    var UserName: String = "Félix Marcotte-Ruffin"
    var UserJob = "Expert Comptable"
    var Survey = 28
    var Point = 350
    var Ranking = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ProfileViewBinding = DataBindingUtil.setContentView(this, R.layout.profile_view)
        binding.vm = this
    }
}
