package com.example.communityshield

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WantedActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Wanted>
    private lateinit var myToolbar: Toolbar
    lateinit var suspectImageId : Array<Int>
    lateinit var crimeInfo : Array<String>
    lateinit var briefReport : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wanted)

        suspectImageId = arrayOf(
            R.drawable.criminal,
            R.drawable.criminal,
            R.drawable.criminal,
            R.drawable.criminal,
            R.drawable.criminal,
            R.drawable.criminal
            )

        crimeInfo = arrayOf(
            "Male, 20 -24yrs, +-1.8m Tall, Armed Robbery",
            "Male, 20 -24yrs, +-1.8m Tall, Armed Robbery",
            "Male, 20 -24yrs, +-1.8m Tall, Armed Robbery",
            "Male, 20 -24yrs, +-1.8m Tall, Armed Robbery",
            "Male, 20 -24yrs, +-1.8m Tall, Armed Robbery",
            "Male, 20 -24yrs, +-1.8m Tall, Armed Robbery"
            )

        briefReport = arrayOf(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vulputate, ante quis consectetur maximus, quam magna congue diam, id finibus nunc libero aliquet lectus. Nulla tincidunt erat ut massa elementum, ut blandit nisi consectetur. Etiam bibendum risus ac eros hendrerit, non aliquam ligula faucibus. Suspendisse eget mauris vitae tellus tempor viverra aliquam quis arcu. Cras tempus diam at dolor laoreet consequat. Aliquam et fermentum magna. Integer tristique, ex non blandit malesuada, nunc nisl rhoncus arcu, eu suscipit eros urna eu leo. Quisque vehicula id magna eget euismod. Praesent eu mauris non massa imperdiet ultrices et vel ligula. Integer vel viverra purus. Ut tincidunt ullamcorper posuere.",
            "Aliquam erat volutpat. Curabitur lectus enim, laoreet et magna quis, aliquam ultrices nisl. Mauris nulla enim, pellentesque ut molestie a, blandit gravida tortor. Duis egestas turpis ante, quis accumsan magna sollicitudin sit amet. Maecenas sit amet ornare tortor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nullam nec lobortis tellus, eu blandit tortor. Aliquam at urna sed tellus accumsan consectetur mollis a mauris. Donec tellus nisi, ornare at purus vel, blandit sodales mauris. Nullam augue ligula, pulvinar et faucibus et, ultrices ut elit. Maecenas ullamcorper varius ante ac scelerisque. Aliquam leo dolor, rutrum pretium luctus et, dapibus at eros. Proin ac lacinia nunc, et rutrum risus. Maecenas placerat aliquet metus, sit amet tincidunt lacus ultricies eget. Sed ac gravida enim, quis tempus turpis.",
            "Etiam mattis, dui ut rhoncus molestie, elit orci auctor dui, interdum aliquam diam diam quis justo. Aliquam eu lobortis nisl. Donec sollicitudin sapien in accumsan ultricies. Aenean pretium urna sit amet ipsum lacinia, nec posuere orci mattis. In efficitur accumsan ante, id dictum ante fermentum quis. Nam viverra ipsum eu rhoncus sollicitudin. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; In fermentum malesuada sem tempus fringilla.",
            "Donec in consequat ipsum. Aenean congue non mauris et iaculis. Nulla maximus tincidunt placerat. Nam interdum vitae lorem et facilisis. Sed at vehicula lectus. Suspendisse ut orci velit. Praesent a faucibus purus, posuere porttitor ante. Curabitur non lorem commodo lacus scelerisque imperdiet. Vestibulum auctor tortor ac pellentesque placerat. Nulla euismod arcu elit, eu cursus nibh mollis eget.",
            "Maecenas imperdiet nibh ac sem convallis, at porta mi mattis. Praesent et orci lorem. Etiam elementum venenatis erat consequat euismod. Praesent lacus sapien, scelerisque ut lectus non, fringilla consectetur mauris. Maecenas pretium vulputate magna ac volutpat. Mauris blandit, nisi feugiat congue accumsan, massa velit efficitur lectus, at lacinia risus sapien eu sapien.",
            "Pellentesque laoreet enim vel nulla pellentesque, non congue turpis faucibus. Ut et velit porta, placerat enim a, egestas nisi. Nam bibendum enim a mi cursus, eget congue urna volutpat. Ut finibus tincidunt purus quis egestas. Cras sit amet risus porttitor, feugiat massa vitae, commodo turpis. Fusce eu purus ut enim tincidunt auctor."
        )


            newRecyclerView = findViewById(R.id.wantedRecyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Wanted>()
        getData()
    }

    private fun getData() {
        for (i in suspectImageId.indices)
        {
            val wanted = Wanted(suspectImageId[i],crimeInfo[i],briefReport[i])
            newArrayList.add(wanted)
        }

        newRecyclerView.adapter = WantedAdapter(newArrayList)
    }
}