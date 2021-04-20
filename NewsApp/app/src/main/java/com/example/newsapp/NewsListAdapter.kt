package com.example.newsapp
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.bumptech.glide.Glide
class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() // adapter needs data ,data is brought by activity ,for data we take arraylist of strings
{

    private val items: ArrayList<News> = ArrayList()
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder // this is called when viewholder is created
  {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false) //  layoutinflator convert item_news xml to view
      val viewHolder=NewsViewHolder(view)
      view.setOnClickListener{  // functionality when click on item is done
                listener.onItemClicked(items[viewHolder.adapterPosition])
       }
      return viewHolder
  }
    override fun getItemCount(): Int // called only ist time tells how many item are there in list
    {
        return items.size
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) // binds data to holder
    {
        val currentItem=items[position]
        holder.titleView.text=currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)  // for binding image we use glide library
    }
    fun updateNews(updatedNews: ArrayList<News>)  //  to update the items of an adapter
    {
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()  // to tell adapter to update its items
    }
}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
      val titleView: TextView = itemView.findViewById(R.id.title)   // for help see item_news.xml
      val image: ImageView = itemView.findViewById(R.id.image)
      val author: TextView = itemView.findViewById(R.id.author)
}

// to implement callback interfaces are used, callback is used by adapter to tell  activity that an item is clicked
interface NewsItemClicked{
    fun onItemClicked(item: News)
}






