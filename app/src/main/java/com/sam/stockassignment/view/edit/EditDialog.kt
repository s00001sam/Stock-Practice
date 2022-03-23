package com.sam.stockassignment.view.edit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.sam.stockassignment.R
import com.sam.stockassignment.databinding.DialogEditBinding
import com.sam.stockassignment.hilt.ViewModule
import com.sam.stockassignment.util.Logger
import com.sam.stockassignment.util.PriceManager
import com.sam.stockassignment.util.Util.showToast
import com.sam.stockassignment.util.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogEditBinding
    private val viewModel: EditViewModel by viewModels()

    companion object {
        private const val FRAGMENT_TAG = "EditDialog"

        @JvmStatic
        fun newInstance(): EditDialog {
            val args = Bundle()
            val fragment = EditDialog()
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun show(fragmentManager: FragmentManager): EditDialog {
            val dialog = newInstance()
            dialog.show(fragmentManager, FRAGMENT_TAG)
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        dialog?.window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEditBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectFlow()
    }

    private fun initView() {
        viewModel.edit.value = PriceManager.interval.toString()
        viewModel.isShowRed.value = PriceManager.isShowRed

        binding.btnConfirm.setOnClickListener {
            setDataToPriceManager()
        }
    }

    private fun collectFlow() {
        viewModel.leaveControl.collectFlow(viewLifecycleOwner) {
            if (it) {
                dismiss()
                viewModel.leaveComplete()
            }
        }
    }

    private fun setDataToPriceManager() {
        if (viewModel.edit.value.isEmpty() || viewModel.edit.value.startsWith("0")) {
            getString(R.string.interval_enter_error).showToast()
            return
        }
        try {
            PriceManager.interval = viewModel.edit.value.toLong()
            PriceManager.isShowRed = viewModel.isShowRed.value
        } catch (e: Exception) {
            Logger.d("setDataToPriceManager error=${e.localizedMessage}")
        } finally {
            dismiss()
        }

    }
}