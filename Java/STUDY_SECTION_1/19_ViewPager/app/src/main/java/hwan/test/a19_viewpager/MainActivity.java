package hwan.test.a19_viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    ViewPager viewPager;

    ArrayList<View> viewArrayList = new ArrayList<View>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView)findViewById(R.id.resultText);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        // 뷰객체를 생성하여 ArrayList에 넣는다.
        LayoutInflater layoutInflater = getLayoutInflater();
        View v1 = layoutInflater.inflate(R.layout.view1, null);
        View v2 = layoutInflater.inflate(R.layout.view2, null);
        View v3 = layoutInflater.inflate(R.layout.view3, null);

        viewArrayList.add(v1);
        viewArrayList.add(v2);
        viewArrayList.add(v3);

        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter();
        viewPager.setAdapter(adapter);

        PagerListener listener = new PagerListener();
        viewPager.addOnPageChangeListener(listener);
    }

    class CustomViewPagerAdapter extends PagerAdapter{

        // ViewPager로 보여줄 뷰의 전체 갯수
        @Override
        public int getCount() {

            return viewArrayList.size();
        }

        // 뷰 페이저로 보여줄 뷰 객체를 반환한다.
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            // position 번쨰 뷰 객체를 뷰 페이저에 세팅한다.
            viewPager.addView(viewArrayList.get(position));
            return viewArrayList.get(position);
        }

        // instantiateItem에서 만든 객체를 사용할 것인지 여부를 결정한다.
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            // 해당 메소드는 안드로이드 os가 스스로 콜 하기도 한다 따라서 아래와 같은 기법을 사용한다.
            // 내가 원하는 작동일 경우에만 true를 반환하도록 한다. (파라미터 설명 - view (안드 선택 뷰), object (instantiateItem 반환 객체))
            return view == object;
        }

        // 뷰 페이저에서 뷰가 사라질 때 제거하는 작업을 한다.
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            viewPager.removeView((View)object);
        }
    }

    class PagerListener implements ViewPager.OnPageChangeListener{

        // 뷰의 전환이 완료되었을 때 호출되는 메서드
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            resultText.setText(position + " Page index!");
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}