package com.sam.stockassignment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDialogFragment
import com.sam.stockassignment.databinding.ActivityMainBinding
import com.sam.stockassignment.util.PriceManager
import com.sam.stockassignment.util.SpUtil
import com.sam.stockassignment.util.SpUtil.KEY_STOCKS
import com.sam.stockassignment.util.Util.showToast
import com.sam.stockassignment.util.collectFlow
import com.sam.stockassignment.util.toYrMonDayStr
import com.sam.stockassignment.view.edit.EditDialog
import com.sam.stockassignment.view.loading.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel : MainViewModel by viewModels()
    private var dialog: AppCompatDialogFragment? = null

    lateinit var mainHandler: Handler

    private val updateTextTask = object : Runnable {
        override fun run() {
            PriceManager.randomUpdateStocks()
            binding.stockView.setDataToView(PriceManager.currentStocks, PriceManager.randomNums)
            mainHandler.postDelayed(this, PriceManager.interval)
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initView()
        initData()
        collectFlowsOrObserve()
    }

    private fun initData() {
        mainHandler = Handler(Looper.getMainLooper())
    }

    private fun initView() {
        binding.ivEdit.setOnClickListener {
            EditDialog.show(supportFragmentManager)
        }
    }

    private fun collectFlowsOrObserve() {
        viewModel.apiStockResult.collectFlow(this) {
            if (it.isLoading()) showLoading() else dismissLoading()
            if (it.isSuccess()) {
                PriceManager.defaultStockIds?.let { ids ->
                    val newList = it.data?.filter { stock-> ids.contains(stock.id) } ?: listOf()
                    PriceManager.currentStocks = newList
                    viewModel.triggerTimer()
                    viewModel.apiStockResultComplete()
                }
            } else if (it.isFailed()) {
                it.message?.showToast()
            }
        }

        viewModel.needTimer.collectFlow(this) { isNeed->
            if (isNeed) {
                startTimer()
                viewModel.triggerTimerComplete()
            }
        }

        viewModel.dbStocks.observe(this) {
            if (viewModel.hasGetDB) return@observe
            viewModel.completeGetDB()

            val today = System.currentTimeMillis().toYrMonDayStr()
            if (it.isNullOrEmpty() || SpUtil.getString(KEY_STOCKS) != today) { //資料為空 或 當天還沒有拿過資料
                viewModel.getStocks()
                return@observe
            }
            PriceManager.defaultStockIds?.let { ids ->
                val newList = it.filter { stock-> ids.contains(stock.id) }
                PriceManager.currentStocks = newList
                viewModel.triggerTimer()
            }
        }
    }

    private fun startTimer() {
        if (PriceManager.currentStocks.isEmpty()) return
        mainHandler.post(updateTextTask)
    }

    fun showLoading() {
        if (dialog == null) {
            dialog = LoadingDialog.show(supportFragmentManager)
        }
    }

    fun dismissLoading() {
        dialog?.dismiss()
        dialog = null
    }
}