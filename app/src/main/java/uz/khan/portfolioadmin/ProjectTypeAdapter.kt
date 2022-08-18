package uz.khan.portfolioadmin

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.khan.portfolioadmin.databinding.TypeItemBinding

/**
 * @Author: Temur
 * @Date: 01/08/2022
 */


class ProjectTypeAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val currentList = ArrayList<Type>()
    var selectedType = ""

    fun submitData(list: List<Type>) {
        currentList.clear()
        currentList.addAll(list)
        notifyDataSetChanged()
    }

    var onClick: OnClick<Type>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Vh(
            TypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Vh).bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}

private class Vh(private val binding: TypeItemBinding, private val onClick: OnClick<Type>?) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(type: Type) {
        binding.icon.setImageResource(type.image)
        if (type.isSelected) {
            binding.root.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        } else {
            binding.root.backgroundTintList =
                ColorStateList.valueOf(binding.root.context.getColor(R.color.defColor))
        }
        binding.icon.setOnClickListener {
            onClick?.onClick(type)
        }
    }
}
