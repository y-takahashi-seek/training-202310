package jp.seekengine.trainingjava.controller;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule {

    // ゲッター & セッター
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String fromDatetime;  // 独自の日時フォーマットで保存
    private String toDatetime;    // 独自の日時フォーマットで保存

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFromDatetime(String fromDatetime) {
        this.fromDatetime = fromDatetime;
    }

    public void setToDatetime(String toDatetime) {
        this.toDatetime = toDatetime;
    }

    // 必要であれば、他のメソッドやコンストラクタも追加できます
}
