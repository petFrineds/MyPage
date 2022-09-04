package petfriends.mypage.dto;

import lombok.Data;
import petfriends.AbstractEvent;

@Data
public class DailyWrited extends AbstractEvent {

	private Long id;					// 일지ID
    private String dailyRecordDate;		// 작성일자
    private String contents;			// 작성내용
    private int starScore;				// 별점
    private String review;				// 후기
    private Long walkId;				// 산책ID
    private String dogWalkerId;			// 도그워커ID
    private String dogWalkerName;		// 도그워커명
    private String userId;				// 회원ID
    private String userName;			// 회원명
    private String walkStartDate; 		// 산책 시작 일시분(실제)
	private String walkEndDate;			// 산책 종료 일시분(실제)

}