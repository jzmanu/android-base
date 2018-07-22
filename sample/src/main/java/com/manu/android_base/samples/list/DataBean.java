package com.manu.android_base.samples.list;

import java.util.List;

/**
 * Created by jzman
 * Powered by 2018/7/22 0022.
 */

public class DataBean {

    private List<GroupBean> groupList;

    public List<GroupBean> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupBean> groupList) {
        this.groupList = groupList;
    }

    public static class GroupBean {
        private String groupName;
        private List<ChildBean> childList;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<ChildBean> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildBean> childList) {
            this.childList = childList;
        }

        public GroupBean(String groupName, List<ChildBean> groupList) {
            this.groupName = groupName;
            this.childList = groupList;
        }


        public static class ChildBean {
            private String childName;

            public ChildBean(String childName) {
                this.childName = childName;
            }

            public String getChildName() {
                return childName;
            }

            public void setChildName(String childName) {
                this.childName = childName;
            }
        }
    }
}
