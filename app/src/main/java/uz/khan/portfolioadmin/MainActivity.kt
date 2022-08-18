package uz.khan.portfolioadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.portfolio.data.FirebaseDataReceiver
import uz.khan.portfolioadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        adapter = ProjectAdapter()
        binding.rvSwitch.adapter = adapter

        adapter.onClick = OnClick {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("project", it)
            startActivity(intent)
        }

        binding.publicBtn.setOnClickListener {
            val intent = Intent(this, PublishedActivity::class.java)
            startActivity(intent)
        }

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, SendActivity::class.java)
            startActivity(intent)
        }

        FirebaseDataReceiver().getProjects(onSuccess = {
            adapter.submitData(it)
        }, )
    }
}