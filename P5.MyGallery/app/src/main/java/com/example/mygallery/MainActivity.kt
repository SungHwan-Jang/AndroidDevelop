package com.example.mygallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

//Android have major 4 Component.
// 1. Activity : consist of Window
// 2. ContentProvider : share DB, file, network data to other app
// 3. BroadcastReceiver : receive broadcast from app or device
// 4. Service : it is good to background work without window

// 1.  Give app  authorization of reading saved data because Picture data saved external storage.
// 2. authorization of getting data from external storage is much danger. so, Author must be checked while app processing
// 3. Using contentResolver instance, external data bring Cursor instance.

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // 무적권 먼저

        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){ //권한 부여 확인

            //권한이 허용되지 않은 경우
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //이전에 이미 권한이 거부되었을 떄 설명창
                alert("It have to need authorization of external storage access") {
                    yesButton {
                        ActivityCompat.requestPermissions(this@MainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton { }
                }.show()
            }
            else { //첫 권한 설정 창
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE)
            }
        }
        else{ //권한이 이미 허용된 상황
            getAllPhotos()
        }
    }

    //사용 권한 요청 응답 처리
    //사용자가 권한을 요청하면 시스템은 onRequesetPermissionResult() 메서드를 호출하고 사용자의 응답을 전달.
    //해당 메서드를 통하여 응답 결과를 확인하여 사진 정보를 가져오거나 권한이 거부 되었다는 토스트 메시지를 표기
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_READ_EXTERNAL_STORAGE->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //권한이 허용된 상태. (하나의 권한만 요청했기 때문에 0번 인덱스만 확인한다.)
                    getAllPhotos()
                }
                else{
                    //권한이 거부된 상태
                    toast("Denied Author")
                }
                return
            }
        }
    }

    private fun getAllPhotos(){
        // This query() method need 5 params. (SQL GRAMMAR), 외부 저장소 읽기 권한을 추가하기위하여 Manifest.xml을 수정한다.
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN) // + "DESC" 로 내림차순 정렬 하면 에러 뿜뿜
        // 1. 어떤 데이터를 가져오느냐 (외부저장소 EXTERNAL_CONTENT_URI 지정)
        // 2. 어떤 항목의 데이터를 가져올 것인지 String 배열로 지정 (null 지정시 모든 항목 가져옴)
        // 3. 데이터를 가져올 조건을 지정 (null 전체 데이터 가져옴)
        // 4. 3번째 파라미터와 조합하여 조건을 지정 (null 사용하지 않음)
        // 5. 정렬 방법을 지정 (DESC 날짜의 내림차순 정렬)

        val fragments = ArrayList<Fragment>()

        cursor?.let{
            while(it.moveToNext()){
                val uri = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                //getString()에 index 전달시 해당 데이터를 String 반환 (사진이 저장된 위치의 경로)
                //getColumnIndexOrThrow() 메서드는 해당 컬럼이 몇 번째 인덱스인지 알 수 있다.
                Log.d("MainActivity", uri)
                fragments.add(PhotoFragment.newInstance(uri))
            }
            // 해당 cursor 객체는 내부적으로 데이터를 이동하는 포인터를 가지고 있어서 moveToNext() 메서드로 다음 정보로 이동 그 결과를 true로 반환
            // 만일 사진이 없다면 cursor 객체는 null이 된다.

            it.close()
            // 닫아주지 않는다면 메모리 누수 발생.
        }
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        ViewPager.adapter = adapter

        //To-Do (Timer 사용 3초 자동 슬라이드)
        // viewPager.currentItem, adapter.count 사용ㅜ
    }
}
