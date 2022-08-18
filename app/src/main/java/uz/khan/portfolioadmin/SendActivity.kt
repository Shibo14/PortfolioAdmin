package uz.khan.portfolioadmin

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.portfolio.data.FirebaseDataSender
import com.google.firebase.storage.FirebaseStorage
import uz.khan.portfolioadmin.data.models.ProjectModel
import uz.khan.portfolioadmin.databinding.ActivitySendBinding

class SendActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendBinding
    private lateinit var adapter: ProjectTypeAdapter
    private lateinit var adapterImages: ProjectImagesAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ProjectTypeAdapter()
        adapterImages = ProjectImagesAdapter()
        binding.typesRv.adapter = adapter
        binding.imagesRv.adapter = adapterImages

        adapter.submitData(
            arrayListOf(
                Type(1, "android", R.drawable.ic_baseline_phone_android_24, false),
                Type(2, "web", R.drawable.ic_baseline_desktop_mac_24, false),
                Type(3, "graphic", R.drawable.ic_baseline_grain_24, false),
            )
        )

        adapter.onClick = OnClick { adapterType ->
            adapter.selectedType = adapterType?.title ?: ""
            adapter.submitData(adapter.currentList.map { it.copy(isSelected = it.id == adapterType?.id) })
        }

        adapterImages.onRemove = OnClick {
            adapterImages.currentList.remove(it)
            binding.counterTv.text = ("${adapterImages.currentList.filter { it.isImage }.size}/10")
            val cacheImages = ArrayList<ImageModel>()
            cacheImages.addAll(adapterImages.currentList)

            adapterImages.currentList.clear()
            cacheImages.forEach {
                adapterImages.submitData(it)
            }
            adapterImages.notifyDataSetChanged()
        }

        binding.sendBtn.setOnClickListener {
            if (adapter.selectedType.isNotEmpty() && adapterImages.currentList.filter { it.isImage }
                    .isNotEmpty() && binding.projectDescEdt.text?.trim()
                    ?.isNotEmpty() == true && binding.projectNameEdt.text?.trim()
                    ?.isNotEmpty() == true) {

                binding.nestedView.isGone = true
                binding.progress.isGone = false
                val imgUrls = ArrayList<String>()

                val storage = FirebaseStorage.getInstance()
                var uploadedCount = 0

                adapterImages.currentList.forEach {
                    val ref = storage.getReference(System.currentTimeMillis().toString())

                    if (it.isImage) {

                        ref.putFile(it.uri!!).addOnSuccessListener {

                            ref.downloadUrl.addOnSuccessListener {
                                imgUrls.add(it.toString())
                            }.addOnCompleteListener {
                                uploadedCount++

                                if (adapterImages.currentList.size - 1 == uploadedCount) {
                                    val project = ProjectModel(
                                        "",
                                        binding.projectNameEdt.text.toString(),
                                        binding.projectDescEdt.text.toString(),
                                        adapter.selectedType,
                                        imgUrls.asString(), false
                                    )

                                    FirebaseDataSender().send(project) {
                                        binding.nestedView.isGone = false
                                        binding.progress.isGone = true
                                        finish()
                                    }
                                }
                            }

                        }.addOnCompleteListener {

                        }
                    }
                }
            } else {
                Toast.makeText(this, "Malumotlarni to'liq kiritilmadi !", Toast.LENGTH_SHORT).show()
            }
        }


        adapterImages.onClick = OnClick {
            selectImageFromGalleryResult.launch("image/*")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                adapterImages.submitData(ImageModel(uri))
                binding.counterTv.text =
                    ("${adapterImages.currentList.filter { it.isImage }.size}/10")
            }
        }
}


data class Type(
    val id: Int,
    val title: String,
    @DrawableRes val image: Int,
    val isSelected: Boolean,
)


data class ImageModel(
    val uri: Uri?,
    val isImage: Boolean = true,
    @DrawableRes val addImage: Int = R.drawable.ic_baseline_add_24,
)

