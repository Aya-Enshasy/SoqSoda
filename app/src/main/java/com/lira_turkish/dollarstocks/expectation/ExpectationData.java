
package com.lira_turkish.dollarstocks.expectation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExpectationData implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        private final static long serialVersionUID = -5926048728165516412L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

}