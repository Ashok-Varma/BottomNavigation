package com.ashokvarma.bottomnavigation.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Class description

 * @author ashokvarma
 * *
 * @version 1.0
 * *
 * @see
 * @since 10 Jul 2017
 */
internal const val KEY_MESSAGE = "message"

fun newTextFragmentInstance(message: String): TextFragment {
    val textFragment = TextFragment()

    val args = Bundle()
    args.putString(KEY_MESSAGE, message)

    textFragment.arguments = args
    return textFragment
}

class TextFragment : Fragment() {

    private var msg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        msg = arguments?.getString(KEY_MESSAGE, "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_text, container, false)
        (view.findViewById<TextView>(R.id.tf_textview)).text = msg ?: ""
        return view
    }
}