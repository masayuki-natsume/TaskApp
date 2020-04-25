package jp.techacademy.masayuki.natsume.taskapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(context: Context): BaseAdapter() {

    private val mLayoutInflater: LayoutInflater //xmlのViewを取り扱う　ﾌﾟﾛﾊﾟﾃｲ
    var taskList = mutableListOf<Task>() //taskListﾌﾟﾛﾊﾟﾃｲ(ｱｲﾃﾑを保持するﾘｽﾄを定義)

    init {                                                  //Realmの初期化
        this.mLayoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {                 //mTaskListのｻｲｽﾞ
        return taskList.size
    }

    override fun getItem(position: Int): Any {     //mTaskListの要素(ﾃﾞｰﾀ)
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {  //mTaskListのﾃﾞｰﾀID
        return taskList[position].id.toLong()
    }
    //BaseAdapterｸﾗｽのgetViewﾒｯｿﾄﾞの実装
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: mLayoutInflater.inflate(android.R.layout.simple_list_item_2, null)

        val textView1 = view.findViewById<TextView>(android.R.id.text1)
        val textView2 = view.findViewById<TextView>(android.R.id.text2)

        // Taskクラスから情報を取得
        textView1.text = taskList[position].title
        //情報
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE)
        val date = taskList[position].date
        textView2.text = simpleDateFormat.format(date)

        return view
    }
}