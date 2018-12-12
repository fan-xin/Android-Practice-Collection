package com.example.networkdemo;

import java.util.List;

public class LessonResult {
    private int mStatus;
    private List<Lesson> mLessons;

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public List<Lesson> getmLessons() {
        return mLessons;
    }

    public void setmLessons(List<Lesson> mLessons) {
        this.mLessons = mLessons;
    }

    @Override
    public String toString() {
        return "获取到的课程结果：{" +
                "mStatus=" + mStatus +
                ", mLessons=" + mLessons +
                '}';
    }

    public static class Lesson {
        private int mId;
        private int mLearnNumber;
        private String mName;
        private String mSmallPicture;
        private String mBigPicture;
        private String mDescription;

        public int getmId() {
            return mId;
        }

        public void setmId(int mId) {
            this.mId = mId;
        }

        public int getmLearnNumber() {
            return mLearnNumber;
        }

        public void setmLearnNumber(int mLearnNumber) {
            this.mLearnNumber = mLearnNumber;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmSmallPicture() {
            return mSmallPicture;
        }

        public void setmSmallPicture(String mSmallPicture) {
            this.mSmallPicture = mSmallPicture;
        }

        public String getmBigPicture() {
            return mBigPicture;
        }

        public void setmBigPicture(String mBigPicture) {
            this.mBigPicture = mBigPicture;
        }

        public String getmDescription() {
            return mDescription;
        }

        public void setmDescription(String mDescription) {
            this.mDescription = mDescription;
        }

        @Override
        public String toString() {
            return "\n 这节课的内容是 \n {" +
                    "Id=" + mId +
                    ",\n 学习人数=" + mLearnNumber +
                    ",\n 课程名称='" + mName + '\'' +
                    ",\n 小图片='" + mSmallPicture + '\'' +
                    ",\n 大图片='" + mBigPicture + '\'' +
                    ",\n 描述='" + mDescription + '\'' +
                    '}'+'\n';
        }
    }
}
