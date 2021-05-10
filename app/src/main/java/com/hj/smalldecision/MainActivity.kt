package com.hj.smalldecision

import android.app.Dialog
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.hj.goodweight.extension.defaultSharedPreferences
import com.hj.smalldecision.animation.TossAnimation
import com.hj.smalldecision.databinding.ActivityMainBinding
import com.hj.smalldecision.ui.base.BaseActivity
import com.hj.smalldecision.utils.IntentExtras
import com.hj.smalldecision.weight.PrivacyDialog
import com.hj.smalldecision.weight.TipsDialog
import com.hj.smalldecision.weight.TossImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity() {

    companion object{
        const val HOME = 0
        const val COIN = 1
        const val DICE = 2
    }

    lateinit var binding: ActivityMainBinding
    private var currentClick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showPrivacyDialog()
        binding.apply {
            homeButton.setOnClickListener{
                if(currentClick == HOME){
                    return@setOnClickListener
                }
                currentClick = HOME
                updateBottomIcon(HOME)
                var isTurnTable = defaultSharedPreferences.getBoolean("home_model",false)
                if(isTurnTable){
                    Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.turn_table_fragment)
                }else{
                    Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.home_fragment)
                }
            }
            coinButton.setOnClickListener{
                if(currentClick == COIN){
                    return@setOnClickListener
                }
                currentClick = COIN
                updateBottomIcon(COIN)
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.coin_fragment)
            }
            diceButton.setOnClickListener{
                if(currentClick == DICE){
                    return@setOnClickListener
                }
                currentClick = DICE
                updateBottomIcon(DICE)
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.adventure_fragment)
            }
            var isTurnTable = defaultSharedPreferences.getBoolean("home_model",false)
            if(isTurnTable){
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.turn_table_fragment)
            }else{
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.home_fragment)
            }
            updateBottomIcon(HOME)
        }
    }

    private fun updateBottomIcon(clickIndex: Int){
        when(clickIndex){
            HOME -> {
                home_icon.setBackgroundResource(R.mipmap.main_bottom_home_icon)
                coin_icon.setBackgroundResource(R.mipmap.main_bottom_coin_dark_icon)
                dice_icon.setBackgroundResource(R.mipmap.main_bottom_dice_dark_icon)
            }
            COIN -> {
                home_icon.setBackgroundResource(R.mipmap.main_bottom_home_dark_icon)
                coin_icon.setBackgroundResource(R.mipmap.main_bottom_coin_icon)
                dice_icon.setBackgroundResource(R.mipmap.main_bottom_dice_dark_icon)
            }
            DICE -> {
                home_icon.setBackgroundResource(R.mipmap.main_bottom_home_dark_icon)
                coin_icon.setBackgroundResource(R.mipmap.main_bottom_coin_dark_icon)
                dice_icon.setBackgroundResource(R.mipmap.main_bottom_dice_icon)
            }
        }
    }

    private fun showPrivacyDialog(){
        var showPrivacyDialog = defaultSharedPreferences.getInt(IntentExtras.FIRST_SHOW_PRIVACY_DIALOG,0)==0
        if(showPrivacyDialog){
            var privacyDialog = PrivacyDialog.Builder(this)
                .setOnCancelListener(object: PrivacyDialog.OnCancelListener{
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        finish()
                    }
                })
                .setOnPositiveListener(object: PrivacyDialog.OnPositiveListener{
                    override fun onClick(dialog: Dialog) {
                        defaultSharedPreferences.edit().putInt(IntentExtras.FIRST_SHOW_PRIVACY_DIALOG,1).commit()
                        dialog.dismiss()
                    }
                })
                .create()
            privacyDialog.setCancelable(false)
            privacyDialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}