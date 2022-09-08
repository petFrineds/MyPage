package petfriends.mypage.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import petfriends.mypage.dto.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="mypage")
@Slf4j
@Data
public class MyPage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long mypage_id;

    /* 예약 */
    @Column(name = "reserved_id")
    private Long reservedId;

    //예약 시작시간
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "start_time")
	private Date startTime;

    //예약 종료시간
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "end_time")
	private Date endTime;

    @Column(name="dogwalker_schedule_id")
    private Long dogwalkerScheduleId;

    @Column(name="dogwalker_id")
    private String dogwalkerId;

    @Column(name="dogwalker_name")
    private String dogwalkerName;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column(name = "amount")
    private Double amount;


    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private ReservationStatus status; // 1-요청중, 2-결재완료, 3-산책시작, 4-산책종료, 5-포인트지급, 10-결재취소

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_type")
    private PayType payType;

    @Column(name = "walk_id")
    private Long walkId;
    /* 산책 */
    @Column(name = "walk_start_date")
    private Date walkStartDate;

    //산책시작
    @Column(name = "walk_end_date")
    private Date walkEndDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="reg_date")
    private Date regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="upd_date")
    private Date updDate;


    @Transient
    private int sortSeq;

    @PostPersist
    public void onPostPersist(){
        //MyPage Post
        log.info(">> MyPage onPostPersist --> " + this.toString());
    }

    @PostUpdate
    public void onPostUpdate(){
        log.info(">> MyPage onPostUpdate  --> " +  this.toString());
    }

}
