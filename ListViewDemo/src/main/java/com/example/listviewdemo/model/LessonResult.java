package com.example.listviewdemo.model;

import java.util.List;

public class LessonResult {

    private String message;
    private int mStatus;
    private List<LessonInfo> mLessonInfoList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public List<LessonInfo> getmLessonInfoList() {
        return mLessonInfoList;
    }

    public void setmLessonInfoList(List<LessonInfo> mLessonInfoList) {
        this.mLessonInfoList = mLessonInfoList;
    }

    //具体课程的类，内部类，也可以单独提取成一个文件
    public static class LessonInfo{
        private int id;
        private String name;
        private String Descrioption;

        public LessonInfo() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescrioption() {
            return Descrioption;
        }

        public void setDescrioption(String descrioption) {
            Descrioption = descrioption;
        }
    }
}
