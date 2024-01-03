package com.example.financetrackerapp_ctis487_team6

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ctis487.retrofit.Constants
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.financetrackerapp_ctis487_team6.adapter.CustomRecyclerViewAdapter
import com.example.financetrackerapp_ctis487_team6.databinding.ActivitySecondBinding
import com.example.financetrackerapp_ctis487_team6.db.Customer
import com.example.financetrackerapp_ctis487_team6.db.CustomerRoomDatabase
import com.google.android.material.snackbar.Snackbar
import java.util.Collections
import java.util.concurrent.TimeUnit
import androidx.work.OneTimeWorkRequestBuilder


class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding

    lateinit var workManager: WorkManager
    lateinit var workRequest: OneTimeWorkRequest
    lateinit var customWorker: CustomWorker
    var adapter: CustomRecyclerViewAdapter?=null

    private val customerDB: CustomerRoomDatabase by lazy {
        Room.databaseBuilder(this, CustomerRoomDatabase::class.java, Constants.DATABASENAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    var customer1: Customer = Customer("Salary", "30.10.2023", "Halkbank", 120000.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerCustomer.setLayoutManager(LinearLayoutManager(this))

        getData()

        val textView: TextView = findViewById(R.id.textView2)

        // Create a blinking animation with YoYo
        val blinkingAnimation = YoYo.with(Techniques.Flash)
            .repeat(YoYo.INFINITE)
            .duration(1000) // Adjust the duration as needed

        // Start the blinking animation
        blinkingAnimation.playOn(textView)

        binding.apply {
            fabAdd.setOnClickListener{
                //Assume that these customer details can be taken as an input from the user and input values are validated
                //var customerToAdd = Customer(444,"didem","kutlar",100.0)
                //var customerToAdd = Customer(555,"karan","kutlar",100.0)
                var customerToAdd = Customer("Shopping","11.10.2023","Garanti",-15000.0)
                customerDB.customerDao().insertCustomer(customerToAdd)
                getData()
                Snackbar.make(it, "Customer inserted", Snackbar.LENGTH_LONG).show()

            }
            fabDelete.setOnClickListener{
                customerDB.customerDao().deleteCustomer(customer1)
                getData()
                Snackbar.make(it, "Customer ${customer1.toString()} is deleted", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.fabDeleteAll.setOnClickListener{
            customerDB.customerDao().deleteAllCustomers()
            getData()
            Snackbar.make(it, "All customers are deleted", Snackbar.LENGTH_LONG).show()
        }
        binding.fabAddAll.setOnClickListener {
            prepareData()
            getData()
            Snackbar.make(it, "Customers are added", Snackbar.LENGTH_LONG).show()
        }

        binding.fabUpdate.setOnClickListener {
            customer1.name = "13.10.2023"
            customer1.surname = "ZiraatBank"
            customerDB.customerDao().updateCustomer(customer1)
            getData()
            Snackbar.make(it, "Customer updated", Snackbar.LENGTH_LONG).show()
        }

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // Cancel the notification that we started
        val intent = intent
        val notificationId = intent.getIntExtra("notificationID", 0)
        val message = intent.getStringExtra("msg")
        if (message != null) {
            mNotificationManager.cancel(notificationId)
            //binding.tvResult.text =message
            mNotificationManager.cancel(notificationId)
        }
        /*
        STEP4: Define Constraints if any
        When all constraints are met, work will run
        */
        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
        /*
        STEP3: Create work policy: Schedule the work
        OneTimeWorkRequest: worker will be used for just one work
        PeriodicWorkRequest: Worker will be used for periodic work
         */
        //STEP 4 & STEP7, Create work policy and send data as input to the work. How input will be taken , check CustomWorker class
        workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
            .setInputData(Data.Builder().putInt("num", 10).putString("name", "nese").build())
            .build()
        //it will begin immeditaley in the background
        // if constraints are assigned, it will run immeditaley if constraits are met
        //workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java).setConstraints(constraints).build()

        //STEP 5: To delay work for 10 minutes
        //workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java).setInitialDelay(10, TimeUnit.MINUTES).build();

        //STEP 6: To retry and backoff policy: in each 10 seconds it will retry, after subsequent attempts exponential it will try as 20,40,80...
        workRequest = OneTimeWorkRequestBuilder<CustomWorker>().setBackoffCriteria(
            BackoffPolicy.LINEAR,
            30,//OneTimeWorkRequest.MIN_BACKOFF_MILLIS
            TimeUnit.MILLISECONDS)
            .build()
        //workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java).build();
        //var mperidicRequest = PeriodicWorkRequest.Builder(CustomWorker::class.java, 1, TimeUnit.HOURS).build(); // work scheduled to one hour
        //var mperidicRequest = PeriodicWorkRequest.Builder(CustomWorker::class.java, 1, TimeUnit.HOURS, 15, TimeUnit.MINUTES).build(); // work scheduled to one hour

        /*
        STEP 8_1: to un the work, create WorkManager object
         */
        workManager = WorkManager.getInstance(this)

        binding.btnStartService.setOnClickListener(View.OnClickListener {
            //STEP 4 & STEP7, Create work policy and send data as input to the work. How input will be taken , check MyWorker class
            workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
                .setInputData(Data.Builder().putInt("num", 10).putString("name", "nese").build())
                .build()

            /*
            STEP 8-2: to run the work, assign request object to work manager.
           */
            var sum=0.0
            sum = customerDB.customerDao().getTotalDebt()

            workManager.enqueue(workRequest)
            Toast.makeText(this@SecondActivity, "The total is: $sum",
                Toast.LENGTH_SHORT).show()

            //at the end of background task what will be done
            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this@SecondActivity,
                Observer{ workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val resultData: Data = workInfo.outputData//get output of worker
                        Toast.makeText(this@SecondActivity, "SUCCEEDED " + resultData.getInt("result", 0),
                            Toast.LENGTH_LONG ).show()
                    }
                })

        })

    }



    private fun displayCustomers(customers: MutableList<Customer>) {
        adapter = CustomRecyclerViewAdapter(this, customers)
        binding.recyclerCustomer.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    fun getData(){
        if(customerDB.customerDao().getAllCustomers().isNotEmpty()){
            displayCustomers(customerDB.customerDao().getAllCustomers())
        }
        else{
            binding.recyclerCustomer.adapter = null
        }
    }
    fun prepareData(){
        var customers=ArrayList<Customer>()
        Collections.addAll(customers,
            customer1,
            Customer("Food", "15.10.2023", "Yapı Kredi", -1200.0),
            Customer("Entertainment", "01.10.2023", "Halkbank", -10000.0),
            Customer("Freelance Work", "15.10.2023", "Yapı Kredi", 5000.0))

        customerDB.customerDao().insertAllCustomer(customers)
        /*
         //OR
         customerDB.customerDao().insertCustomer(customer1)
         customerDB.customerDao().insertCustomer(Customer(148, "veli", "korkmaz", 200.0))
         customerDB.customerDao().insertCustomer(Customer(897, "ali", "candan", 150.0))
         customerDB.customerDao().insertCustomer(Customer(333, "zeynep", "aydogmus", 100.0))
         */
    }
}