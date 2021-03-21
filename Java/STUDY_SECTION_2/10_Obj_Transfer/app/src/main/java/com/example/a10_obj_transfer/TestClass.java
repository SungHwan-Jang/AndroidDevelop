package com.example.a10_obj_transfer;

import android.os.Parcel;
import android.os.Parcelable;

public class TestClass implements Parcelable {
    int data10;
    String data20;

    // **** 중요
    public static final Creator<TestClass> CREATOR = new Creator<TestClass>() {
        // 새롭게 생성된 activity에서 객체를 복원할때 아래 메서드를 자동 호출하게 된다.
        // 객체 복원
        @Override
        public TestClass createFromParcel(Parcel source) {
            TestClass t1 = new TestClass();
            t1.data10 = source.readInt();
            t1.data20 = source.readString();

            return t1;
            //return null;
        }

        // 아래 메서드는 배열로 추출될때 사용된다.
        @Override
        public TestClass[] newArray(int size) {
            return new TestClass[size];
            //return new TestClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 객체를 intent에 담을 때 자동으로 호출되는 메서드
        // 첫번째 매개변수로 들어오는 Parcel 객체의 객체복원을 위해 필요한 정보를 담는다.
        dest.writeInt(data10);
        dest.writeString(data20);
    }
}
