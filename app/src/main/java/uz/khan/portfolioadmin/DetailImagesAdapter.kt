package uz.khan.portfolioadmin

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.khan.portfolioadmin.databinding.DetailImageItemBinding

/**
 * @Author: Temur
 * @Date: 01/08/2022
 */


class DetailImagesAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val currentList = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun submitData(image: List<String>) {
        currentList.clear()
        currentList.addAll(image)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VhDetailImages(
            DetailImageItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false),
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VhDetailImages).bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}

private class VhDetailImages(
    private val binding: DetailImageItemBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(image: String) {
        Picasso.get()
            .load(image).into(binding.icon)
    }
}
