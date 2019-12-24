package com.example.mygallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo.*

// ***** Convert FrameLayout to ConstraintLayout으로 xml을 변경한다. *****
/*
    imageView.setImageURI(Uri.parse("/storage/emulated/0/DCIM.../dkdk.jpg")) 형태로 이미지 뷰에 표시가 가능하다
    다만 Glide 라이브러리를 사용하는게 효율성이나 메모리 관리에서 좋다.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
private const val ARG_URI = "uri" // 컴파일 시간에 결정되는 상수

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    private var uri:String? = null //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //프래그먼트가 생성되면 onCreate() 메서드가 호출되고 ARG_URI 키에 저장된 uri값을 얻어서 변수에 저장
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
            uri = it.getString(ARG_URI)
        }
    }
    // onCreateView() 메서드는 프래그먼트에 표시될 뷰를 생성. 액티비티가 아닌 곳에서 레이아웃 리소스를 가지고 오려면
    // 액티비티가 아닌 곳에서 레이아웃 리소스를 가지고 오려면 LayoutInflater 객체의 inflate() 메서드를 사용한다.
    // R.layout.fragment_photo의 레이아웃 파일을 가지고 와서 반환.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    //뷰가 생성된 직후에 호출됨.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // savedInstanceState 는 상태를 저장할 객체이다.
        Glide.with(this).load(uri).into(imageView)
        // Glide.with(this)로 사용준비, load()메서드에 uri값을 인자 전달 해당 이미지를 로딩, 로딩 완료시 into() 메서드로 imageView에 표시.
    }

    // newInstance() 메서드를 이용하여 프래그먼트를 생성할 수 있고 인자로 uri 값을 전달
    // 이 값은 Bundle 객체에 ARG_URI 키로 저장되고 arguments 프로퍼티에 저장.
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        //fun newInstance(param1: String, param2: String) =
        fun newInstance(uri: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                    putString(ARG_URI, uri)
                }
            }
    }
}
