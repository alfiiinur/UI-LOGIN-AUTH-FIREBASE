package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

import com.example.myapplication.databinding.ShapeBtmBinding

class BottomNav @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 1
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: ShapeBtmBinding = ShapeBtmBinding.inflate(LayoutInflater.from(context), this, true)

    private var selectedIndex = 0

    private val items by lazy {
        listOf(binding.item1, binding.item2, binding.item3)
    }

    private lateinit var itemSelectedListener:(Int)-> Unit

    init{
        updateUi()
        initClickList()
    }

    private fun initClickList() = with(binding){
        items.forEachIndexed{index,
            item -> item.setOnClickListener{onItemClicked(index)}
        }
    }
    private fun onItemClicked(index:Int){
        this.selectedIndex = index
        updateUi()
        itemSelectedListener.invoke(index)
    }

    private fun Int.toDp() : Int{
        val scale  = resources.displayMetrics.density
        return( this * scale).toInt()
    }

    private fun updateUi() {
        items.forEachIndexed{index, imageView ->
            if (index == selectedIndex) {
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.white))
                imageView.setPadding(12.toDp(), 0, 0, 0)
            }else{
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.grey))
                imageView.setPadding(15.toDp(), 0, 0 , 0)
            }
        }
    }



}
