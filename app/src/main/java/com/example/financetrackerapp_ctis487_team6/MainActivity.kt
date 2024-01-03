package com.example.financetrackerapp_ctis487_team6

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ctis487.retrofit.ApiClient
import com.ctis487.retrofit.Currency
import com.ctis487.retrofit.CurrencyService
import com.ctis487.retrofitjsonobjectwithjsonobjectandarray.Parent
import com.example.financetrackerapp_ctis487_team6.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener, TopFragment.TopFragmentListener {
    val TAG:String="GESTURE"
    lateinit var parent: Parent
    var gDetector: GestureDetectorCompat? = null
    lateinit var currencyService: CurrencyService
    lateinit var currencydata: List<Currency>
    lateinit var bottomFragment : BottomFragment

    lateinit var binding: ActivityMainBinding

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomFragment = BottomFragment()
        loadFrag(bottomFragment)
        currencyService = ApiClient.getClient().create(CurrencyService::class.java) // By that reference retrofit understands which requests will be sent to server
        var request = currencyService.getCurrencies()

        Log.d("JSONARRAYPARSE", "Before Request")
        request.enqueue(object : Callback<Parent> {
            override fun onFailure(call: Call<Parent>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
                Log.d("JSONARRAYPARSE", "Error: "+t.message.toString())
            }
            override fun onResponse(call: Call<Parent>, response: Response<Parent>) {
                Log.d("JSONARRAYPARSE", "Response taken")
                if (response.isSuccessful) {
                    parent = (response.body() as Parent?)!!
                    Log.d("JSONARRAYPARSE", "Recipes taken from server"+parent.toString())
                    //adapter.setData(parent.recipes!!)
                    currencydata = parent.currencies!!
                    val topFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewTop) as? TopFragment

                    topFragment?.updateCurrencyData(currencydata)
                    //binding.textView.setText(parent.currencies.toString())
                }
            }
        })
        Log.d("JSONARRAYPARSE", "After Request")

        gDetector =  GestureDetectorCompat(this, this)
        gDetector?.setOnDoubleTapListener(this)


        binding.secondActivityBtn.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            playSound()
            startActivity(intent)
        }


        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.happy)

    }

    private fun playSound() {
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer resources
        mediaPlayer?.release()
    }

    fun loadFrag(dynamicFragment: Fragment) {
        val bundle=Bundle()
        bundle.putInt("num1", 10)
        bundle.putString("num2", "20")
        dynamicFragment.arguments = bundle
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.linearLayoutForDynamicFragment, dynamicFragment)
        ft.commit()

        //fm.beginTransaction().add(R.id.linearLayoutForDynamicFragment, dynamicFragment).commit()
        //ft.remove()

//        val otherFragment = TopFragment()
//        ft.replace(R.id.linearLayoutForDynamicFragment, otherFragment)
//        ft.addToBackStack(null)
//        ft.commit()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        Log.i("GESTURE","onTouchEvent ${event.action}")
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        Log.i(TAG, "onDown")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        Log.i(TAG, "onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.i(TAG, "onSingleTapUp")
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.i(TAG, "onScroll")
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        Log.i(TAG, "onLongPress")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.i(TAG, "onFling")
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.i(TAG, "onSingleTapConfirmed")
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.i(TAG, "onDoubleTap")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.i(TAG, "onDoubleTapEvent")
        return true
    }

    override fun onButtonClick(text: String) {
        bottomFragment.updateDetailsText(text)

    }


}